package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;

public class CartaMonopolio extends CartaDesarrollo {
    private TipoDeRecurso recursoElegido;

    public CartaMonopolio( ) {
        super();
    }
    public void setRecursoElegido(TipoDeRecurso recursoElegido){
        this.recursoElegido = recursoElegido;
    }
    @Override
    public void ejecutarEfecto(Jugador jugadorActivo, Tablero tablero, List<Jugador> oponentes) {
        this.usar();

        if (this.recursoElegido == null) {
            throw new IllegalStateException("Debes elegir un recurso antes de jugar Monopolio");
        }

        for (Jugador victima : oponentes) {
            // Evitar robarse a sí mismo si la lista incluye al jugador activo
            if (!victima.equals(jugadorActivo)) {

                // La víctima calcula cuánto tiene, lo borra y nos devuelve el int.
                int cantidadRobada = victima.entregarTodo(this.recursoElegido);

                if (cantidadRobada > 0) {
                    jugadorActivo.recibirBotin(this.recursoElegido, cantidadRobada);
                }
            }
        }
    }


    public void ejecutarMonopolio(Jugador ladron, List<Jugador> todasLasVictimas) {
        if (this.recursoElegido == null) {
            throw new IllegalStateException("Debes elegir un recurso antes de jugar Monopolio");
        }

        for (Jugador victima : todasLasVictimas) {

            if (!victima.equals(ladron)) {
                int botin = victima.entregarTodo(this.recursoElegido);


                ladron.recibirBotin(this.recursoElegido, botin);
            }
        }
    }
}
