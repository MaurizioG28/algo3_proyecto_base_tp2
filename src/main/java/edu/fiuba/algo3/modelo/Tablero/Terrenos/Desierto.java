package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;

public class Desierto extends Terreno {

    public Desierto(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

        produccion = new Produccion(0);
    }

    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return null;
    }

    @Override
    public boolean esDesierto(){
        return true;
    }

    @Override
    public void producirRecurso() {

    }
}
