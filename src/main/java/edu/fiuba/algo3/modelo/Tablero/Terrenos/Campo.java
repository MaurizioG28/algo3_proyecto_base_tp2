package edu.fiuba.algo3.modelo.Tablero.Terrenos;

public class Campo extends Terreno {

    public Campo(){
        super();
        tipoTerreno = this.getClass().getSimpleName();
        cantidadMaxima = 4;
    }
}
