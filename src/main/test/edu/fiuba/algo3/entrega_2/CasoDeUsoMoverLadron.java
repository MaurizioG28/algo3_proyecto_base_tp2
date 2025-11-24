package edu.fiuba.algo3.entrega_2;
import edu.fiuba.algo3.modelo.CasosDeUso.CasoDeUsoLadron;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CasoDeUsoMoverLadron {

    private void agregarTerrenoAMapDeTerrenos(Terreno tipoTerreno, int idTerreno, Axial posicionTerreno, int numProduccion, Map<Integer, Terreno> terrenosPorId) {
        tipoTerreno.setId(idTerreno);
        tipoTerreno.setPosicion(posicionTerreno);
        tipoTerreno.asignarHexagono(new Hexagono());
        tipoTerreno.setProduccion(new Produccion(numProduccion));
        terrenosPorId.put(idTerreno, tipoTerreno);
    }

    private void conectarTerrenoConVertice(Terreno terreno,Map<Coordenada, Vertice> vertices){
        terreno.agregarVertices(vertices);
    }

    @Test
    void Test01MoverladronANuevaPosicion(){
        // Crear un terreno de prueba

        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada = new HashMap<>();

        Terreno terreno = new Bosque();
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terreno.setProduccion(new Produccion(2));
        terrenosPorId.put(1, terreno);

        Terreno terrenoDesierto = new Desierto();
        terreno.setId(2);
        terreno.setPosicion(new Axial(1, 0));
        terreno.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terreno);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);

        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador(new Color("Rojo"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);

        caso.moverLadron(actual,1);

        assertFalse(terreno.sePuedeProducir());
    }
//    @Test
//    void Test07MoverladronANuevaPosicionSinVictimas(){
//        Tablero tablero = new Tablero();
//        tablero.agregarHexagono( hex(TipoTerreno.DESIERTO, 7, vLibre(), vLibre(), vLibre()));
//        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 9,vLibre(), vLibre(), vLibre());
//        tablero.agregarHexagono( bosque9);
//
//
//        Jugador actual = new Jugador();
//        List<Jugador> victimas=tablero.moverLadron(actual,bosque9);
//        assertTrue(victimas.isEmpty());
//    }
//    @Test
//    void test08MoverLadronConUnaVictima() {
//        Tablero tablero = new Tablero();
//        Jugador actual = new Jugador();
//        Jugador victima = new Jugador();
//        Hexagono desierto = hex(TipoTerreno.DESIERTO, 0,vLibre(), vLibre(), vLibre());
//        tablero.agregarHexagono(desierto);
//        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 9,vPoblado(victima), vLibre(), vLibre());
//        tablero.agregarHexagono( bosque9);
//
//
//        List<Jugador> victimas = tablero.moverLadron(actual, bosque9);
//        assertEquals(1, victimas.size());
//        assertTrue(victimas.contains(victima));
//
//    }
//
//    @Test
//    void test09MoverLadronConMuchasVictimas() {
//        Tablero tablero = new Tablero();
//        Jugador actual = new Jugador();
//        Jugador victima1 = new Jugador();
//        Jugador victima2 = new Jugador();
//        Hexagono desierto = hex(TipoTerreno.DESIERTO, 0,vLibre(), vLibre(), vLibre());
//        tablero.agregarHexagono(desierto);
//        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 9,vPoblado(victima1), vPoblado(victima2), vLibre());
//        tablero.agregarHexagono( bosque9);
//        List<Jugador> victimasEsperadas = List.of(victima1, victima2);
//
//        List<Jugador> victimas = tablero.moverLadron(actual, bosque9);
//        assertEquals(2, victimas.size());
//
//        assertTrue(victimas.containsAll(victimasEsperadas));
//
//    }
}
