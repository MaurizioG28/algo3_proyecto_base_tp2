package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

import java.util.Objects;

public abstract class RecursoBase {
    protected final int cantidad;

    public RecursoBase(int cantidad) {
        this.cantidad = cantidad;
    }

    public int cantidad() {
        return cantidad;
    }

    public abstract Recurso tipo();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecursoBase)) return false;
        RecursoBase otro = (RecursoBase) o;
        return this.cantidad == otro.cantidad &&
                this.tipo() == otro.tipo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo(), cantidad);
    }
}
