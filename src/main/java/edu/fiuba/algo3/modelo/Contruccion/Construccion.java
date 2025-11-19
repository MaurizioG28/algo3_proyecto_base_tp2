package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;

public interface Construccion {
    boolean esPoblado();
    boolean esCiudad();

    String getColor();
    Color getColorActual();
    int obtenerFactorProduccion();
}
