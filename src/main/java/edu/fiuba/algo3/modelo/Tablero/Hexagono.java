package edu.fiuba.algo3.modelo.Tablero;

import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;

public class Hexagono {
    private final TipoTerreno tipo;
    private final int numero; // 2..12 (no produce con 7)
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

    public boolean sePuedeProducir() {
        return (!bloqueadoPorLadron);
    }

}
