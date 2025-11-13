package edu.fiuba.algo3.modelo.Tablero;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Random;

public abstract class Terreno {
    protected int cantidadMaxima;
    protected int cantidadColocada = 0;
    protected TipoTerreno tipoTerreno;

    public void agregarTerreno(ArrayList<Hexagono> hexagonos, int[] fichasNuemeradas) {
        if(cantidadColocada >= cantidadMaxima){
            return;
        }
        int fichaSorteada = sortearFicha(fichasNuemeradas);
        Hexagono hexagonoAColocar = new Hexagono(tipoTerreno, fichaSorteada);
        hexagonos.add(hexagonoAColocar);
        cantidadColocada++;
    }

    public boolean todosColocados(){
        return cantidadColocada == cantidadMaxima;
    }

    protected int sortearFicha(int [] fichasNumeradas){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(fichasNumeradas.length);
        int fichaSorteada = fichasNumeradas[numeroAleatorio];

        fichasNumeradas = ArrayUtils.remove(fichasNumeradas, numeroAleatorio);

        return fichaSorteada;
    }
}
