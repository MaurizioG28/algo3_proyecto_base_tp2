package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Bosque extends Terreno {

    public Bosque(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

    }

    @Override
    public String getTipoTerreno() {
        return "Bosque";
    }

    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return new Madera(cantidad);
    }

}
