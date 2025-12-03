package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

import java.util.List;

public class Poblado implements  Construccion,Productor, FichaComprable {
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

    public List<TipoDeRecurso> costoRecursos() {
        return List.of(
              new Madera(1), new Ladrillo(1), new Lana(1), new Grano(1)
        );
    }

}
