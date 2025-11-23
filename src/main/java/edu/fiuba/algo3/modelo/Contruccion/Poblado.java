package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;

public class Poblado implements  Construccion,Productor{
    private Color  color;
    public Poblado(Color color) {
        this.color = color;
    }


    @Override
    public String getColor() {
        return this.color.getColor();
    }

    @Override
    public Color getColorActual() {
        return this.color;
    }

    @Override
    public int obtenerFactorProduccion() {
        return 1;
    }


}
