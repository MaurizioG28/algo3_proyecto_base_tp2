package edu.fiuba.algo3.modelo.Tablero.Factory;

public class Produccion {
    private int numero;

    public Produccion(int numero){
        this.numero = numero;
    }

    @Override
    public boolean equals(Object object){
        if(this.getClass() !=  object.getClass()){return false;}
        return ((Produccion)object).tieneMismoNumero(this.numero);
    }

    public boolean tieneMismoNumero(int numero) {
        return this.numero == numero;
    }
}
