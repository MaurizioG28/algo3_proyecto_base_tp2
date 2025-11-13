package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jugadorTest {


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


}
