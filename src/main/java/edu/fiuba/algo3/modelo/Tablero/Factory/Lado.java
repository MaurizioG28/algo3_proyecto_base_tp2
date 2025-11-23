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

    public void agregarAdyacente(Lado lado) {
        if (lado != this && !adyacentes.contains(lado)) {
            adyacentes.add(lado);

            if (!lado.esLadoAdyacenteA(this)) {
                lado.agregarAdyacente(this);
            }
        }
        ;
    }

    private boolean esLadoAdyacenteA(Lado lado) {
        return adyacentes.contains(lado);
    }

    public void agregarPunta(Vertice v) {
        if (!puntas.contains(v)) {
            puntas.add(v);
        }
    }

    public boolean esLadoAdyacente(Lado l1) {
        return adyacentes.contains(l1);
    }
}
