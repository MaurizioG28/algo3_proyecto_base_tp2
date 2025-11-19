package edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.Contruccion.Contruccion;
import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;


public class Hexagono {
    private final TipoTerreno tipo;
    private final int numero; // 2..12 (no produce con 7)
    private HashMap<Integer, Contruccion> viviendas = new HashMap<>(); // 0..6
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

    public void agregarVivienda(Contruccion vivienda, Integer identificacion) {
        viviendas.put(identificacion, vivienda);
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
