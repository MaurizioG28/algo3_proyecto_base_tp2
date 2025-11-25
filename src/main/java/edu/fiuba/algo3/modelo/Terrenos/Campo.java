package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Grano;

public class Campo extends TipoTerreno {

    public Campo(){
        super(new Grano(0));

    }

    @Override
    public String nombre() {
        return "Campo";
    }
}
