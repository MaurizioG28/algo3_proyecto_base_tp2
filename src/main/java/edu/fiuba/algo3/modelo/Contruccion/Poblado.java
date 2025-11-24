package edu.fiuba.algo3.modelo.Contruccion;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;

public class Poblado implements  Construccion,Productor{
    private Color  color;
    public Poblado(Color color) {
        this.color = color;
    }
    private Jugador propietario;

    public Poblado(Jugador propietario) {
        this.propietario = propietario;
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
    public void produci() {
         propietario.agregarRecurso(new Madera(1));
    }

    @Override
    public int obtenerFactorProduccion() {
        return 1;
    }


}
