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

    private Integer posicionDelLadron;

//    public Tablero(){
//    }

    public Tablero(Map<Integer, Terreno> hexagonos, Map<Coordenada, Vertice> vertices, Map<Coordenada, Lado> ladosPorCoordenada) {
        this.terrenos = hexagonos;
        this.vertices = vertices;
        this.lados= ladosPorCoordenada;

        this.posicionDelLadron= encontrarPosicionInicialDelLadron();
    }

    private Integer encontrarPosicionInicialDelLadron() {
        for (Map.Entry<Integer, Terreno> entry : terrenos.entrySet()) {
            Terreno terreno = entry.getValue();
            if (terreno.esDesierto()) {
                return entry.getKey();
            }
        }
       return 0;
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

//    private Map<Jugador, EnumMap<Recurso, Integer>> calcularProduccion(int numeroLanzado){
//        Map<Jugador, EnumMap<Recurso, Integer>> produccion = new HashMap<>();
//
//        for (Hexagono h : hexagonos) {
//            if (h.getNumero() != numeroLanzado) continue;
//            if (!h.getTipo().produceAlgo()) continue;    // desierto no produce
//            if (!h.sePuedeProducir()) continue;          // si usás ladrón
//
//            RecursoBase r = h.getTipo().recursoOtorgado();
//
//            for (Vertice v : h.getVertices()) {
//                if (!v.tieneConstruccion()) continue;
//
//                int cant = v.obtenerFactorProduccion();
//                Jugador j = v.getPropietario();
//
//                assert r != null;
//                produccion
//                        .computeIfAbsent(j, k -> new EnumMap<>(Recurso.class))
//                        .merge(r.tipo(), cant, Integer::sum);
//            }
//        }
//        return produccion;
//    }

//    public void repartirProduccion(int numeroLanzado){
//        Map<Jugador, EnumMap<Recurso, Integer>> bolsa = calcularProduccion(numeroLanzado);
//        bolsa.forEach((jug, mapa) -> mapa.forEach(jug::agregarRecurso)); // (recurso,cantidad)
//    }


    public List<Color> moverLadron(Jugador jugadorActual, Integer posicionId) {
        Terreno nuevo = terrenos.get(posicionId);
        Terreno anterior = terrenos.get(posicionDelLadron);
        if(nuevo==anterior)
            throw new IllegalStateException("Se debe movera al Ladron.");

        // Quitar ladrón del terreno anterior (si no es desierto)
        if (anterior!=null && !anterior.esDesierto())
            anterior.moverLadronQuitar();


        nuevo.moverLadronPoner();
        posicionDelLadron = posicionId;

        // Obtener víctimas delegando en Terreno
        List<Color> victimas= nuevo.jugadoresAfectadosPorElLadron(jugadorActual);
        return victimas;
    }



    public Dividendo colocarEnVertice(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException, ReglaDistanciaException {
        Vertice verticeObjetivo = vertices.get(coordenada);
        Color colorActual = pieza.getColorActual();

        if (verticeObjetivo.tieneConstruccionAdyacente())
            throw new ReglaDistanciaException("No se puede colocar el poblado por la regla de distancia");

        verticeObjetivo.colocar(pieza);
        pobladosColocadosPorColor.put(colorActual, pobladosColocadosPorColor.getOrDefault(colorActual, 0) + 1);


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

    public Dividendo colocarEnLado(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException {

        Lado l = lados.get(coordenada);
        l.colocar(pieza);
        return null;
    }

    public boolean tieneCarreteraEn(Coordenada caminoEsperadoEn) {
        Lado l = lados.get(caminoEsperadoEn);
        return l.tieneConstruccion();
    }
}

