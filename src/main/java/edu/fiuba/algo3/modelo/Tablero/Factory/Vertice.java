package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Contruccion.Productor;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;

import java.util.ArrayList;

public class Vertice {
    private ArrayList<Vertice> adyacentes = new ArrayList<>();
    private Construccion tipo = null;

    public boolean tieneConstruccion() {
        return (tipo != null || tipo != null);
    }

    public boolean tieneConstruccionAdyacente() {
        for (Vertice v : adyacentes) {
            if (v.tieneConstruccion()) return true;
        }
        return false;
    }
    public boolean pobladoAyacente(Color color) {
        if (tipo != null) {
            if (tipo.getColorActual().esMismoColor(color)) {
                return true;
            }
        }
        return false;
    }

    public void colocarPoblado(Jugador jugador) throws ConstruccionExistenteException {
        for (Vertice vertice : adyacentes) {
            if (vertice.tipo != null) {
                throw new ConstruccionExistenteException("No se puede construir: vértice adyacente ocupado (regla de distancia 1)");
            }
        }
        colocarConstruccion(new Poblado(jugador));

    }


    public void mejorarACiudad() {
        if (this.tipo.getColor() == null) throw new IllegalStateException("No hay poblado para mejorar");
        String color = this.tipo.getColor();
        this.tipo = new Ciudad(new Color(color));
    }


    public Construccion getTipoConstruccion() {
        return tipo;
    }

    public void agregarAdyacente(Vertice vertice) {
        if (vertice == this) return; // No auto-adyacencia
        if (adyacentes.contains(vertice)) return; // Ya existe
        if (adyacentes.size() >= 3) {
            throw new IllegalStateException("Vértice no puede tener más de 3 adyacentes");
        }

        adyacentes.add(vertice);
        // Conexión bidireccional OPcional - si la quitas, quita esta parte
        if (!vertice.esVerticeAdyacente(this)) {
            vertice.agregarAdyacente(this);
        }
    }

    public boolean esVerticeAdyacente(Vertice otroVertice) {
        return this.adyacentes.contains(otroVertice);
    }

    public void colocarConstruccion(Construccion pieza) throws ConstruccionExistenteException {
        if (this.tipo != null) {
            throw new ConstruccionExistenteException("El vértice ya tiene una construcción.");
        }
        this.tipo = pieza;

    }

    public Integer factorProduccion() {
        if (tipo instanceof Productor) {
            return ((Productor) tipo).obtenerFactorProduccion();
        }
        return 0;
    }

    public void construir(Poblado poblado) {
    }

    public void produci(int valor) {
        if (tipo instanceof Poblado) {
            tipo.produci();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}