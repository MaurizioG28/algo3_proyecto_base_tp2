package edu.fiuba.algo3.modelo.Tablero;

public class Desierto extends Terreno {

    public Desierto(){
        super();
        tipoTerreno = TipoTerreno.DESIERTO;
        cantidadMaxima = 1;
        produccion = new Produccion(0);
    }

    @Override
    protected int sortearFicha(int[] fichasNumeradas){
        return 0;
    }

    @Override
    public boolean esDesierto(){
        return true;
    }
}
