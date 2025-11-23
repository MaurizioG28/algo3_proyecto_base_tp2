package edu.fiuba.algo3.modelo.Tablero.Factory;

import java.util.Objects;

public class Cubic {
    public final int x, y, z;

    public Cubic(int x, int y, int z) {
        if (x + y + z != 0) throw new IllegalArgumentException("Cubic no es x+y+z=0");

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cubic add(Cubic o) {
        return new Cubic(x + o.x, y + o.y, z + o.z);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cubic)) return false;
        Cubic c = (Cubic) o;
        return x == c.x && y == c.y && z == c.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
