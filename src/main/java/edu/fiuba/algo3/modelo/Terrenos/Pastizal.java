package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Mineral;

public class Pastizal extends TipoTerreno {

    public Pastizal(){
        super(new Lana());
    }
    @Override public String nombre() { return "Pastizal"; }
}
