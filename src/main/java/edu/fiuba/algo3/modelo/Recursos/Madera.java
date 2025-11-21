package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;



public class Madera extends TipoDeRecurso {

    public Madera(int cantidad) {
        super(cantidad);
    }

    @Override
    public TipoDeRecurso nuevo(int cantidad) {
        return new Madera(cantidad);
    }

    @Override
    public String nombre() {
        return "Madera";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Madera;  // solo importa el tipo
    }

    @Override
    public int hashCode() {
        return Objects.hash("Madera");
    }
}

