package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Pastizal extends Terreno {

    public Pastizal(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

    }
    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return new Lana(cantidad);
    }
}
