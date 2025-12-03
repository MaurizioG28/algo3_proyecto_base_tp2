package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

import java.util.List;

public class Carretera implements  Construccion, FichaComprable {
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

    public List<TipoDeRecurso> costoRecursos() {
        return List.of(
                new Madera(1), new Ladrillo(1)
        );
    }

}
