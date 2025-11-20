package edu.fiuba.algo3.modelo.Tablero;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Random;

public abstract class Terreno {
    protected int cantidadMaxima;
    protected int cantidadColocada = 0;
    protected TipoTerreno tipoTerreno;
    protected Produccion produccion;

    public void agregarTerreno(ArrayList<Hexagono> hexagonos, int[] fichasNumeradas) {
        if(cantidadColocada >= cantidadMaxima){
            return;
        }
        int fichaSorteada = sortearFicha(fichasNumeradas);
        Hexagono hexagonoAColocar = new Hexagono(tipoTerreno, fichaSorteada);
        hexagonos.add(hexagonoAColocar);
        cantidadColocada++;
    }

    public boolean esCantidadMaxima(int cantidadMaxima) {
        return  this.cantidadMaxima == cantidadMaxima;
    }

    public boolean esCantidadColocada(int cantidadColocada) {
        return  this.cantidadColocada == cantidadColocada;
    }

    public boolean esTipoTerreno(TipoTerreno tipoTerreno) {
        return  this.tipoTerreno == tipoTerreno;
    }

    public boolean todosColocados(){
        return cantidadColocada == cantidadMaxima;
    }

    protected int sortearFicha(int[] fichasNumeradas){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(fichasNumeradas.length);
        int fichaSorteada = fichasNumeradas[numeroAleatorio];

        fichasNumeradas = ArrayUtils.remove(fichasNumeradas, numeroAleatorio);

        return fichaSorteada;
    }

    public void setProduccion(Produccion produccion) {
        this.produccion = produccion;
    }

    public boolean esDesierto() {
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if(this.getClass() != object.getClass()){return false;}
        return mismaProduccion(((Terreno) object).getProduccion());
    }

    private Produccion getProduccion() {
        return this.produccion;
    }

    public boolean mismaProduccion(Produccion produccion){
        return  this.produccion.equals(produccion);
    }
}
