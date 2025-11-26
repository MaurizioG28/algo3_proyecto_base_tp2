package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.List;

public class PuntajeDeVictoria {
    private int puntosPublicos;
    private int puntosOcultos;

    private List<PuntajeListener> listeners = new ArrayList<>();

    public void addListener(PuntajeListener listener) {
        listeners.add(listener);
    }
    private void notificar() {
        for (var l : listeners) {
            l.puntajeActualizado(this);
        }
    }

    public PuntajeDeVictoria() {
        puntosPublicos=0;
        puntosOcultos=0;
    }

    public void agregarPuntos(int puntos) {
        this.puntosPublicos += puntos;
        notificar();
    }

    public int obtenerPuntos() {
        return this.puntosPublicos;
    }
    public void agregarPuntosOcultos(int puntos) {
        this.puntosOcultos += puntos;
        notificar();
    }
    public void setPuntosPublicos(int puntosPublicos) {
        this.puntosPublicos = puntosPublicos;
        notificar();
    }
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof PuntajeDeVictoria)) return false;
        PuntajeDeVictoria p = (PuntajeDeVictoria) obj;
        return this.puntosPublicos == p.puntosPublicos && this.puntosOcultos == p.puntosOcultos;
    }

    public int getPuntosPublicos() {
        return puntosPublicos;
    }

    public int total() {
        return puntosPublicos + puntosOcultos;
    }

    public void restarPuntosPublicos(int i) {
        this.puntosPublicos -= i;
        notificar();
    }
}
