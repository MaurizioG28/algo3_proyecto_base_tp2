package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Tablero.Tablero;

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
    public void construirPoblado(Vertice vertice) {
        Jugador jugadorActual = getJugadorActual();
        tablero.construirPoblado(jugadorActual,vertice);
    }
}
