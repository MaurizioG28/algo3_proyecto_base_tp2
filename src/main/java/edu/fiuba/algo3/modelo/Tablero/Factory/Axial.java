package edu.fiuba.algo3.modelo.Tablero.Factory;

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
}
