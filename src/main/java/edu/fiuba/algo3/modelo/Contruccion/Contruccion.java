package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Jugador;

final class Contruccion {
    final Jugador duenio;
    final TipoConstruccion tipoConstruccion;

    Contruccion(Jugador duenio, TipoConstruccion tipoConstruccion) {
        this.duenio = duenio;
        this.tipoConstruccion = tipoConstruccion;
    }
}
