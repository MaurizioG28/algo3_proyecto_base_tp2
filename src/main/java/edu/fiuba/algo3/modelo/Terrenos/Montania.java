package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Mineral;

public class Montania extends TipoTerreno {

    public Montania(){
        super(new Mineral(0));
    }
    @Override public String nombre() { return "MONTANIA"; }
}
