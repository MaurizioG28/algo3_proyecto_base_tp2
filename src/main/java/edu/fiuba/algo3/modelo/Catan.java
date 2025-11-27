package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;

import java.util.*;

public class Catan implements PuntajeListener {
    private final Random rng;
    private final List<Jugador> jugadores= new ArrayList<>();
    private Jugador ganador = null;

    private List<Terreno> terrernos = Arrays.asList(
            new Bosque(),
            new Campo(),
            new Bosque(),
            new Pastizal(),
            new Bosque(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Colina(),
            new Colina(),
            new Desierto(),
            new Colina(),
            new Pastizal(),
            new Montania(),
            new Pastizal(),
            new Bosque(),
            new Pastizal()
    );

    private List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
            new Produccion(2),
            new Produccion(3),
            new Produccion(3),
            new Produccion(4),
            new Produccion(4),
            new Produccion(5),
            new Produccion(5),
            new Produccion(6),
            new Produccion(6),
            new Produccion(8),
            new Produccion(8),
            new Produccion(9),
            new Produccion(9),
            new Produccion(10),
            new Produccion(10),
            new Produccion(11),
            new Produccion(11),
            new Produccion(12)

    ));


    public Catan() {
        this.rng = new Random();  // producción
        inicializar();
    }
    public Catan(Random randomFijoParaTest) {
        this.rng = randomFijoParaTest;   // reproducible
        inicializar();
    }

    private void inicializar() {
        randomizarTerrenos();
        randomizarFichas();
    }

    private void randomizarTerrenos() {
        Collections.shuffle(terrernos, rng);
    }

    private void randomizarFichas() {
        Collections.shuffle(fichasNumeradas, rng);
    }
    public Tablero crearTablero() {
        return TableroFactory.crear(terrernos, fichasNumeradas);
    }
//    public void IniciarPartida() {
//        this.Manager= new ManagerTurno(jugadores,this.crearTablero(), rng);
//    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        jugador.suscribirACatan(this);
    }

    @Override
    public void puntajeActualizado( PuntajeDeVictoria puntaje) {
        if (puntaje.total() >= 10) {
            Jugador ganador = buscarJugadorConPuntaje(puntaje);
            terminarPartida(ganador);
        }
    }

    private Jugador buscarJugadorConPuntaje(PuntajeDeVictoria puntaje) {
        for (Jugador jugador : jugadores) {
            if (jugador.mismoPuntaje(puntaje)) {
                return jugador;
            }
        }
        throw new IllegalStateException("No se encontro un jugador con el puntaje dado");
    }

    private void terminarPartida(Jugador jugador) {
        ganador= jugador;
        System.out.println("El jugador " + jugador.getNombre() + " ha ganado la partida !");
    }


    public Jugador hayGanador() {
        if(ganador==null){
            throw new IllegalStateException("No hay ganador aun");
        }
        return ganador;
    }
}
