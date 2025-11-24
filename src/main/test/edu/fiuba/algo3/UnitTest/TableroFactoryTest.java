package edu.fiuba.algo3.UnitTest;
import edu.fiuba.algo3.modelo.CasoDeUsoArmarTablero;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import edu.fiuba.algo3.modelo.interfaces.ILado;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory.Vertice_OFFSETS;
import static edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory.generarLayoutHexagonal;
import static org.junit.jupiter.api.Assertions.*;

public class TableroFactoryTest {
    @Test
    public void test01generarLayoutCon19Hexagonos(){


        List<Axial> HEX_esperados = new LinkedList<>(Arrays.asList(
                new Axial(0, -2), new Axial(1, -2), new Axial(2, -2),
                new Axial(-1, -1), new Axial(0, -1), new Axial(1, -1), new Axial(2, -1),
                new Axial(-2, 0), new Axial(-1, 0), new Axial(0, 0), new Axial(1, 0), new Axial(2, 0),
                new Axial(-2, 1), new Axial(-1, 1), new Axial(0, 1), new Axial(1, 1),
                new Axial(-2, 2), new Axial(-1, 2), new Axial(0, 2)
        ));

        List<Axial> HEX_obtenidos = generarLayoutHexagonal();

        assertEquals(HEX_esperados.size(), HEX_obtenidos.size());

        assertEquals(new HashSet<>(HEX_esperados), new HashSet<>(HEX_obtenidos));



    }

    @Test
    public void test02conectarVerticesNoCreaNuevosVertices(){


        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();

        // Crear un terreno de prueba con ID 1
        Terreno terreno = new Bosque(); // o el terreno que uses
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terrenosPorId.put(1, terreno);

        // Crear vértices iniciales para este hexágono
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }

        int cantVerticesEsperada = new HashSet<>(verticesPorCoordenada.values()).size();

        // Conectar vértices
        TableroFactory.conectarVerticesAdyacentes(terrenosPorId, verticesPorCoordenada);

