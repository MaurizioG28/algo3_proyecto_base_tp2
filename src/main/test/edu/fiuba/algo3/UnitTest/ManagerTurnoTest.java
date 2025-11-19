package edu.fiuba.algo3.UnitTest;
import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ManagerTurnoTest {
    private ManagerTurno managerTurno;
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Random random;
    private List<Jugador> jugadores;
    @BeforeEach
    void setUp() {
        tablero = new Tablero();
        jugadores = new ArrayList<>();
        jugador1 = new Jugador();
        jugador2 = new Jugador();
        random = new Random();

    }
    @Test
    public void test01ConstruirUnPobladoConVerticesAdyacentes2Ovejas1LadriloEnUnaCeldaValida() {
        // Arrange
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        managerTurno = new ManagerTurno(jugadores, tablero, random);
        IVertice vertice = mock(IVertice.class);
        Mockito.when(vertice.tieneConstruccion()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.tieneConstruccionAdyacente()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.darRecursos()).thenReturn(new ArrayList<Recurso>(List.of(Recurso.LANA, Recurso.LANA,Recurso.LADRILLO)));
        // Act
        managerTurno.construirPoblado(vertice);
        // Assert
        assertTrue(jugador1.tiene(0,1,2,0,0));

    }
    @Test
    public void test02NoDebeDarRecursosSiVerticeEsInvalido() {
        // Arrange
        managerTurno = new ManagerTurno(jugadores, tablero, random);
        jugadores.add(jugador1);
        IVertice vertice = mock(IVertice.class);
        Mockito.when(vertice.tieneConstruccion()).thenReturn(Boolean.TRUE);
        Mockito.when(vertice.tieneConstruccionAdyacente()).thenReturn(Boolean.TRUE);
        Mockito.when(vertice.darRecursos()).thenReturn(new ArrayList<Recurso>(List.of(Recurso.GRANO, Recurso.GRANO,Recurso.GRANO)));

        // Act
        managerTurno.construirPoblado(vertice);
        // Assert
        assertTrue(jugador1.tiene(0,0,0,0,0)); // no recibió recursos    }
    }
    @Test
    public void test03JugadorRecibeRecursoDeUnaSolaCelda() {
        // Arrange
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        managerTurno = new ManagerTurno(jugadores, tablero, random);
        IVertice vertice = Mockito.mock(IVertice.class);
        Mockito.when(vertice.tieneConstruccion()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.tieneConstruccionAdyacente()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.darRecursos()).thenReturn(List.of(Recurso.MADERA));
        // Act
        managerTurno.construirPoblado(vertice);

        // Assert
        assertTrue(jugador1.tiene(1,0,0,0,0));
    }
    @Test
    public void test04JugadorRecibeTresRecursosIguales() {
        //Arrange
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        managerTurno = new ManagerTurno(jugadores, tablero, random);
        managerTurno.siguienteTurno();
        IVertice vertice = mock(IVertice.class);
        Mockito.when(vertice.tieneConstruccion()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.tieneConstruccionAdyacente()).thenReturn(Boolean.FALSE);
        Mockito.when(vertice.darRecursos()).thenReturn(new ArrayList<Recurso>(List.of(Recurso.GRANO, Recurso.GRANO,Recurso.GRANO)));
        //Act
        managerTurno.construirPoblado(vertice);
        //Assert
        assertTrue(jugador2.tiene(0,0,0,3,0));
    }



}
