package edu.fiuba.algo3.modelo.Mocks;

import edu.fiuba.algo3.modelo.ICelda;

public class FakeCelda implements ICelda {
    private final Recurso recurso;

    public FakeCelda(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public Recurso darRecurso() {
        return recurso; // lógica mínima pero real
    }
}
