package edu.fiuba.algo3.modelo.Terrenos;

import edu.fiuba.algo3.modelo.Recursos.Ladrillo;

public class Colina extends TipoTerreno {

    public Colina(){
        super(new Ladrillo());
    }
    @Override
    public String nombre() { return "COLINA"; }
}
