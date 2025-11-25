package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.MazoOculto;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Random;

public class IntercambioEntreJugadores {

    private final ManagerTurno manager;

    public IntercambioEntreJugadores() {
        List<Jugador> jugadores = mock(List.class);
        Tablero tablero = mock(Tablero.class);
        Random random = mock(Random.class);
        MazoOculto mazoOculto = mock(MazoOculto.class);
        this.manager = new ManagerTurno(jugadores, tablero, random, mazoOculto);
    }

    public void intercambiar(Jugador jugador1, TipoDeRecurso recursoAentregar, int cantidadAentregar, Jugador jugador2, TipoDeRecurso recursoArecibir, int cantidadArecibir) throws RecursosIsuficientesException {
        manager.intercambiarConJugadores(jugador1,
                recursoAentregar,
                cantidadAentregar,
                recursoArecibir,
                cantidadArecibir,
                List.of(jugador2));

    }
}
