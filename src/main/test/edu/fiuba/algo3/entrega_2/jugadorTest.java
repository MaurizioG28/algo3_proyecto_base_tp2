package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jugadorTest {



    @Test
    public void Test04jugadorDebeRobarUnRecursoDeLaVictima() {
        Jugador ladron = new Jugador("nombre1",new Color("Azul"));
        Jugador victima = new Jugador("nombre2",new Color("Rojo"));


        victima.agregarRecurso(new Madera(1));


        ladron.robarRecurso(victima);

        assertEquals(1, ladron.cantidadRecurso(new Madera(0)));

        assertEquals(0, victima.cantidadRecurso(new Madera(0)));
    }

    @Test
    public void Test05noDebeRobarSiLaVictimaNoTieneRecursos() {
        Jugador ladron = new Jugador("nombre1",new Color("Azul"));
        Jugador victima = new Jugador("nombre2",new Color("Rojo"));


        ladron.robarRecurso(victima);


        assertEquals(0, ladron.totalRecursos());
        assertEquals(0, victima.totalRecursos());
    }

    @Test
    public void testJugadorTieneLosRecursosCorrectos() {
        Jugador jugador = new Jugador("Ana", new Color("Rojo"));

        jugador.agregarRecurso(new Madera(2));
        jugador.agregarRecurso(new Grano(1));

        assertEquals(2, jugador.cantidadRecurso(new Madera(0)));
        assertEquals(1, jugador.cantidadRecurso(new Grano(0)));
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo(0))); // No tiene ladrillo
    }


}
