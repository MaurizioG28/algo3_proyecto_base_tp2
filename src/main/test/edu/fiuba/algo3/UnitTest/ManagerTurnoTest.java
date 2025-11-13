package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Mocks.FakeCelda;
import edu.fiuba.algo3.modelo.Mocks.FakeVertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTurnoTest {
    @Test
    public void test01ConstruirUnPobladoConVerticesAdyacentes2Ovejas1LadriloEnUnaCeldaValida() {
        // Arrange
        Tablero tablero = new Tablero();
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        ManagerTurno managerTurno = new ManagerTurno(jugadores,tablero);
        List<ICelda>  celdasAdyacentes = new ArrayList<>();
        FakeCelda fakeCeldaLana1 = new FakeCelda(Recurso.LANA);
        FakeCelda fakeCeldaLana2 = new FakeCelda(Recurso.LANA);
        FakeCelda fakeCeldaLadrillo1 = new FakeCelda(Recurso.LADRILLO);
        celdasAdyacentes.add(fakeCeldaLana1);
        celdasAdyacentes.add(fakeCeldaLana2);
        celdasAdyacentes.add(fakeCeldaLadrillo1);
        IVertice vertice =new FakeVertice(true,celdasAdyacentes);
        // Act
        managerTurno.construirPoblado(vertice);

        // Assert

        assertTrue(jugador1.tiene(0,1,2,0,0));

    }
    @Test
    public void test02NoDebeDarRecursosSiVerticeEsInvalido() {
        // Arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugador), tablero);


        IVertice vertice = new FakeVertice(false, List.of());

        // Act
        assertThrows(IllegalArgumentException.class, () -> managerTurno.construirPoblado(vertice));

        // Assert
        assertTrue(jugador.tiene(0,0,0,0,0)); // no recibió recursos
    }

}
