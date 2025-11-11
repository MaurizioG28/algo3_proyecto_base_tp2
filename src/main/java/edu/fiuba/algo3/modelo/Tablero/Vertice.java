package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public class Vertice {

    private Jugador propietario;
    private ArrayList<Vertice> adyacentes = new ArrayList<>();

    public boolean tieneConstruccion() {
        return propietario != null;
    }

    public boolean tieneConstruccionAdyacente() {
        for (Vertice v : adyacentes) {
            if (v.tieneConstruccion()) return true;
        }
        return false;
    }

    public void colocarConstruccion(Jugador jugador) {
        this.propietario = jugador;
    }
}
