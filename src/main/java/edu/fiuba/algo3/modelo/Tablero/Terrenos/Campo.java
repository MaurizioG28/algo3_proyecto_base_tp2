package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Campo extends Terreno {

    public Campo(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

    }
    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return new Grano(cantidad);
    }
}
