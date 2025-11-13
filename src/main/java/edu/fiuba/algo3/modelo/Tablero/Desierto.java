package edu.fiuba.algo3.modelo.Tablero;

public class Desierto extends Terreno {

    public Desierto(){
        super();
        tipoTerreno = TipoTerreno.DESIERTO;
        cantidadMaxima = 1;
    }

    @Override
    protected int sortearFicha(int[] fichasNumeradas){
        return 0;
    }
}
