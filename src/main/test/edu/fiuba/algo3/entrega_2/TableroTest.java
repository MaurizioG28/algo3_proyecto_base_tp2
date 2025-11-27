package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.CasoDeUsoArmarTablero;

import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableroTest {




    @Test
    public void test01InicializarTableroConHexagonosCreados() {

        List<Terreno> hexagonos = Arrays.asList(
                new Bosque(),
                new Campo(),
                new Bosque(),
                new Pastizal(),
                new Bosque(),
                new Campo(),
                new Montania(),
                new Campo(),
                new Montania(),
                new Campo(),
                new Colina(),
                new Colina(),
                new Desierto(),
                new Colina(),
                new Pastizal(),
                new Montania(),
                new Pastizal(),
                new Bosque(),
                new Pastizal()
        );

        List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
                new Produccion(2),
                new Produccion(3),
                new Produccion(3),
                new Produccion(4),
                new Produccion(4),
                new Produccion(5),
                new Produccion(5),
                new Produccion(6),
                new Produccion(6),
                new Produccion(8),
                new Produccion(8),
                new Produccion(9),
                new Produccion(9),
                new Produccion(10),
                new Produccion(10),
                new Produccion(11),
                new Produccion(11),
                new Produccion(12)

        ));

        CasoDeUsoArmarTablero caso = new CasoDeUsoArmarTablero(hexagonos, fichasNumeradas);

        var tablero = caso.crearTablero();
        var tableroEsperado =  TableroFactory.crear(hexagonos, fichasNumeradas);

        assertEquals(tableroEsperado, tablero);
    }

}
