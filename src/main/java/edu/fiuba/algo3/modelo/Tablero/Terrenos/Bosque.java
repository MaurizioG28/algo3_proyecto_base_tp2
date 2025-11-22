package edu.fiuba.algo3.modelo.Tablero.Terrenos;

public class Bosque extends Terreno {

    public Bosque(){
        super();

        cantidadMaxima = 4;
    }

    @Override
    public String getTipoTerreno() {
        return "Bosque";
    }
}
