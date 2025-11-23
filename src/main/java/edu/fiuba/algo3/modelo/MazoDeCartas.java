package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.PuntoDeVictoria;

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

    public <T extends CartaDesarrollo> int cantidadDeTipo(Class<T> tipo) {
        int cantidad = 0;

        for (CartaDesarrollo carta : cartas) {
            if (carta.getClass() == tipo) {
                cantidad++;
            }
        }

        return cantidad;
    }
}
