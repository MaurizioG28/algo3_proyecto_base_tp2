package edu.fiuba.algo3.modelo.Mocks;

import java.util.Random;

public class FakeRandom extends Random {
    private final int numero;

    public FakeRandom(int numeroElegido) {
        super();
        this.numero = numeroElegido;
    }

    public int nextInt() {
        return numero;
    }
}
