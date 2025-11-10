package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jugadorTest {

    //Verificar la producción correcta: 1 recurso por Poblado, 2 recursos por Ciudad,
    //adyacentes al número lanzado.


    //Verificar que si un jugador tiene más de 7 cartas, descarte correctamente la mitad,
    //redondeando hacia abajo, al lanzar un 7.
    @Test
    public void jugadorTieneMasDe7RecursosEnTotalYDescartaCorrectamenteLaMitadRedondeaHaciaAbajo(){
        int cantidadCartasEsperadas  =  4;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.Madera,8);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }
    @Test
    public void jugadorTieneMenosDe7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  =  5;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.Madera,5);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }

    @Test
    public void jugadorTiene7RecursosEnTotalYNoDescarta(){
        int cantidadCartasEsperadas  =  7;
        int cantidadRecursosJugador;
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(Recurso.Madera,7);
        jugador.descartarMitadDeRecursos();

        cantidadRecursosJugador = jugador.totalRecursos();

        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);

    }


}
