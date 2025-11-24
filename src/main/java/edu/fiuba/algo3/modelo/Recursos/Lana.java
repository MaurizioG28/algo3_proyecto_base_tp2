package edu.fiuba.algo3.modelo.Recursos;

import java.util.Objects;

public class Lana extends TipoDeRecurso{

    public Lana(int cantidad) {
        super(cantidad);
    }
    public Lana() {
        this(0);

    }

    @Override
    public String nombre() {
        return "Lana";
    }

    @Override
    public TipoDeRecurso nuevo(int cantidad) {
        return new Lana(cantidad);
    }

    @Override
    public boolean equals(Object o) {
        // Solo importa el tipo, no la cantidad
        return o instanceof Lana;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Lana");
    }
}
