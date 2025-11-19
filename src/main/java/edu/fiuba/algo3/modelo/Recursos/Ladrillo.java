package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Ladrillo implements TipoDeRecurso {

    @Override
    public String nombre() {
        return "Ladrillo";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Ladrillo;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Ladrillo");
    }

    @Override
    public String toString() {
        return nombre();
    }
}
