package edu.fiuba.algo3.UnitTest;
import edu.fiuba.algo3.modelo.CasoDeUsoArmarTablero;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import edu.fiuba.algo3.modelo.interfaces.ILado;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TableroFactoryTest {
    @Test
    public void test01generarLayoutCon19Hexagonos(){


        List<Axial> HEX_esperados = new LinkedList<>(Arrays.asList(
                new Axial(0, -2), new Axial(1, -2), new Axial(2, -2),
                new Axial(-1, -1), new Axial(0, -1), new Axial(1, -1), new Axial(2, -1),
                new Axial(-2, 0), new Axial(-1, 0), new Axial(0, 0), new Axial(1, 0), new Axial(2, 0),
                new Axial(-1, 1), new Axial(0, 1), new Axial(1, 1), new Axial(2, 1),
                new Axial(0, 2), new Axial(1, 2), new Axial(2, 2)
        ));

        List<Axial> HEX_obtenidos = TableroFactory.generarLayoutHexagonal();

        assertEquals(HEX_esperados.size(), HEX_obtenidos.size());

        assertEquals(new HashSet<>(HEX_esperados), new HashSet<>(HEX_obtenidos));

        assertEquals(HEX_esperados, HEX_obtenidos);

    }

    @Test
    public void test02conectarVerticesNoCreaNuevosVertices(){


        Map<Cubic, Vertice> vertices = new HashMap<>();
        Cubic[] coordenadasReales = {
                new Cubic(1, 0, -1),   // Vértice 0 - Este
                new Cubic(1, -1, 0),   // Vértice 1 - Sureste
                new Cubic(0, -1, 1),   // Vértice 2 - Suroeste
                new Cubic(-1, 0, 1),   // Vértice 3 - Oeste
                new Cubic(-1, 1, 0),   // Vértice 4 - Noroeste
                new Cubic(0, 1, -1)    // Vértice 5 - Noreste
        };

        for (Cubic coord : coordenadasReales) {
            vertices.put(coord, new Vertice());
        }

        int cantVerticesEsperada = vertices.size();

        List<Axial> posicionesHexagono = new LinkedList<>(Arrays.asList(
                new Axial(0,0)
        ));

        TableroFactory.conectarVerticesAdyacentesSimples(vertices, posicionesHexagono);

        assertEquals(cantVerticesEsperada, vertices.size());

    }

    @Test
    public void test03conectar6VerticesAdyacentesCorrectamenteEn1Hex(){
        Map<Cubic, Vertice> vertices = new HashMap<>();
        Cubic[] coordenadasReales = {
                new Cubic(1, 0, -1),   // Vértice 0 - Este
                new Cubic(1, -1, 0),   // Vértice 1 - Sureste
                new Cubic(0, -1, 1),   // Vértice 2 - Suroeste
                new Cubic(-1, 0, 1),   // Vértice 3 - Oeste
                new Cubic(-1, 1, 0),   // Vértice 4 - Noroeste
                new Cubic(0, 1, -1)    // Vértice 5 - Noreste
        };

        for (Cubic coord : coordenadasReales) {
            vertices.put(coord, new Vertice());
        }

        List<Axial> posicionesHexagono = Arrays.asList(new Axial(0, 0));

        TableroFactory.conectarVerticesAdyacentesSimples(vertices, posicionesHexagono);

        Vertice v0 = vertices.get(new Cubic(1, 0, -1));
        Vertice v1 = vertices.get(new Cubic(1, -1, 0));
        Vertice v5 = vertices.get(new Cubic(0, 1, -1));

        assertTrue(v0.esVerticeAdyacente(v1));
        assertTrue(v0.esVerticeAdyacente(v5));


    }



    @Test
    public void test04InicializarTableroConHexagonosCreados() {

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
        var tableroEsperado = TableroFactory.crear(hexagonos, fichasNumeradas);

        assertEquals(tableroEsperado, tablero);
    }
    @Test
    public void test05conectarLadosNoCreaNuevos(){


        Map<Cubic, Lado> lados = new HashMap<>();
        Cubic[] coordenadasReales = {
                new Cubic(1, 0, -1),   // Vértice 0 - Este
                new Cubic(1, -1, 0),   // Vértice 1 - Sureste
                new Cubic(0, -1, 1),   // Vértice 2 - Suroeste
                new Cubic(-1, 0, 1),   // Vértice 3 - Oeste
                new Cubic(-1, 1, 0),   // Vértice 4 - Noroeste
                new Cubic(0, 1, -1)    // Vértice 5 - Noreste
        };

        for (Cubic coord : coordenadasReales) {
            lados.put(coord, new Lado());
        }

        int cantLadosEsperada = lados.size();

        List<Axial> posicionesHexagono = new LinkedList<>(Arrays.asList(
                new Axial(0,0)
        ));

        TableroFactory.conectarLadosAdyacentesSimples(lados, posicionesHexagono);

        assertEquals(cantLadosEsperada, lados.size());

    }

    @Test
    public void test06conectar6LadosAdyacentesCorrectamenteEn1Hex(){
        Map<Cubic, Lado> lados = new HashMap<>();
        Cubic[] coordenadasReales = {
                new Cubic(1, 0, -1),   // Vértice 0 - Este
                new Cubic(1, -1, 0),   // Vértice 1 - Sureste
                new Cubic(0, -1, 1),   // Vértice 2 - Suroeste
                new Cubic(-1, 0, 1),   // Vértice 3 - Oeste
                new Cubic(-1, 1, 0),   // Vértice 4 - Noroeste
                new Cubic(0, 1, -1)    // Vértice 5 - Noreste
        };

        for (Cubic coord : coordenadasReales) {
            lados.put(coord, new Lado());
        }

        List<Axial> posicionesHexagono = Arrays.asList(new Axial(0, 0));

        TableroFactory.conectarLadosAdyacentesSimples(lados, posicionesHexagono);

        Lado l0 = lados.get(new Cubic(1, 0, -1));
        Lado l1 = lados.get(new Cubic(1, -1, 0));
        Lado l5 = lados.get(new Cubic(0, 1, -1));

        assertTrue(l0.esLadoAdyacente(l1));
        assertTrue(l0.esLadoAdyacente(l5));


    }



}
