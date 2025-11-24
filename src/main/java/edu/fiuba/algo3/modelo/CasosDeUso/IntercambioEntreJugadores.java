package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class IntercambioEntreJugadores {
    public void intercambiar(Jugador jugador1, TipoDeRecurso recursoArecibir, int cantidadArecibir, Jugador jugador2, TipoDeRecurso recursoAentregar, int cantidadAentregar) throws RecursosIsuficientesException {
        jugador1.intercambiar(recursoArecibir, cantidadArecibir, jugador2, recursoAentregar, cantidadAentregar);
    }

}
