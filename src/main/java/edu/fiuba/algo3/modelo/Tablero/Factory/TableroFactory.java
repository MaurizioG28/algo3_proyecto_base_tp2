package edu.fiuba.algo3.modelo.Tablero.Factory;

import edu.fiuba.algo3.modelo.Intercambios.Puerto;
import edu.fiuba.algo3.modelo.Intercambios.PuertoEspecifico;
import edu.fiuba.algo3.modelo.Intercambios.PuertoGenerico;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.*;

public class TableroFactory {

// Offsets para calcular las posiciones de los vertices relativos a un hexagono
    public static final Cubic[] Vertice_OFFSETS = {
            new Cubic(1,-1,0),
            new Cubic(1,0,-1),
            new Cubic(0,1,-1),
            new Cubic(-1,1,0),
            new Cubic(-1,0,1),
            new Cubic(0,-1,1)
    };
    public static final Axial[] NEIGHBOR_OFFSETS = {
            new Axial(1, -1),  // 0
            new Axial(1, 0),   // 1
            new Axial(0, 1),   // 2
            new Axial(-1, 1),  // 3
            new Axial(-1, 0),  // 4
            new Axial(0, -1)   // 5
    };
    private static final Coordenada[][] POSICIONES_PUERTOS = {
            { new Coordenada(1,0), new Coordenada(1,1) },
            { new Coordenada(2,3), new Coordenada(2,4) },
            { new Coordenada(4,3), new Coordenada(4,4) },
            { new Coordenada(7,1), new Coordenada(7,2) },
            { new Coordenada(8,0), new Coordenada(8,5) },
            { new Coordenada(11,3), new Coordenada(11,4) },
            { new Coordenada(14,1), new Coordenada(14,2) },
            { new Coordenada(16,5), new Coordenada(16,0) },
            { new Coordenada(19,2), new Coordenada(19,3) }
    };
    private static final Puerto[] PUERTOS = {
            new Puerto(new PuertoGenerico(3)), new Puerto(new PuertoGenerico(3)), new Puerto(new PuertoGenerico(3)), new Puerto(new PuertoGenerico(3)),
            new Puerto(new PuertoEspecifico(new Madera(0),2)), new Puerto(new PuertoEspecifico(new Lana(0),2)), new Puerto(new PuertoEspecifico(new Ladrillo(0),2)),
            new Puerto(new PuertoEspecifico(new Grano(0),2)), new Puerto(new PuertoEspecifico(new Mineral(0),2))
    };







    public static Tablero crear(List<Terreno> terrenos, List<Produccion> fichas) {

        return logicaCrear(terrenos, fichas,new Random());
    }

    public static Tablero crear(List<Terreno> terrenos, List<Produccion> fichas,Random random) {
        return logicaCrear(terrenos, fichas,random );
    }

