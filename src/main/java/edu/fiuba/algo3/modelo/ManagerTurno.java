package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Vertice;

import java.util.List;


public class ManagerTurno {
    private List<Jugador> jugadores;
    private int indiceJugadorActual = 0;
    private final Tablero tablero;

    public ManagerTurno(List<Jugador> jugadores, Tablero tablero) {
        this.jugadores = jugadores;
        this.tablero = tablero;
    }

    private Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    private void siguienteTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
    }

    public void construirPoblado(Vertice vertice){
        Jugador jugadorActual = getJugadorActual();
        try {
            tablero.construirPoblado(jugadorActual, vertice);
        } catch (ReglaDistanciaException e) {
            System.out.println( e.getMessage());
        }
    }
}
