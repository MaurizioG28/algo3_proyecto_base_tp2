package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
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

}
