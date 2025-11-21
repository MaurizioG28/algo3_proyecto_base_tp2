package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Ladrillo extends TipoDeRecurso {

    public Ladrillo(int cantidad) {
        super(cantidad);
    }

    @Override
    public String nombre() {
        return "Ladrillo";
    }

    @Override
    public TipoDeRecurso nuevo(int cantidad) {
        return new Ladrillo(cantidad);
    }

    @Override
    public boolean equals(Object o) {
        // Solo importa el tipo, no la cantidad
        return o instanceof Ladrillo;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Ladrillo");
    }
}
