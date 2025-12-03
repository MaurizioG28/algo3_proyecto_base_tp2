package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;

public class PuntoDeVictoria extends CartaDesarrollo implements CartaProductora{

    public PuntoDeVictoria() {
    }

    @Override
    public void ejecutarEfecto(Jugador jugador, Tablero tablero, List<Jugador> oponentes) {
    }

    public int obtenerCantidadPV() {
        return 1;
    }
    @Override
    public boolean esConsumible() {
        return false; // Esta se queda en la mano para siempre
    }
}
