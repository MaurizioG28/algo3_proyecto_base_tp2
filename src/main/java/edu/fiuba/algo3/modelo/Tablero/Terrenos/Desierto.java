package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;

public class Desierto extends Terreno {

    public Desierto(){
        super();
        tipoTerreno = this.getClass().getSimpleName();
        cantidadMaxima = 1;
        produccion = new Produccion(0);
    }



    @Override
    public boolean esDesierto(){
        return true;
    }
}
