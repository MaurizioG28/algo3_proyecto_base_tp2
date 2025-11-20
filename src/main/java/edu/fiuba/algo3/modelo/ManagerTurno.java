package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Vertice;

import java.util.List;
import java.util.Random;


public class ManagerTurno {
    private final List<Jugador> jugadores;
    private int indiceJugadorActual = 0;
    private final Tablero tablero;
    private final Random azar;

    public ManagerTurno(List<Jugador> jugadores, Tablero tablero, Random Random) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.azar=  Random;
    }

    private Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    private void siguienteTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }

    public void construirPoblado(IVertice vertice){
        Jugador jugadorActual = getJugadorActual();
        try {
            tablero.construirPoblado(jugadorActual, vertice);
        } catch (ReglaDistanciaException e) {
            System.out.println( e.getMessage());
        }
    }

    public void moverLadron(Hexagono posicion){
        Jugador jugadorActual = getJugadorActual();
        List<Jugador> victimas= tablero.moverLadron(jugadorActual, posicion);

        if(!victimas.isEmpty()){

            Jugador victima = victimas.get(azar.nextInt(victimas.size()));
            //Selecciona una victima al azar por ahora, depues vemos como hacer para que el jugador elija desde la interfaz
            jugadorActual.robarRecurso(victima);
        }

    }

}
