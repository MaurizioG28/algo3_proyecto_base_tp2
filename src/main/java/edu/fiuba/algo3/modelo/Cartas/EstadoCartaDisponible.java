package edu.fiuba.algo3.modelo.Cartas;

public class EstadoCartaDisponible implements IEstadoCarta{
    @Override
    public boolean sePuedeUsar() {
        return true;
    }

    @Override
    public IEstadoCarta actualizarEstado() {
        return new EstadoCartaUsada();
    }

    @Override
    public void comprobarUso() {
    }
}
