package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Cartas.PuntoDeVictoria;

import java.util.ArrayList;
import java.util.List;

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

    public void actualizarEstadoDeCartas() {
        for (CartaDesarrollo carta : this.cartas) {
            carta.nuevoTurno();
        }
    }

    public CartaDesarrollo verCarta(int indiceCarta) {
        if (indiceCarta < 0 || indiceCarta >= cartas.size()) {
            throw new IllegalArgumentException("Indice de carta inválido: " + indiceCarta);
        }
        return cartas.get(indiceCarta);
    }

    public List<CartaDesarrollo> getCartas() {
        return this.cartas;
    }

    public void eliminarCarta(int indice) {
        if (indice < 0 || indice >= cartas.size()) {
            throw new IllegalArgumentException("No se puede eliminar la carta: índice " + indice + " fuera de rango.");
        }

        cartas.remove(indice);
    }
}
