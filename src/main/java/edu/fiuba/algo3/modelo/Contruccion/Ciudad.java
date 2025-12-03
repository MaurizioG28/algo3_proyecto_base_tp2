package edu.fiuba.algo3.modelo.Contruccion;


import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

import java.util.List;

public class Ciudad implements Construccion,Productor, FichaComprable {
    private Color  color;
    public Ciudad(Color color) {
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
        return 2;
    }

    public List<TipoDeRecurso> costoRecursos() {
        return List.of(
                new Grano(2), new Mineral(3)
        );
    }
}
