package edu.fiuba.algo3.modelo.Tablero.Factory;

import java.util.Objects;

public class Cubic {
    public final int q, r, s;

    public Cubic(int q, int r, int s) {
        this.q = q;
        this.r = r;
        this.s = s;
    }

    public Cubic add(Cubic other) {
        return new Cubic(q + other.q, r + other.r, s + other.s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cubic)) return false;
        Cubic cubic = (Cubic) o;
        return q == cubic.q && r == cubic.r && s == cubic.s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r, s);
    }

    @Override
    public String toString() {
        return "Cubic(" + q + "," + r + "," + s + ")";
    }
}
