package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.interfaces.IDado;

import java.util.Random;

public class Dados implements IDado {
    private int generarNumero() {
        Random random = new Random();
        return random.nextInt(6) + 1;  // 1 a 6
    }

    public int tirar() {
        int d1 = generarNumero();
        int d2 = generarNumero();
        int total = d1 + d2;

        return total;
    }

}
