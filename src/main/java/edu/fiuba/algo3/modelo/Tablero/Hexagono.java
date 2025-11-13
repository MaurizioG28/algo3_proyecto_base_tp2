package edu.fiuba.algo3.modelo.Tablero;

import java.util.HashMap;

import edu.fiuba.algo3.modelo.Contruccion.Contruccion;

import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;

public class Hexagono {
    private final TipoTerreno tipo;
    private final int numero; // 2..12 (no produce con 7)
    private HashMap<Integer, Contruccion> viviendas = new HashMap<>(); // 0..6
    private HashMap<Integer, Contruccion> caminos = new HashMap<>(); // 0..6
    private boolean bloqueadoPorLadron = false;

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

    public void agregarCaminos(Contruccion camino, Integer identificacion) {
        viviendas.put(identificacion, camino);
    }

    public void producirRecursos() {
        if (!bloqueadoPorLadron) {
            viviendas.forEach((clave, construccion) -> {
                construccion.recolectarRecurso(tipo.recursoOtorgado());
            });
        }
    }
    public boolean sePuedeProducir() {
        return (!bloqueadoPorLadron);
    }

}
