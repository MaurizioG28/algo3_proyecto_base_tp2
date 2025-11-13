package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Mocks.FakeCelda;
import edu.fiuba.algo3.modelo.Mocks.FakeVertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.VerticeOcupado;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        ManagerTurno managerTurno = new ManagerTurno(jugadores,tablero,random);
        List<ICelda>  celdasAdyacentes = new ArrayList<>();
        FakeCelda fakeCeldaLana1 = new FakeCelda(Recurso.LANA);
        FakeCelda fakeCeldaLana2 = new FakeCelda(Recurso.LANA);
        FakeCelda fakeCeldaLadrillo1 = new FakeCelda(Recurso.LADRILLO);
        celdasAdyacentes.add(fakeCeldaLana1);
        celdasAdyacentes.add(fakeCeldaLana2);
        celdasAdyacentes.add(fakeCeldaLadrillo1);
        IVertice vertice =new FakeVertice(false,celdasAdyacentes);
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
        Random random = new Random();
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugador), tablero,random);

        IVertice vertice = new FakeVertice(false,
                List.of(
                        new FakeCelda(Recurso.GRANO),
                        new FakeCelda(Recurso.GRANO),
                        new FakeCelda(Recurso.GRANO)
                )
        );

        // Act
        managerTurno.construirPoblado(vertice);
        // Assert
        assertTrue(jugador.tiene(0,0,0,0,0)); // no recibió recursos    }
}
    @Test
    public void test03JugadorRecibeRecursoDeUnaSolaCelda() {
        // Arrange
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        Random random = new Random();
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugador), tablero,random);

        FakeCelda celda = new FakeCelda(Recurso.MADERA);
        IVertice vertice = new FakeVertice(false, List.of(celda));

        // Act
        managerTurno.construirPoblado(vertice);

        // Assert
        assertTrue(jugador.tiene(1,0,0,0,0));
    }
    @Test
    public void test04JugadorRecibeTresRecursosIguales() {
        Tablero tablero = new Tablero();
        Jugador jugador = new Jugador();
        Random random = new Random();
        ManagerTurno managerTurno = new ManagerTurno(List.of(jugador), tablero,random);

        IVertice vertice = new FakeVertice(false,
                List.of(
                        new FakeCelda(Recurso.GRANO),
                        new FakeCelda(Recurso.GRANO),
                        new FakeCelda(Recurso.GRANO)
                )
        );

        managerTurno.construirPoblado(vertice);

        assertTrue(jugador.tiene(0,0,0,3,0));
    }


}
