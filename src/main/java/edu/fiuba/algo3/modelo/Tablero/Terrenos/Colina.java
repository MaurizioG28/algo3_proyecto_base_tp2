package edu.fiuba.algo3.modelo.Tablero.Terrenos;

public class Colina extends Terreno {

    public Colina(){
        super();
        tipoTerreno = this.getClass().getSimpleName();
        cantidadMaxima = 3;
    }
}
