package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class OrdenTurnosIniciales {
    private final List<Integer> orden;
    private int indice = 0;
    private boolean finalizado = false;

    public OrdenTurnosIniciales(int cantidadJugadores) {
        orden = new ArrayList<>();

        // Primera vuelta
        for (int i = 0; i < cantidadJugadores; i++) {
            orden.add(i);
        }
        // Segunda vuelta (invertida)
        for (int i = cantidadJugadores - 1; i >= 0; i--) {
            orden.add(i);
        }
    }

    public boolean haTerminado() {
        return indice >= orden.size();
    }

    public int indiceJugadorActual() {
        return orden.get(indice);
    }

    public void avanzar() {
        indice++;
    }

    public int tamanio() {
        return orden.size();
    }

    public boolean esSegundoPoblado() {
        return indice >= ((orden.size())/2);
    }
}
