package edu.fiuba.algo3.modelo.Tablero;

public class Campo extends Terreno {

    public Campo(){
        super();
        tipoTerreno = TipoTerreno.CAMPO;
        cantidadMaxima = 4;
    }
}
