package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Intercambios.Puerto;
import edu.fiuba.algo3.modelo.Intercambios.PuertoEspecifico;
import edu.fiuba.algo3.modelo.Intercambios.PuertoGenerico;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PuertoTest {
    @Test
    public void testPuertoConPoliticaEspecifica() {
        PoliticaDeIntercambio politica = new PuertoEspecifico(new Madera(0), 2);
        Puerto puerto = new Puerto(politica);

        assertEquals(2, puerto.getTasa());
        assertTrue(puerto.puedeIntercambiar(new Jugador("Test",new Color("azul")), new Madera(2)));
        assertFalse(puerto.puedeIntercambiar(new Jugador("Test",new Color("azul")), new Grano(2)));
    }

    @Test
    public void testPuertoConPoliticaGenerica() {
        PoliticaDeIntercambio politica = new PuertoGenerico(3);
        Puerto puerto = new Puerto(politica);

        assertEquals(3, puerto.getTasa());
        assertTrue(puerto.puedeIntercambiar(new Jugador("Test",new Color("azul")), new Madera(3)));
        assertTrue(puerto.puedeIntercambiar(new Jugador("Test",new Color("azul")), new Grano(3)));
    }

    @Test
    public void testGetPolitica() {
        PoliticaDeIntercambio politica = new PuertoEspecifico(new Grano(0), 2);
        Puerto puerto = new Puerto(politica);

        assertSame(politica, puerto.getPolitica());
    }
    }
