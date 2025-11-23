package edu.fiuba.algo3.modelo.Tablero.Factory;

import java.util.Objects;

public class Axial {
    public final int q, r;

    public Axial(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public Cubic toCubic() {
        int x = q;
        int z = r;
        int y = -x - z;
        return new Cubic(x, y, z);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Axial)) return false;
        Axial axial = (Axial) o;
        return q == axial.q && r == axial.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }

    @Override
    public String toString() {
        return "Axial(" + q + "," + r + ")";
    }
}
