package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class casoDeUsoRoboRecursos {
    private final TipoDeRecurso madera = new Madera(0);
    @Test
    public void Test04jugadorDebeRobarUnRecursoDeLaVictima() {
        Jugador ladron = new Jugador("nombre1",new Color("Azul"));
        Jugador victima = new Jugador("nombre2",new Color("Rojo"));

        victima.agregarRecurso(new Madera(1));

        ladron.robarRecurso(victima);

        assertEquals(1, ladron.CantidadRecurso(madera));
        assertEquals(0, victima.CantidadRecurso(madera));
    }

    @Test
    public void Test05noDebeRobarSiLaVictimaNoTieneRecursos() {
        Jugador ladron = new Jugador("nombre1",new Color("Azul"));
        Jugador victima = new Jugador("nombre2",new Color("Rojo"));

        ladron.robarRecurso(victima);

        assertEquals(0, ladron.totalRecursos());
        assertEquals(0, victima.totalRecursos());
    }
}
