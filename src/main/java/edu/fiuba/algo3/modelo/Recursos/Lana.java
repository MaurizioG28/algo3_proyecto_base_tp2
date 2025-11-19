package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Lana implements TipoDeRecurso{
    @Override
    public String nombre() {
        return "Lana";
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Lana;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Lana");
    }

    @Override
    public String toString() {
        return nombre();
    }
}
