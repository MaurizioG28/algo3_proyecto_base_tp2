package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;
import java.util.stream.Collectors;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;


public class Tablero {


    private final Map<Integer, Terreno> terrenos;
    private final Map<Coordenada, Vertice> vertices;
    private final Map<Coordenada, Lado> lados;
    private final Map<Color, Integer> pobladosColocadosPorColor = new HashMap<>();

    private Hexagono posicionDelLadron;


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


  
    public void tirarDados(Dados dados){
        int valor = dados.tirar();
        for (Vertice vertice : new HashSet<>(vertices.values())){
            vertice.produci(valor);
        }

    }
    public void veticicesAdyacentes(Coordenada coordenada){

    }


    public void construirPoblado(Jugador jugador,  Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException {
        Vertice vertice = this.vertices.get(coordenada);
        if (vertice == null) throw new IllegalArgumentException("Vertice inexistente");
        jugador.puedeCosntruirPoblado();
        vertice.colocarPoblado(jugador);
    }

//
//    public List<Jugador> moverLadron(Jugador jugadorActual, Hexagono posicion) {
////        if (!tableroInicializado) {
////           throw new IllegalStateException("El tablero debe estar inicializado antes de mover al ladrón.");
////        }
//        posicion.moverLadron();
//        this.posicionDelLadron = posicion;
//        List<Jugador> victimas = new ArrayList<>();
//        for (Vertice v : posicion.getVertices()) {
//            if (!v.tieneConstruccion()) continue;
//            Color propietario = v.getPropietario();
//            if (propietario == null) continue;
//            if (propietario.equals(jugadorActual)) continue;
//            if (!victimas.contains(propietario)) {
//                victimas.add(propietario);
//            }
//        }
//        return victimas;
//    }


    public Dividendo colocarEnVertice(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException, ReglaDistanciaException {
        Vertice verticeObjetivo = vertices.get(coordenada);
        Color colorActual = pieza.getColorActual();

        if (verticeObjetivo.tieneConstruccionAdyacente())
            throw new ReglaDistanciaException("No se puede colocar el poblado por la regla de distancia");

        verticeObjetivo.colocarConstruccion(pieza);
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

    public void construirCamino(Jugador jugador, Coordenada coordenada) {
        Lado lado = lados.get(coordenada);
        if(lado == null) throw  new IllegalArgumentException("Lado no encontrado");
        jugador.puedeCosntruirCamino();
        lado.colocarCamino(jugador);
    }
}

