package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.ReglaDeCompraYUsoException;

public class EstadoCartaRecienComprada implements IEstadoCarta{
    @Override
    public boolean sePuedeUsar() {
        return false;
    }

    @Override
    public IEstadoCarta actualizarEstado() {
        return new EstadoCartaDisponible();
    }

    @Override
    public void comprobarUso() {
        throw new ReglaDeCompraYUsoException("La carta no puede ser usada el mismo turno en el que se compra.");
    }
}
