package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Grano implements TipoDeRecurso {

    @Override
    public String nombre() {
        return "Grano";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Grano;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Grano");
    }

    @Override
    public String toString() {
        return nombre();
    }
}
