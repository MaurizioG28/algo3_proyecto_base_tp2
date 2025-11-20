package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;

import java.util.ArrayList;

public class MazoDeCartas {
    ArrayList<CartaDesarrollo> cartas = new ArrayList<>();


    public void agregarCarta(CartaDesarrollo carta) {
        if (carta == null) throw new IllegalArgumentException("Carta no puede ser null");
        cartas.add(carta);
    }

    public CartaDesarrollo agarrarCarta(int indice) {
        return cartas.get(indice);
    }
}
