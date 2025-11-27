package edu.fiuba.algo3.modelo.Cartas;

public interface IEstadoCarta {
    boolean sePuedeUsar();
    IEstadoCarta actualizarEstado();

    void comprobarUso();
}
