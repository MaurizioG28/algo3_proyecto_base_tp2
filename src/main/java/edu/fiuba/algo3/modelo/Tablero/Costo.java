package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Recurso;
import java.util.Map;

public class Costo {

    private final Map<Recurso, Integer> requeridos;

    public Costo(Map<Recurso, Integer> requeridos) {
        this.requeridos = Map.copyOf(requeridos);
    }


    public boolean esIgualA(Costo otro) {
        return this.requeridos.equals(otro.requeridos);
    }

    public Map<Recurso, Integer> requeridos() {
        return requeridos;
    }
}

