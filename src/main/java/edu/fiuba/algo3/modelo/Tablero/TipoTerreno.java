package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Recursos.*;

import java.util.function.Supplier;

public enum TipoTerreno {
    BOSQUE(() -> new Madera(1)),
    COLINA(() -> new Ladrillo(1)),
    PRADERA(() -> new Lana(1)),
    CAMPO(() -> new Grano(1)),
    MONTANIA(() -> new Mineral(1)),
    DESIERTO(null);
    private final Supplier<RecursoBase> fabrica;

    TipoTerreno(Supplier<RecursoBase> fabrica) {
        this.fabrica = fabrica;
    }

    public boolean produceAlgo() {
        return fabrica != null;
    }
    public RecursoBase recursoOtorgado() {
        return (fabrica == null) ? null : fabrica.get();
    }

}