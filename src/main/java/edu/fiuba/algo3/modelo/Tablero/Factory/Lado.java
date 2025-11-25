package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lado {
    private Color propietario;
    private ArrayList<Lado> adyacentes = new ArrayList<>();
    private ArrayList<Vertice> puntas = new ArrayList<>();
    private Construccion construccion = null;
    public boolean tieneConstruccion() {
        return construccion != null;
    }

    public void colocar(Construccion pieza) throws ConstruccionExistenteException, ReglaConstruccionException {
        if (construccion != null) {
            throw new ConstruccionExistenteException("El vértice ya tiene una construcción.");
        }
        for (Vertice v : puntas) {
            if (v.tieneConstruccion()) {
                Color propietario = v.getPropietario();
                if (!propietario.equals(pieza.getColorActual())) {
                    throw new ReglaConstruccionException("Hay una construcción enemiga bloqueando este lado.");
                }
            }
        }

        // 3. Debe haber conexión con una red propia (poblado propio o carretera adyacente del mismo color)
        if (!hayConexionCon(pieza.getColorActual())) {
            throw new ReglaConstruccionException("La carretera debe conectarse con tu red.");
        }


        this.construccion = pieza;
        this.propietario = pieza.getColorActual();
    }


    private boolean hayConexionCon(Color colorActual) {

        // A. Verificar si alguna punta tiene construcción del mismo color
        for (Vertice v : puntas) {
            if (v.tieneConstruccion() &&
                    v.getPropietario().equals(colorActual)) {
                return true;
            }
        }

        // B. Verificar si alguna carretera adyacente es del mismo color
        for (Lado lado : adyacentes) {
            if (lado.tieneConstruccion()) {
                Color c = lado.getPropietario();
                if (c.equals(colorActual)) {
                    return true; // conectado vía carretera propia
                }
            }
        }

        return false;
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

    public Color getPropietario() {
        if (construccion != null) {
            return this.propietario;
        }
        return null;
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
