package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Montania extends Terreno {

    public Montania(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

    }
    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return new Mineral(cantidad);
    }
}
