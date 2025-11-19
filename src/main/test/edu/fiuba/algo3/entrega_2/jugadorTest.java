package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class jugadorTest {
//
//
//    @Test
//    public void Test01jugadorTieneMasDe7RecursosEnTotalYDescartaCorrectamenteLaMitadRedondeaHaciaAbajo(){
//        int cantidadCartasEsperadas  =  4;
//        int cantidadRecursosJugador;
//        Jugador jugador = new Jugador();
//        TipoDeRecurso Madera = new Madera();
//        jugador.agregarRecurso(Madera,8);
//        jugador.descartarMitadDeRecursos();
//
//        cantidadRecursosJugador = jugador.totalRecursos();
//
//        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);
//
//    }
//    @Test
//    public void Test02jugadorTieneMenosDe7RecursosEnTotalYNoDescarta(){
//        int cantidadCartasEsperadas  =  5;
//        int cantidadRecursosJugador;
//        Jugador jugador = new Jugador();
//        TipoDeRecurso Madera = new Madera();
//        jugador.agregarRecurso(Madera,5);
//        jugador.descartarMitadDeRecursos();
//
//        cantidadRecursosJugador = jugador.totalRecursos();
//
//        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);
//
//    }
//
//    @Test
//    public void Test03jugadorTiene7RecursosEnTotalYNoDescarta(){
//        int cantidadCartasEsperadas  =  7;
//        int cantidadRecursosJugador;
//        Jugador jugador = new Jugador();
//        TipoDeRecurso Madera = new Madera();
//        jugador.agregarRecurso(Madera,7);
//        jugador.descartarMitadDeRecursos();
//
//        cantidadRecursosJugador = jugador.totalRecursos();
//
//        assertEquals(cantidadCartasEsperadas,cantidadRecursosJugador);
//
//    }
//
//    @Test
//    public void Test04jugadorDebeRobarUnRecursoDeLaVictima() {
//        Jugador ladron = new Jugador();
//        Jugador victima = new Jugador();
//        TipoDeRecurso Madera = new Madera();
//
//        victima.agregarRecurso(Madera, 1);
//
//
//        ladron.robarRecurso(victima);
//
//        assertEquals(1, ladron.CantidadRecurso(Madera));
//
//        assertEquals(0, victima.CantidadRecurso(Madera));
//    }
//
//    @Test
//    public void Test05noDebeRobarSiLaVictimaNoTieneRecursos() {
//        Jugador ladron = new Jugador();
//        Jugador victima = new Jugador();
//
//
//        ladron.robarRecurso(victima);
//
//
//        assertEquals(0, ladron.totalRecursos());
//        assertEquals(0, victima.totalRecursos());
//    }


//}
