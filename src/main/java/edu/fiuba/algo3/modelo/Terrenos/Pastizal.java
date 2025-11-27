package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Mineral;

public class Pastizal extends TipoTerreno {

    public Pastizal(){
        super(new Lana(0));
    }
    @Override public String nombre() { return "Pastizal"; }
}
