package edu.fiuba.algo3.modelo;

public class PuntajeDeVictoria {
    private int puntosPublicos;
    private int puntosOcultos;

    public PuntajeDeVictoria() {
        puntosPublicos=0;
        puntosOcultos=0;
    }

    public void agregarPuntos(int puntos) {
        this.puntosPublicos += puntos;
    }

    public int obtenerPuntos() {
        return this.puntosPublicos + this.puntosOcultos;
    };
    public void agregarPuntosOcultos(int puntos) {
        this.puntosOcultos += puntos;
    }
    public void setPuntosPublicos(int puntosPublicos) {
        this.puntosPublicos = puntosPublicos;
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
}
