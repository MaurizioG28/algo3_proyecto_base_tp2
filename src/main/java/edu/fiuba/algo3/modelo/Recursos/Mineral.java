package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Mineral extends TipoDeRecurso {

    public Mineral(int cantidad) {
        super(cantidad);
    }

    @Override
    public String nombre() {
        return "Mineral";
    }

    @Override
    public TipoDeRecurso nuevo(int cantidad) {
        return new Mineral(cantidad);
    }

    @Override
    public boolean equals(Object o) {
        // Solo importa el tipo, no la cantidad
        return o instanceof Mineral;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Mineral");
    }
}



