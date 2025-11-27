package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;

import java.util.List;
import java.util.Random;

public class CartaCaballero extends CartaDesarrollo {

    public CartaCaballero( ) {
    }

    public void usarCarta(Jugador jugador, List<Jugador> victimas) {
        if(!victimas.isEmpty()){
            Random azar = new Random();
            Jugador victima = victimas.get(azar.nextInt(victimas.size()));
            //Selecciona una victima al azar por ahora, depues vemos como hacer para que el jugador elija desde la interfaz
            jugador.robarRecurso(victima);
        }
    }

}
