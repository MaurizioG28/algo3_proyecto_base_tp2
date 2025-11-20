package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

public class Madera extends RecursoBase {
    public Madera(int cantidad) {
        super(cantidad);
    }
    @Override
    public Recurso tipo() {
        return Recurso.MADERA;
    }
}
