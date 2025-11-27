package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.ReglaDeCompraYUsoException;

public class EstadoCartaUsada implements IEstadoCarta{
    @Override
    public boolean sePuedeUsar() {
        return false;
    }

    @Override
    public IEstadoCarta actualizarEstado() {
        return this;
    }

    @Override
    public void comprobarUso() {
        throw new ReglaDeCompraYUsoException("Esta carta ya fue utilizada.");
    }
}
