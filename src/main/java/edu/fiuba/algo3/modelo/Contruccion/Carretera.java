package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;

public class Carretera implements  Construccion{
    private Color  color;
    public Carretera(Color color) {
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


}
