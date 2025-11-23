package edu.fiuba.algo3.modelo.Tablero.Terrenos;

public class Montania extends Terreno {

    public Montania(){
        super();
        tipoTerreno = this.getClass().getSimpleName();
        cantidadMaxima = 3;
    }
}
