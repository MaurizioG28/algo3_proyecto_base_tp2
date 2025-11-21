package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class casoDeUsoDescartarRecursos {
    @Test
    public void Test01jugadorTieneMasDe7RecursosEnTotalYDescartaCorrectamenteLaMitadRedondeaHaciaAbajo(){
        int cantidadCartasEsperadas  = 4;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(8));  // antes: (Recurso.MADERA, 8)
        jugador.descartarMitadDeRecursos();

        int cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas, cantidadRecursosJugador);
    }

    @Test
    public void Test02jugadorTieneMenosDe7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  = 5;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(5));
        jugador.descartarMitadDeRecursos();

        int cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas, cantidadRecursosJugador);
    }

    @Test
    public void Test03jugadorTiene7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  = 7;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(7));
        jugador.descartarMitadDeRecursos();

        int cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas, cantidadRecursosJugador);
    }
}
