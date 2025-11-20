package edu.fiuba.algo3.modelo.Tablero;

import java.util.Objects;

public class Coordenada {

    private final int hex;
    private final int indice;

    public Coordenada(int hex, int indice) {
        this.hex = hex;
        this.indice = indice;
    }

    public int numHex() { return hex; }
    public int indice() { return indice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordenada)) return false;
        Coordenada c = (Coordenada) o;
        return hex == c.hex && indice == c.indice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hex, indice);
    }
}
