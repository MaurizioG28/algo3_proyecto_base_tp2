package edu.fiuba.algo3.modelo.Tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dados {
    int dado1;
    int dado2;
    private int generarNumero() {
        Random random = new Random();
        return random.nextInt(6) + 1;  // 1 a 6
    }

    public int tirar() {
        int dado1 = generarNumero();
        int dado2 = generarNumero();
        List<Integer> dados = new ArrayList<>();
        dados.add(dado1);
        dados.add(dado2);
        int suma = dado1 + dado2;
        return suma;
    }
    public int getDado1() {
        return dado1;
    }
    public int getDado2() {
        return dado2;
    }

}
