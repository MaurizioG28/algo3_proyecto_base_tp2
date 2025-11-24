package edu.fiuba.algo3.modelo.Tablero.Factory;


import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;

import java.util.*;


public class Hexagono {
    //private final Terreno tipo;


    private boolean bloqueadoPorLadron = false;
    private final List<Vertice> vertices = new ArrayList<>(6);
    //private final Vertice[] vertices = new Vertice[6];
    private final List<Lado> lados = new ArrayList<>(6);

    public Hexagono() {
        //this.tipo = tipo;


    }

    public void moverLadron() {
        bloqueadoPorLadron = !bloqueadoPorLadron;
    }



    public boolean sePuedeProducir() {
        return (!bloqueadoPorLadron);
    }

    //public Terreno  getTipo() { return tipo; }


    public List<Vertice> getVertices() { return (vertices); }

    public void agregarVertice( Vertice v) { vertices.add(v) ; }
    public void agregarLado(Lado lado) {
        if (lados.size() < 6) {
            lados.add(lado);
        }
    }



    public Lado getLado(int indice) {
        return lados.get(indice);
    }

    public void crearVertices(Map<Cubic, Vertice> verticesUnicos, Map<Coordenada, Vertice> verticesPorCoordenada, Axial posicion, int terrenoId, Cubic[] Vertice_OFFSETS) {

        Cubic centro = posicion.toCubic();

        for (int i = 0; i < 6; i++) {
            Cubic vCoord = centro.add(Vertice_OFFSETS[i]);
            Vertice v = verticesUnicos.computeIfAbsent(vCoord, k -> new Vertice());
            this.agregarVertice(v);

            Coordenada coord = new Coordenada(terrenoId, i);
            verticesPorCoordenada.put(coord, v);
        }

    }

    public void crearLados(Map<Cubic, Lado> ladosUnicos, Map<Coordenada, Lado> ladosPorCoordenada, Axial posicion, int id, Cubic[] Lado_OFFSETS) {

        Cubic centro = posicion.toCubic();

        for (int i = 0; i < 6; i++) {

            Vertice v1 = vertices.get(i);
            Vertice v2 = vertices.get((i + 1) % 6);

            Cubic vOffset1 = Lado_OFFSETS[i];


            Cubic ladoCoord = centro
                    .add(vOffset1);


            // Crear o obtener lado existente
            Lado lado = ladosUnicos.computeIfAbsent(ladoCoord, k -> {
                Lado nuevoLado = new Lado();
                nuevoLado.agregarPunta(v1);
                nuevoLado.agregarPunta(v2);
                return nuevoLado;
            });

            this.agregarLado(lado);

            ladosPorCoordenada.put(new Coordenada(id, i), lado);
        }
    }

    public boolean tieneVertice(Vertice v) {
        return vertices.contains(v);
    }

    public void activarVertices(Terreno terrenoOrigen) {
        if (bloqueadoPorLadron) return; // Lógica del ladrón

        for (Vertice v : vertices) {
            v.cosechar(terrenoOrigen); // Delega al vértice
        }
    }
}
