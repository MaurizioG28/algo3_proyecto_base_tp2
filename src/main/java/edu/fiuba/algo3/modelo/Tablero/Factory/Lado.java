package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lado {
    private Jugador propietario;
    private ArrayList<Lado> adyacentes = new ArrayList<>();
    private ArrayList<Vertice> puntas = new ArrayList<>();
    private Construccion construccion = null;
    public boolean tieneConstruccion() {
        return construccion != null;
    }

    public void colocar(Construccion pieza) throws ConstruccionExistenteException {
        if (construccion != null) {
            throw new ConstruccionExistenteException("El vértice ya tiene una construcción.");
        }
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
    public List<Vertice> getPuntas() {
        return Collections.unmodifiableList(puntas);
    }

    public boolean tienePunta(Vertice a) {
        return puntas.contains(a);
    }

    public Vertice getPunta(int i) {
        return puntas.get(i);
    }
}
