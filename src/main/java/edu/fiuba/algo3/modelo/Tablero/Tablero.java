package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;
import java.util.stream.Collectors;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.modelo.interfaces.*;


public class Tablero {


    private final Map<Integer, Terreno> terrenos;
    private Dados dados = new Dados();
    private final Map<Coordenada, Vertice> vertices;
    Map<Coordenada, Lado> lados;
    private final Map<Color, Integer> pobladosColocadosPorColor = new HashMap<>();

    private Hexagono posicionDelLadron;

//    public Tablero(){
//    }

    public Tablero(Map<Integer, Terreno> hexagonos, Map<Coordenada, Vertice> vertices, Map<Coordenada, Lado> ladosPorCoordenada) {
        this.terrenos = hexagonos;
        this.vertices = vertices;
        this.lados= ladosPorCoordenada;
    }



    @Override
    public boolean equals(Object object){
        if(this.getClass() != object.getClass()){return false;}
        return ((Tablero) object).mismosHexagonos(terrenos);
    }

    private boolean mismosHexagonos(Map<Integer, Terreno> OtroTerreno){
        return terrenos.equals(OtroTerreno);

    }


  
    public int tirarDados(){
        return dados.tirar();
    }





//    public boolean tableroCorrectamenteInicializado(){
//        boolean correcto = true;
//        Collection<Terreno> todosTerrenos = terrenos.values();
//        for (Terreno terreno : todosTerrenos) {
//            if (!terreno.todosColocados()) {
//                correcto = false;
//            }
//        }
//
//        return correcto;
//    }

    public void construirPoblado(Jugador jugador, IVertice vertice) throws ReglaDistanciaException {
//        if (!tableroInicializado) {
//            throw new IllegalStateException("El tablero debe estar inicializado antes de construir poblados.");
//        }

        if (vertice.tieneConstruccion() || vertice.tieneConstruccionAdyacente()) {
            throw new ReglaDistanciaException("No se puede construir tan cerca de otro poblado.");
        }
        /*
        construye directamente, falta implementar el chequeo de recursos del jugardor.
        Con algo como jugador.recursosPoblado()
        */
        vertice.colocarPoblado(jugador);
        //List<Recurso> recursos = vertice.darRecursos();
        //jugador.sumarRecursos(recursos);
    }

    public void distribuirProduccion(int numeroDado) {
        // Iteramos sobre los valores del mapa 'terrenos'
        for (Terreno terreno : this.terrenos.values()) {
            terreno.verificarYProducir(numeroDado);
        }
    }


    public List<Jugador> moverLadron(Jugador jugadorActual, Hexagono posicion) {
//        if (!tableroInicializado) {
//            throw new IllegalStateException("El tablero debe estar inicializado antes de mover al ladrón.");
//        }
        posicion.moverLadron();
        this.posicionDelLadron = posicion;
        List<Jugador> victimas = new ArrayList<>();
        for (Vertice v : posicion.getVertices()) {
            if (!v.tieneConstruccion()) continue;
            Jugador propietario = v.getPropietario();
            if (propietario == null) continue;
            if (propietario.equals(jugadorActual)) continue;
            if (!victimas.contains(propietario)) {
                victimas.add(propietario);
            }
        }
        return victimas;
    }


//    private void validarLado(ILado lado, Jugador jugador) {
//        ILado.validar(lado, jugador);
//    }

    public Dividendo colocarEnVertice(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException, ReglaDistanciaException {
        Vertice verticeObjetivo = vertices.get(coordenada);
        Color colorActual = pieza.getColorActual();

        if (verticeObjetivo.tieneConstruccionAdyacente())
            throw new ReglaDistanciaException("No se puede colocar el poblado por la regla de distancia");

        verticeObjetivo.colocar(pieza);
        pobladosColocadosPorColor.put(colorActual, pobladosColocadosPorColor.getOrDefault(colorActual, 0) + 1);
        List<Terreno> terrenosAdyacentes = terrenos.values().stream()
                .filter(t -> t.tieneVertice(verticeObjetivo))
                .collect(Collectors.toList());

        if ( esSegundoPoblado(pieza.getColorActual()))
            return calcularDividendosIniciales(coordenada,colorActual);

        return Dividendo.vacio();
    }

    private boolean esSegundoPoblado(Color color) {
        return (pobladosColocadosPorColor.get(color)==2);
    }

    private Dividendo calcularDividendosIniciales(Coordenada coord, Color colorActual) {
        Vertice v = vertices.get(coord);
        Dividendo d = new Dividendo(colorActual);

        for (Terreno terrenoActual : terrenos.values()) {
            if (terrenoActual.tieneVertice(v) && terrenoActual.sePuedeProducir()) {
                int cantidad = v.factorProduccion();
                d.agregar(Objects.requireNonNull(terrenoActual.recursoOtorgado(cantidad)));
            }
        }

        return d;
    }

    public Dividendo colocarEnLado(Construccion pieza, Coordenada coordenada) {

        Lado l = lados.get(coordenada);
        l.colocar(pieza);
        return null;
    }

    public boolean tieneCarreteraEn(Coordenada caminoEsperadoEn) {
        Lado l = lados.get(caminoEsperadoEn);
        return l.tieneConstruccion();
    }
    public Vertice obtenerVertice(Coordenada coordenada) {
        return this.vertices.get(coordenada);
    }

}

