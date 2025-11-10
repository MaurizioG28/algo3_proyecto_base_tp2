package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Recurso;

public enum TipoTerreno {
    BOSQUE(Recurso.MADERA), COLINA(Recurso.LADRILLO),
    PRADERA(Recurso.LANA), CAMPO(Recurso.GRANO),
    MONTANIA(Recurso.MINERAL), DESIERTO(null);
    private final Recurso recurso;

    TipoTerreno(Recurso recurso) {
        this.recurso = recurso;
    }

    public boolean produceAlgo() {
        return recurso != null;
    }
    public Recurso recursoOtorgado() { return recurso; }

}