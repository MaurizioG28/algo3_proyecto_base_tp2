package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;

public class IntercambioEntreJugadores {
    public void intercambiar(Jugador jugador1, Recurso recursoArecibir, int cantidadArecibir, Jugador jugador2, Recurso recursoAentregar, int cantidadAentregar) throws RecursosIsuficientesException {
        jugador1.intercambiar(recursoArecibir, cantidadArecibir, jugador2, recursoAentregar, cantidadAentregar);
    }

}
