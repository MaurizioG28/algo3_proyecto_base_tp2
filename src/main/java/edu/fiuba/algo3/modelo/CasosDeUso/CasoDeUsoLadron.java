package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

public class CasoDeUsoLadron {
    private Tablero tablero;
    private Jugador jugador;
    public CasoDeUsoLadron(Tablero tablero, Jugador jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
    }

    public void moverLadron(Jugador actual, int i) {
        tablero.moverLadron(actual, i);
    }
}
