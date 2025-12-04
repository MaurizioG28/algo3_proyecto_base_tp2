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

        if (this.idDestino == null) {
            throw new IllegalStateException("Faltan opciones para jugar el Caballero.");
        }

        List<Color> coloresVictimas = tablero.moverLadron(jugadorActivo, this.idDestino);

        Jugador victimaARobar = this.victima;

        // Si no se especificó víctima (viene null de la vista), elegir una al azar de los colores afectados
        if (victimaARobar == null && !coloresVictimas.isEmpty()) {
            Color colorVictima = coloresVictimas.get(0); // Elegimos el primer color afectado

            // Buscamos qué jugador tiene ese color
            for (Jugador op : oponentes) {
                if (op.getColor().equals(colorVictima)) {
                    victimaARobar = op;
                    break;
                }

            }
        }
        if (victimaARobar != null) {
            if (!victimaARobar.equals(jugadorActivo)) {
                jugadorActivo.robarRecurso(victimaARobar);
            }
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

}
