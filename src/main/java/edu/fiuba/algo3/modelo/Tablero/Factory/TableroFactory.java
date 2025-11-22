package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.*;

public class TableroFactory {

// Offsets para calcular las posiciones de los vertices relativos a un hexagono
    private static final Cubic[] Vertice_OFFSETS = {
            new Cubic(1,-1,0),
            new Cubic(1,0,-1),
            new Cubic(0,1,-1),
            new Cubic(-1,1,0),
            new Cubic(-1,0,1),
            new Cubic(0,-1,1)
    };




    public static Tablero crear(List<Terreno> terrenos, List<Produccion> fichas) {

        if(terrenos.isEmpty() || fichas.isEmpty() || terrenos.size() < 19) {
            throw new IllegalArgumentException("La lista de terrenos o fichas no puede estar vacía y debe contener al menos 19 terrenos.");
        }

        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Cubic, Vertice> verticesUnicos = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        Map<Cubic, Lado> ladosUnicos = new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada = new HashMap<>();

        List<Axial> posicionesHexagonos = generarLayoutHexagonal();

        // 1. Asignar hexágonos fijos a los terrenos
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenos.get(id - 1);
            terrenoActual.asignarHexagono(new Hexagono());
            terrenoActual.setPosicion(posicionesHexagonos.get(id - 1));
            terrenosPorId.put(id, terrenoActual);
            terrenoActual.setId(id);
        }

        for (Terreno terreno : terrenosPorId.values()) {
            terreno.crearVertices(verticesUnicos, verticesPorCoordenada, Vertice_OFFSETS);
        }



        for (Terreno terreno : terrenosPorId.values()) {
            terreno.crearLados(ladosUnicos,ladosPorCoordenada, Vertice_OFFSETS);
        }

        conectarVerticesAdyacentesSimples(verticesUnicos, posicionesHexagonos);
        conectarLadosAdyacentesSimples(ladosUnicos, posicionesHexagonos);

        // 3. Asignar fichas de producción (igual que antes)
        Iterator<Produccion> it = fichas.iterator();
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenosPorId.get(id);
            if (!terrenoActual.esDesierto()) {
                terrenoActual.setProduccion(it.next());
            }
        }

        return new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);
    }



    public static List<Axial> generarLayoutHexagonal() {



        return Arrays.asList(
                new Axial(0, -2), new Axial(1, -2), new Axial(2, -2),
                new Axial(-1, -1), new Axial(0, -1), new Axial(1, -1), new Axial(2, -1),
                new Axial(-2, 0), new Axial(-1, 0), new Axial(0, 0), new Axial(1, 0), new Axial(2, 0),
                new Axial(-1, 1), new Axial(0, 1), new Axial(1, 1), new Axial(2, 1),
                new Axial(0, 2), new Axial(1, 2), new Axial(2, 2)
        );


    }

    public static void conectarVerticesAdyacentesSimples(Map<Cubic, Vertice> verticesUnicos, List<Axial> posicionesHexagonos) {
        for (Axial posHex : posicionesHexagonos) {
            Cubic centro = posHex.toCubic();


            Vertice[] verticesHex = new Vertice[6];
            for (int i = 0; i < 6; i++) {
                Cubic vCoord = centro.add(Vertice_OFFSETS[i]);
                verticesHex[i] = verticesUnicos.get(vCoord);
            }

            // Conectar cada vértice con sus dos vecinos en el hexágono
            for (int i = 0; i < 6; i++) {
                Vertice actual = verticesHex[i];
                if (actual != null) {
                    Vertice siguiente = verticesHex[(i + 1) % 6];
                    Vertice anterior = verticesHex[(i + 5) % 6];

                    if (siguiente != null) actual.agregarAdyacente(siguiente);
                    if (anterior != null) actual.agregarAdyacente(anterior);
                }
            }
        }
    }

    public static void conectarLadosAdyacentesSimples(Map<Cubic, Lado> ladosUnicos, List<Axial> posicionesHexagonos) {
        for (Axial posHex : posicionesHexagonos) {
            Cubic centro = posHex.toCubic();


            Lado[] ladosHex = new Lado[6];
            for (int i = 0; i < 6; i++) {
                Cubic ladoCoord = centro.add(Vertice_OFFSETS[i]);
                ladosHex[i] = ladosUnicos.get(ladoCoord);
            }


            for (int i = 0; i < 6; i++) {
                Lado actual = ladosHex[i];
                if (actual != null) {
                    Lado siguiente = ladosHex[(i + 1) % 6];
                    Lado anterior = ladosHex[(i + 5) % 6];

                    if (siguiente != null) actual.agregarAdyacente(siguiente);
                    if (anterior != null) actual.agregarAdyacente(anterior);
                }
            }
        }
    }


}
