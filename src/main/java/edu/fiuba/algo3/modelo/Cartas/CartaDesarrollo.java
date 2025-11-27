package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;

abstract public class CartaDesarrollo {
    private int TurnoCreacion;

    public CartaDesarrollo(int turno) {
        this.TurnoCreacion = turno;
    }

    public boolean SePuedeUsar(int turno) {
        return !(TurnoCreacion == turno);
    }

}
