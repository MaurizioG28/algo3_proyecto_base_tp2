package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

public class Lana extends RecursoBase{

    public Lana(int cantidad) {
        super(cantidad);
    }
    @Override
    public Recurso tipo() {
        return Recurso.LANA;
    }
}
