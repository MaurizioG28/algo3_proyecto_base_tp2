package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Bosque extends TipoTerreno {

    public Bosque(){
        super(new Madera(0));

    }

    @Override
    public String nombre() {
        return "BOSQUE";
    }
}
