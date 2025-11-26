package edu.fiuba.algo3.modelo.Cartas;

public class CartaDesarrollo {
    private int TurnoCreacion;

    public CartaDesarrollo(int turno) {
        this.TurnoCreacion = turno;
    }

    public boolean SePuedeUsar(int turno) {
        return !(TurnoCreacion == turno);
    }


}