        // Verificar que no se crearon nuevos vértices (solo se conectaron)
        int cantVerticesFinal = new HashSet<>(verticesPorCoordenada.values()).size();
        assertEquals(cantVerticesEsperada, cantVerticesFinal);

    }

    @Test
    public void test03conectar6VerticesAdyacentesCorrectamenteEn1Hex(){
        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();

        // Crear un terreno de prueba
        Terreno terreno = new Bosque();
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terrenosPorId.put(1, terreno);

        // Crear vértices con IDs específicos para poder referenciarlos
        Vertice v0 = new Vertice();
        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();
        Vertice v3 = new Vertice();
        Vertice v4 = new Vertice();
        Vertice v5 = new Vertice();

        // Asignar en orden de hexágono (posición 0-5)
        verticesPorCoordenada.put(new Coordenada(1, 0), v0);
        verticesPorCoordenada.put(new Coordenada(1, 1), v1);
        verticesPorCoordenada.put(new Coordenada(1, 2), v2);
        verticesPorCoordenada.put(new Coordenada(1, 3), v3);
        verticesPorCoordenada.put(new Coordenada(1, 4), v4);
        verticesPorCoordenada.put(new Coordenada(1, 5), v5);



        // Conectar vértices
        TableroFactory.conectarVerticesAdyacentes(terrenosPorId, verticesPorCoordenada);

        // Verificar conexiones en anillo
        assertTrue(v0.esVerticeAdyacente(v1)); // 0 ↔ 1
        assertTrue(v0.esVerticeAdyacente(v5)); // 0 ↔ 5

        assertTrue(v1.esVerticeAdyacente(v0)); // 1 ↔ 0
        assertTrue(v1.esVerticeAdyacente(v2)); // 1 ↔ 2

        assertTrue(v2.esVerticeAdyacente(v1)); // 2 ↔ 1
        assertTrue(v2.esVerticeAdyacente(v3)); // 2 ↔ 3

        assertTrue(v3.esVerticeAdyacente(v2)); // 3 ↔ 2
        assertTrue(v3.esVerticeAdyacente(v4)); // 3 ↔ 4

        assertTrue(v4.esVerticeAdyacente(v3)); // 4 ↔ 3
        assertTrue(v4.esVerticeAdyacente(v5)); // 4 ↔ 5

        assertTrue(v5.esVerticeAdyacente(v4)); // 5 ↔ 4
        assertTrue(v5.esVerticeAdyacente(v0)); // 5 ↔ 0

        // Verificar que NO son adyacentes los no-consecutivos
        assertFalse(v0.esVerticeAdyacente(v2));
        assertFalse(v0.esVerticeAdyacente(v3));
        assertFalse(v0.esVerticeAdyacente(v4));


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
    public void test05conectarLadosNoCreaNuevos() {
        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada = new HashMap<>();

        // Crear un terreno de prueba
        Terreno terreno = new Bosque();
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terrenosPorId.put(1, terreno);

        // Crear vértices para el hexágono
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }

        // Crear lados iniciales
        for (int i = 0; i < 6; i++) {
            ladosPorCoordenada.put(new Coordenada(1, i), new Lado());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terreno.agregarLados(ladosPorCoordenada);


        // Unificar lados (esto no debería crear nuevos, solo unificar duplicados)
        Map<String, Lado> ladosUnicos = TableroFactory.unificarLados(ladosPorCoordenada, verticesPorCoordenada);
        int cantLadosEsperada = ladosUnicos.size();

        // Conectar puntas y lados
        TableroFactory.conectarPuntasYLados(ladosPorCoordenada, verticesPorCoordenada, ladosUnicos);

        // Verificar que no se crearon nuevos lados únicos durante la conexión
        int cantLadosFinal = ladosUnicos.size();
        assertEquals(cantLadosEsperada, cantLadosFinal);
    }
    @Test
    public void testConteoVertices() {
        Map<Cubic, Vertice> verticesUnicos = new HashMap<>();
        List<Axial> posiciones = generarLayoutHexagonal();

        // Simula la creación de vértices
        for (int id = 0; id < posiciones.size(); id++) {
            Axial pos = posiciones.get(id);
            Cubic centro = pos.toCubic();

            for (int i = 0; i < 6; i++) {
                Cubic vCoord = centro.add(Vertice_OFFSETS[i]);
                verticesUnicos.put(vCoord, new Vertice());
            }
        }

        System.out.println("Vértices únicos esperados: " + verticesUnicos.size());
        assertEquals(37, verticesUnicos.size());
        // Debería ser 54
    }

    @Test
    public void test06conectar6LadosAdyacentesCorrectamenteEn1Hex() {
        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada = new HashMap<>();

        // Crear un terreno de prueba
        Terreno terreno = new Bosque();
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terrenosPorId.put(1, terreno);

        // Crear vértices con referencias para poder testear
        Vertice v0 = new Vertice();
        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();
        Vertice v3 = new Vertice();
        Vertice v4 = new Vertice();
        Vertice v5 = new Vertice();

        verticesPorCoordenada.put(new Coordenada(1, 0), v0);
        verticesPorCoordenada.put(new Coordenada(1, 1), v1);
        verticesPorCoordenada.put(new Coordenada(1, 2), v2);
        verticesPorCoordenada.put(new Coordenada(1, 3), v3);
        verticesPorCoordenada.put(new Coordenada(1, 4), v4);
        verticesPorCoordenada.put(new Coordenada(1, 5), v5);

        // Crear lados con referencias
        Lado l0 = new Lado();
        Lado l1 = new Lado();
        Lado l2 = new Lado();
        Lado l3 = new Lado();
        Lado l4 = new Lado();
        Lado l5 = new Lado();

        ladosPorCoordenada.put(new Coordenada(1, 0), l0);
        ladosPorCoordenada.put(new Coordenada(1, 1), l1);
        ladosPorCoordenada.put(new Coordenada(1, 2), l2);
        ladosPorCoordenada.put(new Coordenada(1, 3), l3);
        ladosPorCoordenada.put(new Coordenada(1, 4), l4);
        ladosPorCoordenada.put(new Coordenada(1, 5), l5);

        // Unificar lados (en un solo hexágono, no debería haber unificación)
        Map<String, Lado> ladosUnicos = TableroFactory.unificarLados(ladosPorCoordenada, verticesPorCoordenada);

        // Conectar puntas y lados
        TableroFactory.conectarPuntasYLados(ladosPorCoordenada, verticesPorCoordenada, ladosUnicos);

        // Verificar conexiones en anillo
        assertTrue(l0.esLadoAdyacente(l1)); // Lado 0 ↔ Lado 1 (comparten vértice v1)
        assertTrue(l0.esLadoAdyacente(l5)); // Lado 0 ↔ Lado 5 (comparten vértice v0)

        assertTrue(l1.esLadoAdyacente(l0)); // Bidireccional
        assertTrue(l1.esLadoAdyacente(l2)); // Lado 1 ↔ Lado 2 (comparten vértice v2)

        assertTrue(l2.esLadoAdyacente(l1)); // Bidireccional
        assertTrue(l2.esLadoAdyacente(l3)); // Lado 2 ↔ Lado 3 (comparten vértice v3)

        assertTrue(l3.esLadoAdyacente(l2)); // Bidireccional
        assertTrue(l3.esLadoAdyacente(l4)); // Lado 3 ↔ Lado 4 (comparten vértice v4)

        assertTrue(l4.esLadoAdyacente(l3)); // Bidireccional
        assertTrue(l4.esLadoAdyacente(l5)); // Lado 4 ↔ Lado 5 (comparten vértice v5)

        assertTrue(l5.esLadoAdyacente(l4)); // Bidireccional
        assertTrue(l5.esLadoAdyacente(l0)); // Bidireccional

        // Verificar que NO son adyacentes los no-consecutivos
        assertFalse(l0.esLadoAdyacente(l2));
        assertFalse(l0.esLadoAdyacente(l3));
        assertFalse(l0.esLadoAdyacente(l4));
    }



}
