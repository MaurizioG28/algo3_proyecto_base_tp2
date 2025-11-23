package edu.fiuba.algo3.modelo.Tablero.Terrenos;

public class Pastizal extends Terreno {

    public Pastizal(){
        super();
        tipoTerreno = this.getClass().getSimpleName();
        cantidadMaxima = 4;
    }
}
