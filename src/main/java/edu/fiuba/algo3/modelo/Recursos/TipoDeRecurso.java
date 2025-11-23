package edu.fiuba.algo3.modelo.Recursos;

public abstract class TipoDeRecurso {
    protected int cantidad;

    protected TipoDeRecurso(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.cantidad = cantidad;
    }

    /** Cantidad acumulada de este recurso. */
    public int obtenerCantidad() {
        return cantidad;
    }

    /** Suma cantidad a este recurso. */
    public void sumar(int delta) {
        if (delta < 0) throw new IllegalArgumentException("No se puede sumar negativo");
        this.cantidad += delta;
    }

    /** Resta cantidad a este recurso. */
    public void restar(int delta) {
        if (delta < 0) throw new IllegalArgumentException("No se puede restar negativo");
        if (delta > this.cantidad) {
            throw new IllegalStateException("No hay suficiente " + nombre());
        }
        this.cantidad -= delta;
    }

    public abstract TipoDeRecurso nuevo(int cantidad);

    public abstract String nombre();

    @Override
    public String toString() {
        return nombre();
    }
}