    private static Tablero logicaCrear(List<Terreno> terrenos, List<Produccion> fichas,Random random) {
        if(terrenos.isEmpty() || fichas.isEmpty() || terrenos.size() < 19) {
            throw new IllegalArgumentException("La lista de terrenos o fichas no puede estar vacía y debe contener al menos 19 terrenos.");
        }

        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        //Map<Cubic, Vertice> verticesUnicos = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        //Map<Cubic, Lado> ladosUnicos = new HashMap<>();
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




        for (Integer terrenoId : terrenosPorId.keySet()) {

            for (int i = 0; i < 6; i++) {
                verticesPorCoordenada.put(new Coordenada(terrenoId, i), new Vertice());
            }

        }

        unificarVerticesCompartidos(terrenosPorId, verticesPorCoordenada);

        for (Terreno t : terrenosPorId.values()) {
            t.agregarVertices(verticesPorCoordenada);
        }

        for (Integer terrenoId : terrenosPorId.keySet()) {
            for (int i = 0; i < 6; i++) {
                ladosPorCoordenada.put(new Coordenada(terrenoId, i), new Lado());
            }
        }

        Map<String, Lado> ladosUnicos= unificarLados(ladosPorCoordenada, verticesPorCoordenada);




        conectarVerticesAdyacentes(terrenosPorId, verticesPorCoordenada);



        conectarPuntasYLados(ladosPorCoordenada, verticesPorCoordenada, ladosUnicos);

        for (Terreno terreno : terrenosPorId.values()) {
            terreno.agregarLados(ladosPorCoordenada);
        }


        Iterator<Produccion> it = fichas.iterator();
        for (int id = 1; id <= terrenos.size(); id++) {
            Terreno terrenoActual = terrenosPorId.get(id);
            if (!terrenoActual.esDesierto()) {
                terrenoActual.setProduccion(it.next());
            }
        }

        asignarPuertos(verticesPorCoordenada, random);

        return new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);
    }



    public static List<Axial> generarLayoutHexagonal() {



        return Arrays.asList(
                new Axial(0, -2), new Axial(1, -2), new Axial(2, -2),
                new Axial(-1, -1), new Axial(0, -1), new Axial(1, -1), new Axial(2, -1),
                new Axial(-2, 0), new Axial(-1, 0), new Axial(0, 0), new Axial(1, 0), new Axial(2, 0),
                new Axial(-2, 1), new Axial(-1, 1), new Axial(0, 1), new Axial(1, 1),
                new Axial(-2, 2), new Axial(-1, 2), new Axial(0, 2)
        );

//        List<Axial> list = new ArrayList<>();
//        int radius = 2;
//
//        for (int q = -radius; q <= radius; q++) {
//            int r1 = Math.max(-radius, -q - radius);
//            int r2 = Math.min(radius, -q + radius);
//            for (int r = r1; r <= r2; r++) {
//                list.add(new Axial(q, r));
//            }
//        }
//        return list;

    }







    private static String keyFor(Vertice v1, Vertice v2) {
        int id1 = System.identityHashCode(v1);
        int id2 = System.identityHashCode(v2);
        return (id1 < id2 ? id1 + "_" + id2 : id2 + "_" + id1);
    }

    public static Map<String, Lado> unificarLados(Map<Coordenada, Lado> ladosPorCoord,
                                                  Map<Coordenada, Vertice> verticesPorCoord) {

        Map<String, Lado> ladosUnicos = new HashMap<>();

        for (var entry : ladosPorCoord.entrySet()) {

            Coordenada coord = entry.getKey();
            int hexId = coord.numHex();
            int index = coord.indice();

            // Obtener puntas del lado actual (ya unificadas en etapa anterior)
            Vertice v1 = verticesPorCoord.get(new Coordenada(hexId, index));
            Vertice v2 = verticesPorCoord.get(new Coordenada(hexId, (index + 1) % 6));

            // Normalizar key para que (A,B) sea igual que (B,A)
            String key = keyFor(v1, v2);

            Lado ladoExistente = ladosUnicos.get(key);

            if (ladoExistente == null) {
                // Primer vez que aparece este lado → lo agregamos al mapa único
                ladosUnicos.put(key, entry.getValue());
            } else {
                // Ya existe → entonces reemplazamos la instancia duplicada
                ladosPorCoord.put(coord, ladoExistente);
            }
        }
        return ladosUnicos;
    }

    public static void conectarPuntasYLados(
            Map<Coordenada, Lado> ladosPorCoordenada,
            Map<Coordenada, Vertice> verticesPorCoordenada,
            Map<String, Lado> ladosUnicos) {

        // 1. Asignar puntas (v1, v2) a cada lado
        for (Map.Entry<Coordenada, Lado> entry : ladosPorCoordenada.entrySet()) {
            int hex = entry.getKey().numHex();
            int edge = entry.getKey().indice();

            Vertice v1 = verticesPorCoordenada.get(new Coordenada(hex, edge));
            Vertice v2 = verticesPorCoordenada.get(new Coordenada(hex, (edge + 1) % 6));

            Lado lado = entry.getValue();

            lado.agregarPunta(v1);
            lado.agregarPunta(v2);
        }

        // 2. Conectar lados adyacentes
        for (Lado lado : ladosUnicos.values()) {
            Vertice a = lado.getPunta(0);
            Vertice b = lado.getPunta(1);

            for (Lado otro : ladosUnicos.values()) {
                if (lado == otro) continue;

                // Si comparten al menos 1 punta → son adyacentes
                if (otro.tienePunta(a) || otro.tienePunta(b)) {
                    lado.agregarAdyacente(otro);
                }
            }
        }
    }

    public static void unificarVerticesCompartidos(
            Map<Integer, Terreno> terrenosPorId,
            Map<Coordenada, Vertice> verticesPorCoordenada
    ) {

        // Mapa para búsqueda rápida por posición axial → terreno
        Map<Axial, Terreno> terrenoPorPos = new HashMap<>();
        for (Terreno t : terrenosPorId.values()) {
            terrenoPorPos.put(t.getPosicion(), t);
        }

        // Recorrer cada hexágono
        for (Terreno t : terrenosPorId.values()) {
            Axial pos = t.getPosicion();
            int idA = t.getId();

            // Recorrer las 6 direcciones
            for (int dir = 0; dir < 6; dir++) {

                Axial posVecino = pos.add(NEIGHBOR_OFFSETS[dir]);
                Terreno vecino = terrenoPorPos.get(posVecino);

                if (vecino == null) continue; // no hay hexágono en esa dirección

                int idB = vecino.getId();

                // IMPORTANTE:
                // evitar procesar dos veces la misma relación
                if (idB < idA) continue;

                // Vértices del hexágono A
                Vertice A_v1 = verticesPorCoordenada.get(new Coordenada(idA, dir));
                Vertice A_v2 = verticesPorCoordenada.get(new Coordenada(idA, (dir + 1) % 6));

                // Lado opuesto en el hexágono vecino
                int dirB = (dir + 3) % 6;

                // Vértices del hexágono B, en orden más cercano a A
                Vertice B_v1 = verticesPorCoordenada.get(new Coordenada(idB, (dirB + 1) % 6));
                Vertice B_v2 = verticesPorCoordenada.get(new Coordenada(idB, dirB));

                // Unificar: REEMPLAZAMOS B_v1 → A_v1 y B_v2 → A_v2

                verticesPorCoordenada.put(new Coordenada(idB, (dirB + 1) % 6), A_v1);
                verticesPorCoordenada.put(new Coordenada(idB, dirB), A_v2);
            }
        }
    }
    public static void conectarVerticesAdyacentes(
            Map<Integer, Terreno> terrenosPorId,
            Map<Coordenada, Vertice> verticesPorCoordenada
    ) {
        for (Integer terrenoId : terrenosPorId.keySet()) {

            for (int i = 0; i < 6; i++) {
                Vertice a = verticesPorCoordenada.get(new Coordenada(terrenoId, i));
                Vertice b = verticesPorCoordenada.get(new Coordenada(terrenoId, (i + 1) % 6));

                if (a != null && b != null) {
                    a.agregarAdyacente(b);
                    b.agregarAdyacente(a);
                }
            }
        }
    }

    private static void asignarPuertos(Map<Coordenada, Vertice> verticesPorCoordenada,
                                      Random random) {

        // 1. Copiar lista de puertos y mezclar
        List<Puerto> lista = new ArrayList<>(Arrays.asList(PUERTOS));
        Collections.shuffle(lista, random);

        // 2. Copiar y mezclar posiciones fijas de pares de vértices
        List<Coordenada[]> posiciones = new ArrayList<>(Arrays.asList(POSICIONES_PUERTOS));
        Collections.shuffle(posiciones, random);

        // 3. Asignar cada puerto a su par de vértices
        for (int i = 0; i < lista.size(); i++) {
            Puerto p = lista.get(i);
            Coordenada[] par = posiciones.get(i);

            Vertice v1 = verticesPorCoordenada.get(par[0]);
            Vertice v2 = verticesPorCoordenada.get(par[1]);

            v1.asignarPuerto(p);
            v2.asignarPuerto(p);
        }
    }


}
