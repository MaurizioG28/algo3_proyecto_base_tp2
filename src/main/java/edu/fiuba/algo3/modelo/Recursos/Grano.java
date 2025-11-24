package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Grano extends TipoDeRecurso {


    public Grano(int cantidad) {
        super(cantidad);
    }
    public Grano() {
        this(0);

    }

    @Override
    public String nombre() {
        return "Grano";
    }

    @Override
    public TipoDeRecurso nuevo(int cantidad) {
        return new Grano(cantidad);
    }

    @Override
    public boolean equals(Object o) {
        // Solo importa el tipo, no la cantidad
        return o instanceof Grano;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Grano");
    }
}
