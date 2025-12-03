package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;
import java.util.Random;

public class CartaCaballero extends CartaDesarrollo {
    private Integer idDestino;
    private Jugador victima;

    public CartaCaballero( ) {
        super();
    }

    @Override
    public void ejecutarEfecto(Jugador jugadorActivo, Tablero tablero, List<Jugador> oponentes) {
        this.usar();
        if (this.idDestino == null) {
            throw new IllegalStateException("Faltan opciones para jugar el Caballero.");
        }
        List<Color> coloresVictimas = tablero.moverLadron(jugadorActivo, this.idDestino);
        if (this.victima != null) {
            if (this.victima.equals(jugadorActivo)) {
                throw new RuntimeException("No puedes robarte a ti mismo.");
            }

            if (!coloresVictimas.contains(this.victima.getColor())) {
                throw new RuntimeException("La víctima seleccionada no tiene construcciones en el destino.");
            }

            jugadorActivo.robarRecurso(this.victima);
        }

        jugadorActivo.sumarCaballero();
    }

    public void usarCarta(Jugador jugador, List<Jugador> victimas) {
        if(!victimas.isEmpty()){
            Random azar = new Random();
            Jugador victima = victimas.get(azar.nextInt(victimas.size()));
            jugador.robarRecurso(victima);
        }
    }

    public void setOpciones(int idDestino, Jugador jugadorVictima) {
        this.idDestino = idDestino;
        this.victima = jugadorVictima;
    }
    public boolean esConsumible() {
        return true;
    }
}
