package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Color;
import  edu.fiuba.algo3.modelo.Contruccion.*;
import edu.fiuba.algo3.modelo.Dividendo;

import java.util.*;

public class TableroProduccion {
    private final Map<Coordenada, Hexagono> hexagonos = new HashMap<>();
    private final Map<Coordenada, Vertice> vertices = new HashMap<>();
    private final Map<Coordenada, Lado> lados = new HashMap<>();
    private final Map<Color,Integer> pobladosPorColor = new HashMap<>();



    private Coordenada posicionDelLadron;

    public TableroProduccion() {
        inicializarTableroFijo();
    }

    private void inicializarTableroFijo() {

        // 1. Crear hexágonos con coordenada FIJA


        hexagonos.put(new Coordenada(3,3),new Hexagono(TipoTerreno.COLINA, 4));

        hexagonos.put(new Coordenada(3,4),new Hexagono(TipoTerreno.COLINA, 8));

        hexagonos.put(new Coordenada(4,3),new Hexagono(TipoTerreno.CAMPO, 7));

        // 2. Crear vértices FIJOS
        vertices.put(new Coordenada(2,3), new Vertice());
        vertices.put(new Coordenada(3,2), new Vertice());
        vertices.put(new Coordenada(4,3), new Vertice());
        vertices.put(new Coordenada(2,4), new Vertice());
        vertices.put(new Coordenada(3,6), new Vertice());
        vertices.put(new Coordenada(4,4), new Vertice());
        vertices.put(new Coordenada(3,5), new Vertice());
        vertices.put(new Coordenada(3,4), new Vertice());
        vertices.put(new Coordenada(3,4), new Vertice());
        vertices.put(new Coordenada(2,5), new Vertice());
        vertices.put(new Coordenada(4,5), new Vertice());


        lados.put(new Coordenada(2,3), new Lado());

        // 3. Relación hexágono-vértice
        conectarHexagonosVertices();

        // 4. Ladron en el desierto
        this.posicionDelLadron = new Coordenada(3,5);
    }

    private void conectarHexagonosVertices() {
        Hexagono h1 = hexagonos.get(new Coordenada(3,3));

        Coordenada[] v1coords = {
                new Coordenada(2,3),
                new Coordenada(3,2),
                new Coordenada(4,3),
                new Coordenada(4,4),
                new Coordenada(3,4),
                new Coordenada(2,4)
        };

        conectarVerticesAdyacentes(v1coords);



        Hexagono h2 = hexagonos.get(new Coordenada(3,4));

        Coordenada[] v2coords = {
                new Coordenada(2,4),
                new Coordenada(3,4),
                new Coordenada(4,4),
                new Coordenada(4,5),
                new Coordenada(3,5),
                new Coordenada(2,5)
        };

        conectarVerticesAdyacentes(v2coords);

        Vertice v35 = vertices.get(new Coordenada(3,5));
        Vertice v36 = vertices.get(new Coordenada(3,6));

        v35.agregarAdyacente(v36);
        v36.agregarAdyacente(v35);

        Hexagono h3 = hexagonos.get(new Coordenada(4,3));

        h1.agregarVertice(vertices.get(new Coordenada(3,4)));

        h2.agregarVertice(vertices.get(new Coordenada(3,4)));

        h3.agregarVertice(vertices.get(new Coordenada(3,4)));
    }
    private void conectarVerticesAdyacentes(Coordenada[] vCoords ) {

        List<Vertice> v = new ArrayList<>();
        for (Coordenada c : vCoords) {
            Vertice vertice = vertices.get(c);
            v.add(vertice);
        }
        for (int i = 0; i < v.size(); i++) {
            Vertice a = v.get(i);
            Vertice b = v.get((i + 1) % v.size());
            a.agregarAdyacente(b);
            b.agregarAdyacente(a);
        }
    }

    public Dividendo colocarPoblado(Poblado pieza, Coordenada coord) throws ReglaDistanciaException {
        Vertice vertice = vertices.get(coord);
        if (vertice == null) throw new IllegalArgumentException("Coordenada inválida");

        if (vertice.tieneConstruccionAdyacente())
            throw new ReglaDistanciaException("No se puede colocar el poblado por la regla de distancia");

        vertice.colocar(pieza);
        pobladosPorColor.put(pieza.getColorActual(), pobladosPorColor.getOrDefault(pieza.getColorActual(), 0) + 1);


        if ( esSegundoPoblado(pieza.getColorActual()))
            return calcularDividendosIniciales(coord,pieza.getColorActual());

        return Dividendo.vacio();
    }

    private Dividendo calcularDividendosIniciales(Coordenada coord, Color colorActual) {
        Vertice v = vertices.get(coord);
        Dividendo d = new Dividendo(colorActual);

        for (Hexagono h : hexagonos.values()) {
            if (h.getVertices().contains(v) && h.getTipo().produceAlgo()) {
                d.agregar(Objects.requireNonNull(h.getTipo().recursoOtorgado()));
            }
        }

        return d;
    }

    private boolean esSegundoPoblado(Color color) {
        return (pobladosPorColor.get(color)==2);
    }

    public void colocarCarretera(Carretera pieza, Coordenada coordenada) throws PosInvalidaParaConstruirException {
        Lado lado = lados.get(coordenada);
        if (lado == null) {
            throw new IllegalArgumentException("Coordenada invalida para camino");
        }

        if (lado.tieneConstruccion()) {
            throw new PosInvalidaParaConstruirException("Ya hay una carretera en esa posición");
        }

        lado.colocar(pieza);
    }
    public boolean tieneCarreteraEn(Coordenada coord) {
        Lado lado = lados.get(coord);
        return lado != null && lado.tieneConstruccion();
    }

}
