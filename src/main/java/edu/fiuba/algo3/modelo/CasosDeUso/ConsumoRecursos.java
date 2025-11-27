package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;

//import static org.mockito.Mockito.mock;

public class ConsumoRecursos {
    public void construirPoblado(Jugador jugador, Tablero tablero, Coordenada coord) throws RecursosIsuficientesException {
        Random random = new Random();
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugador), tablero, random);
        managerTurno.construirPoblado(coord);
    }
}
