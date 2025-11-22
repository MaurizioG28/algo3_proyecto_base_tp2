package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;

import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.modelo.interfaces.*;


public class Tablero {


    private final Map<Integer, Terreno> terrenos;

    //la distribucion de fichas numeradas son las siguientes: un 2 y un 12, luego dos de cada una entre 3 y 11, el 7 está excluido
    //private int[] fichasNumeradas = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
    
    private Dados dados = new Dados();

    //private ArrayList<Hexagono> hexagonos = new ArrayList<>();
    private final Map<Coordenada, Vertice> vertices;
    Map<Coordenada, Lado> lados;

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





    public boolean tableroCorrectamenteInicializado(){
        boolean correcto = true;
        Collection<Terreno> todosTerrenos = terrenos.values();
        for (Terreno terreno : todosTerrenos) {
            if (!terreno.todosColocados()) {
                correcto = false;
            }
        }

        return correcto;
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


    private void validarLado(ILado lado, Jugador jugador) {
        ILado.validar(lado, jugador);
    }
    //private void colocar(Camino camino, ILado lado){}

//    public void colocarCaminoEn(ILado lado, Jugador jugador){
//
//        FabricaConstrucciones fabrica = new FabricaConstrucciones();
//        Costo costo = fabrica.costoDeCamino();
//        this.validarLado(lado, jugador);
//        jugador.pagar(costo);
//        Camino camino = fabrica.crearCamino(jugador);
//        this.colocar(camino,lado);
//    }
}

