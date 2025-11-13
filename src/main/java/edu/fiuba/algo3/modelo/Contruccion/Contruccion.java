package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

public final class Contruccion {
    final Jugador duenio;
    final TipoConstruccion tipoConstruccion;
    final Integer identificacion;

    public Contruccion(Jugador duenio, TipoConstruccion tipoConstruccion, Integer identificacion) {
        this.duenio = duenio;
        this.tipoConstruccion = tipoConstruccion;
        this.identificacion = identificacion;
    }

}
