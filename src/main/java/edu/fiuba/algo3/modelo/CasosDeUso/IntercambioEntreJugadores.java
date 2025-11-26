package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.*;

import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Tablero;



import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;

public class IntercambioEntreJugadores {

    private final ManagerTurno manager;

    public IntercambioEntreJugadores() {
        List<Jugador> jugadores = List.of( new Jugador("Jugador1", new Color("Azul")),
         new Jugador("Jugador2", new Color("Rojo")));
        Catan catan =new Catan();
        Tablero tablero = catan.crearTablero();
        Random random = new Random();
        this.manager = new ManagerTurno(jugadores, tablero, random);
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
