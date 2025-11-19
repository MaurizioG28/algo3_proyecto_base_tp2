package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Madera implements TipoDeRecurso {

    @Override
    public String nombre() {
        return "Madera";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Madera;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Madera");
    }

    @Override
    public String toString() {
        return nombre();
    }
}
