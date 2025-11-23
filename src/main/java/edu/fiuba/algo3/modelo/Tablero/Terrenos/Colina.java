package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Colina extends Terreno {

    public Colina(){
        super();
        tipoTerreno = this.getClass().getSimpleName();

    }
    @Override
    public TipoDeRecurso recursoOtorgado(Integer cantidad) {
        return new Ladrillo(cantidad);
    }
}
