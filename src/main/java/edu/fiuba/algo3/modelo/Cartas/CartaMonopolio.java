package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.List;

public class CartaMonopolio extends CartaDesarrollo {
    private TipoDeRecurso recursoElegido;

    public CartaMonopolio(int turno) {
        super(turno);
    }

    public void setRecursoElegido(TipoDeRecurso r) {
        this.recursoElegido = r;
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
