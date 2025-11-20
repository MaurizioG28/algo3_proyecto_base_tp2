package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero.Costo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class jugadorTest {


    private static final Logger log = LoggerFactory.getLogger(jugadorTest.class);

    @Test
    public void Test01jugadorTieneMasDe7RecursosEnTotalYDescartaCorrectamenteLaMitadRedondeaHaciaAbajo(){
        int cantidadCartasEsperadas  =  4;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.MADERA,8);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }
    @Test
    public void Test02jugadorTieneMenosDe7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  =  5;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.MADERA,5);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }

    @Test
    public void Test03jugadorTiene7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  =  7;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.MADERA,7);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }

    @Test
    public void Test04jugadorDebeRobarUnRecursoDeLaVictima() {
        Jugador ladron = new Jugador();
        Jugador victima = new Jugador();


        victima.agregarRecurso(Recurso.MADERA, 1);


        ladron.robarRecurso(victima);

        assertEquals(1, ladron.CantidadRecurso(Recurso.MADERA));

        assertEquals(0, victima.CantidadRecurso(Recurso.MADERA));
    }

    @Test
    public void Test05noDebeRobarSiLaVictimaNoTieneRecursos() {
        Jugador ladron = new Jugador();
        Jugador victima = new Jugador();


        ladron.robarRecurso(victima);


        assertEquals(0, ladron.totalRecursos());
        assertEquals(0, victima.totalRecursos());
    }

    @Test
    public void test06SeDescuentanRecursosAlJugador() {
        // Arrange
        Jugador jugador = new Jugador();
        jugador.agregarRecurso(Recurso.MADERA, 2);
        jugador.agregarRecurso(Recurso.LADRILLO, 1);

        Costo costo = new Costo(Map.of(
                Recurso.MADERA, 1,
                Recurso.LADRILLO, 1
        ));

        // Act
        jugador.pagar(costo);

        assertTrue(jugador.tiene(1, 0, 0, 0, 0));
    }

}
