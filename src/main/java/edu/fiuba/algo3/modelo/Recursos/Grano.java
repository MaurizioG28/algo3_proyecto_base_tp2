package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

public class Grano extends RecursoBase{
    public Grano(int cantidad) {
        super(cantidad);
    }
    @Override
    public Recurso tipo() {
        return Recurso.GRANO;
    }
}
