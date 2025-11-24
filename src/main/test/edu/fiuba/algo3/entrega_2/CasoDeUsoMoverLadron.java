package edu.fiuba.algo3.entrega_2;
import edu.fiuba.algo3.modelo.CasosDeUso.CasoDeUsoLadron;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Colina;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CasoDeUsoMoverLadron {



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
        terrenoDesierto.setId(2);
        terrenoDesierto.setPosicion(new Axial(1, 0));
        terrenoDesierto.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terrenoDesierto);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);

        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador("rojo",new Color("Rojo"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);

        caso.moverLadron(actual,1);

        assertFalse(terreno.sePuedeProducir());
    }
    @Test
    void Test02MoverladronANuevaPosicionSinVictimas(){
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
        terrenoDesierto.setId(2);
        terrenoDesierto.setPosicion(new Axial(1, 0));
        terrenoDesierto.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terrenoDesierto);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);
        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador("rojo",new Color("Rojo"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);

        List<Color> victimas= caso.moverLadron(actual,1);
        assertTrue(victimas.isEmpty());
    }
    @Test
    void test03MoverLadronConUnaVictima() {
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
        terrenoDesierto.setId(2);
        terrenoDesierto.setPosicion(new Axial(1, 0));
        terrenoDesierto.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terrenoDesierto);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }


        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);
        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador("rojo",new Color("Rojo"));
        Jugador victima1 = new Jugador("azul",new Color("Azul"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);
        try {
            caso.colocarVictima(victima1.getColor(),new Coordenada(1,0));
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
        List<Color> victimas= caso.moverLadron(actual,1);
        Color victima = victima1.getColor();
        assertEquals(1, victimas.size());
        assertTrue(victimas.contains(victima));

    }

    @Test
    void test04MoverLadronConMuchasVictimas() {
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
        terrenoDesierto.setId(2);
        terrenoDesierto.setPosicion(new Axial(1, 0));
        terrenoDesierto.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terrenoDesierto);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);
        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador("rojo",new Color("Rojo"));
        Jugador victima1 = new Jugador("azul",new Color("Azul"));
        Jugador victima2 = new Jugador("verde",new Color("Verde"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);
        try {
            caso.colocarVictima(victima1.getColor(),new Coordenada(1,0));
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }

        try {
            caso.colocarVictima(victima2.getColor(),new Coordenada(1,3));
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }


        List<Color> victimas= caso.moverLadron(actual,1);
        List<Color> victimasEsperadas = List.of(victima1.getColor(), victima2.getColor());

        assertEquals(2, victimas.size());

        assertTrue(victimas.containsAll(victimasEsperadas));

    }

    @Test
    void test05MoverLadronYrobarRecurso(){
        Map<Integer, Terreno> terrenosPorId = new HashMap<>();
        Map<Coordenada, Vertice> verticesPorCoordenada = new HashMap<>();
        Map<Coordenada, Lado> ladosPorCoordenada = new HashMap<>();

        Terreno terreno = new Colina();
        terreno.setId(1);
        terreno.setPosicion(new Axial(0, 0));
        terreno.asignarHexagono(new Hexagono());
        terreno.setProduccion(new Produccion(3));
        terrenosPorId.put(1, terreno);

        Terreno terrenoDesierto = new Desierto();
        terrenoDesierto.setId(2);
        terrenoDesierto.setPosicion(new Axial(1, 0));
        terrenoDesierto.asignarHexagono(new Hexagono());
        terrenosPorId.put(2, terrenoDesierto);

        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(1, i), new Vertice());
        }
        for (int i = 0; i < 6; i++) {
            verticesPorCoordenada.put(new Coordenada(2, i), new Vertice());
        }
        terreno.agregarVertices(verticesPorCoordenada);
        terrenoDesierto.agregarVertices(verticesPorCoordenada);
        Tablero tablero = new Tablero(terrenosPorId, verticesPorCoordenada, ladosPorCoordenada);

        Jugador actual = new Jugador("rojo",new Color("Rojo"));
        Jugador victima1 = new Jugador("azul",new Color("Azul"));
        CasoDeUsoLadron caso = new CasoDeUsoLadron(tablero,actual);
        try {
            caso.colocarVictima(victima1.getColor(),new Coordenada(1,0));
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
        victima1.agregarRecurso(terreno.recursoOtorgado(2));


        List<Color> victimas= caso.moverLadron(actual,1);
        Assertions.assertEquals(1, victimas.size());
        Assertions.assertEquals(victima1.getColor(), victimas.get(0));

        boolean roboExitoso = caso.robarRecursoDeVictima(victima1);
        Assertions.assertTrue(roboExitoso);

        int cantidadRobada=caso.verificarRecursoRobado(terreno.recursoOtorgado(1));
        Assertions.assertEquals(1, cantidadRobada);

        }
}
