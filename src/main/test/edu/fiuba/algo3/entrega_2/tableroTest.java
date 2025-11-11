package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Vertice;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class tableroTest {

    @Test
    public void test01InicializarTableroConHexagonosCreados() {
        Tablero tablero = new Tablero();
        tablero.setUp();
        assertTrue(tablero.tableroCorrectamenteInicializado());
    }

    @Test
    public void noDebePermitirConstruirPobladoEnVerticeAdyacente() {
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();

        v1.agregarAdyacente(v2);
        v2.agregarAdyacente(v1);

        try {
            tablero.construirPoblado(jugador1, v1);
        } catch (ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ReglaDistanciaException.class,
                () -> tablero.construirPoblado(jugador1, v2));
    }

}
