package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.interfaces.ICamino;

public class Camino implements ICamino {
    private final Jugador duenio;

    public Camino(Jugador jugador) {
        this.duenio = jugador;
    }
}

