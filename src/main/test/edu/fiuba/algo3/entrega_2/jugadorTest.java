package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jugadorTest {



    @Test
    public void Test04jugadorDebeRobarUnRecursoDeLaVictima() {
        Jugador ladron = new Jugador();
        Jugador victima = new Jugador();


        victima.agregarRecurso(new Madera(1));


        ladron.robarRecurso(victima);

        assertEquals(1, ladron.CantidadRecurso(new Madera(0)));

        assertEquals(0, victima.CantidadRecurso(new Madera(0)));
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
