package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Contruccion.TipoConstruccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;

public class Hexagono {
    private final TipoTerreno tipo;
    private final int numero; // 2..12 (no produce con 7)
    private boolean bloqueadoPorLadron = false;
    private final List<Vertice> vertices = new ArrayList<>(6);

    public Hexagono(TipoTerreno tipo, int numero) {
        this.tipo = tipo;
        this.numero = numero;
        if (this.tipo == DESIERTO) {
            this.bloqueadoPorLadron = true;
        }
    }

    public void moverLadron() {
        bloqueadoPorLadron = !bloqueadoPorLadron;
    }

    public boolean sePuedeProducir() {
        return (!bloqueadoPorLadron);
    }
    public boolean isBloqueadoPorLadron() {
        return (bloqueadoPorLadron);
    }
    public TipoTerreno getTipo() { return tipo; }
    public int getNumero() { return numero; }

    public List<Vertice> getVertices() { return vertices; }

    public void agregarVertice(Vertice v) { this.vertices.add(v); }

}
