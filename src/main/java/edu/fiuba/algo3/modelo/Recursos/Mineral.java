package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

public class Mineral extends RecursoBase {

    public Mineral(int cantidad) {
        super(cantidad);
    }
    @Override
    public Recurso tipo() {
        return Recurso.MINERAL;
    }
}
