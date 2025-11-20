package edu.fiuba.algo3.modelo.Recursos;

import edu.fiuba.algo3.modelo.Recurso;

public class Ladrillo extends  RecursoBase{
    public Ladrillo(int cantidad) {
        super(cantidad);
    }

    @Override
    public Recurso tipo() {
        return Recurso.LADRILLO;
    }
}
