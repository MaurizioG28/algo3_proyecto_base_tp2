package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public class Lado {
    private Jugador propietario;
    private ArrayList<Lado> adyacentes = new ArrayList<>();
    private ArrayList<Vertice> puntas = new ArrayList<>();
    private Construccion construccion = null;
    public boolean tieneConstruccion() {
        return construccion != null;
    }

    public void colocar(Construccion pieza) {
        this.construccion = pieza;
    }

    public void agregarAdyacente(Lado l) {
        adyacentes.add(l);
    }

    public void agregarPunta(Vertice v) {
        puntas.add(v);
    }
}
