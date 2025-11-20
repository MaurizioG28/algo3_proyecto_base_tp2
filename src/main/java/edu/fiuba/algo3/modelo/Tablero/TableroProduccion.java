package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Color;
import  edu.fiuba.algo3.modelo.Contruccion.*;
import edu.fiuba.algo3.modelo.Dividendo;

import java.util.*;

public class TableroProduccion {
    private final Map<Integer, Hexagono> hexagonos = new HashMap<>();
    private final Map<Coordenada, Vertice> vertices = new HashMap<>();
    private final Map<Coordenada, Lado> lados = new HashMap<>();
    private final Map<Color,Integer> pobladosPorColor = new HashMap<>();



    //private Coordenada posicionDelLadron;

    public TableroProduccion() {
        inicializarTableroFijo();
    }

    private void inicializarTableroFijo() {

        // 1. Crear hexágonos con coordenada FIJA


        hexagonos.put(1,new Hexagono(TipoTerreno.COLINA, 4));

        hexagonos.put(2,new Hexagono(TipoTerreno.COLINA, 8));

        hexagonos.put(3,new Hexagono(TipoTerreno.CAMPO, 6));

        // 2. Crear vértices FIJOS
        vertices.put(new Coordenada(1,0), new Vertice());
        vertices.put(new Coordenada(1,1), new Vertice());
        Vertice h1h3 = new Vertice();
        vertices.put(new Coordenada(1,2), h1h3);
        Vertice h1h2h3 = new Vertice();
        vertices.put(new Coordenada(1,3), h1h2h3);
        Vertice h1h2 = new Vertice();
        vertices.put(new Coordenada(1,4), h1h2);
        vertices.put(new Coordenada(1,5), new Vertice());
        vertices.put(new Coordenada(2,0), h1h2);
        vertices.put(new Coordenada(2,1), h1h2h3);
        vertices.put(new Coordenada(2,2), new Vertice());
        vertices.put(new Coordenada(2,3), new Vertice());
        vertices.put(new Coordenada(2,4), new Vertice());
        vertices.put(new Coordenada(2,5), new Vertice());
        vertices.put(new Coordenada(3,0), h1h3);
        vertices.put(new Coordenada(3,1), new Vertice());
        vertices.put(new Coordenada(3,2), new Vertice());
        vertices.put(new Coordenada(3,3), new Vertice());
        vertices.put(new Coordenada(3,4), new Vertice());
        vertices.put(new Coordenada(3,5), h1h2h3);



        lados.put(new Coordenada(2,3), new Lado());

        // 3. Relación hexágono-vértice
        conectarHexagonosVertices(1);
        conectarHexagonosVertices(2);
        conectarHexagonosVertices(3);

        conectarVerticesAdyacentes(1);
        conectarVerticesAdyacentes(2);
        conectarVerticesAdyacentes(3);

        // 4. Ladron en el desierto
       // this.posicionDelLadron = new Coordenada(3,3);
    }

    private void conectarHexagonosVertices(int indiceHexagono) {

        for (int i = 0 ; i < 6 ; i++) {
            hexagonos.get(indiceHexagono).agregarVertice(vertices.get(new Coordenada(indiceHexagono, i)));
        }

    }
    private void conectarVerticesAdyacentes(int hexId ) {

        for (int i = 0; i < 6; i++) {
            Vertice a = vertices.get(new Coordenada(hexId, i));
            Vertice b = vertices.get(new Coordenada(hexId, (i + 1) % 6));
            a.agregarAdyacente(b);
            b.agregarAdyacente(a);
        }
    }

    public Dividendo colocarPoblado(Poblado pieza, Coordenada coord) throws ReglaDistanciaException, ConstruccionExistenteException {
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
