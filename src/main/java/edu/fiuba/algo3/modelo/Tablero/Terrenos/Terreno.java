package edu.fiuba.algo3.modelo.Tablero.Terrenos;

import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Random;

public abstract class Terreno {
    protected int cantidadMaxima;
    protected int cantidadColocada = 0;
    protected String tipoTerreno;
    protected Produccion produccion;
    protected Hexagono hexagono;


    public String getTipoTerreno(){
        return this.getClass().getSimpleName();
    }

    public boolean esCantidadMaxima(int cantidadMaxima) {
        return  this.cantidadMaxima == cantidadMaxima;
    }

    public boolean esCantidadColocada(int cantidadColocada) {
        return  this.cantidadColocada == cantidadColocada;
    }

    public boolean esTipoTerreno(Terreno tipoTerreno) {
        return  this == tipoTerreno;
    }

    public boolean todosColocados(){
        return cantidadColocada == cantidadMaxima;
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

    public void asignarHexagono(Hexagono hexagono) {
        this.hexagono = hexagono;
    }

    public Hexagono getHexagono() {
        return this.hexagono;
    }
}
