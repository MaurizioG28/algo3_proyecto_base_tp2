package edu.fiuba.algo3.modelo.Cartas;

public class PuntoDeVictoria extends CartaDesarrollo implements CartaProductora{

    public PuntoDeVictoria(int turno) {
        super(turno);
    }

    public int obtenerCantidadPV() {
        return 1;
    }
}
