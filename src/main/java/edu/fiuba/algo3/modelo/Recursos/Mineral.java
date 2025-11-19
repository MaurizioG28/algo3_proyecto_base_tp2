package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Mineral implements TipoDeRecurso{
    @Override
    public String nombre() {
        return "Mineral";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Mineral;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Mineral");
    }

    @Override
    public String toString() {
        return nombre();
    }
}
