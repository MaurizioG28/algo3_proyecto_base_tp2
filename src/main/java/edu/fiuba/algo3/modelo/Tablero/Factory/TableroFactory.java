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

        // Generar posiciones hexagonales dinámicamente
        List<Axial> posicionesHexagonos = generarLayoutHexagonal();

        // 1. Asignar hexágonos fijos a los terrenos
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenos.get(id - 1);
            terrenoActual.asignarHexagono(new Hexagono());
            terrenosPorId.put(id, terrenoActual);
        }

        // 2. Generar vertices compartidos geométricamente
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenosPorId.get(id);
            Hexagono hex = terrenoActual.getHexagono();

            Axial axial = posicionesHexagonos.get(id - 1);
            Cubic centro = axial.toCubic();

            for (int i = 0; i < 6; i++) {
                Cubic vCoord = centro.add(Vertice_OFFSETS[i]);
                Vertice v = verticesUnicos.computeIfAbsent(vCoord, k -> new Vertice());
                hex.agregarVertice(v);

                Coordenada coord = new Coordenada(id, i);
                verticesPorCoordenada.put(coord, v);
            }
        }

        conectarVerticesAdyacentesSimples(verticesUnicos, posicionesHexagonos);

        // 3. Asignar fichas de producción (igual que antes)
        Iterator<Produccion> it = fichas.iterator();
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenosPorId.get(id);
            if (!terrenoActual.esDesierto()) {
                terrenoActual.setProduccion(it.next());
            }
        }

        return new Tablero(terrenosPorId, verticesPorCoordenada);
    }



    public static List<Axial> generarLayoutHexagonal() {
        //List<Axial> posiciones = new ArrayList<>();


        return Arrays.asList(
                new Axial(0, -2), new Axial(1, -2), new Axial(2, -2),
                new Axial(-1, -1), new Axial(0, -1), new Axial(1, -1), new Axial(2, -1),
                new Axial(-2, 0), new Axial(-1, 0), new Axial(0, 0), new Axial(1, 0), new Axial(2, 0),
                new Axial(-1, 1), new Axial(0, 1), new Axial(1, 1), new Axial(2, 1),
                new Axial(0, 2), new Axial(1, 2), new Axial(2, 2)
        );


//        for (int q = 0; q < radio; q++) {
//            for (int r = 0; r < radio; r++) {
//                posiciones.add(new Axial(q, r));
//                if (posiciones.size() == cantidadHexagonos) {
//                    return posiciones;
//                }
//            }
//        }
//
//        return posiciones;
    }

    public static void conectarVerticesAdyacentesSimples(Map<Cubic, Vertice> verticesUnicos, List<Axial> posicionesHexagonos) {
        for (Axial posHex : posicionesHexagonos) {
            Cubic centro = posHex.toCubic();

            // Obtener los 6 vértices de este hexágono
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


}
