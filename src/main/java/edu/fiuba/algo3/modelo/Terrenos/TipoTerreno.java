package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public abstract class TipoTerreno {

    private final TipoDeRecurso recurso;



    protected TipoTerreno(TipoDeRecurso recurso) {
        this.recurso = recurso;
    }

    public TipoDeRecurso recursoOtorgado() {
        return recurso;
    }

    public boolean produceAlgo() {
        return recurso != null;
    }

    public abstract String nombre();
}