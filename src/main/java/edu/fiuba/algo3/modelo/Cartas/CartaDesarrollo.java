package edu.fiuba.algo3.modelo.Cartas;

abstract public class CartaDesarrollo {
    private IEstadoCarta estado;
    public  CartaDesarrollo() {
        this.estado = new EstadoCartaRecienComprada();
    }
    protected CartaDesarrollo(IEstadoCarta estado) {
        this.estado = estado;
    }
    public void nuevoTurno() {
        this.estado = this.estado.actualizarEstado();
    }

    public void usar() {
        this.estado.comprobarUso();
    }
}
