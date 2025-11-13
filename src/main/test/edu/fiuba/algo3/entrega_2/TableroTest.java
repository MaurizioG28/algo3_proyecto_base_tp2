package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TableroTest {

    private Hexagono hex(TipoTerreno t, int numero, Vertice... vs) {
        Hexagono h = new Hexagono(t, numero);
        for (Vertice v : vs) h.agregarVertice(v);
        return h;
    }
    private Vertice vPoblado(Jugador j) {
        Vertice v = new Vertice();
        v.colocarPoblado(j);
        return v;
    }
    private Vertice vCiudad(Jugador j) {
        Vertice v = new Vertice();
        v.colocarPoblado(j);
        v.mejorarACiudad();
        return v;
    }
    private Vertice vLibre() { return new Vertice(); }

    @Test
    public void test01InicializarTableroConHexagonosCreados() {
        Tablero tablero = new Tablero();
        tablero.setUp();
        assertTrue(tablero.tableroCorrectamenteInicializado());
    }

    @Test
    public void test02noDebePermitirConstruirPobladoEnVerticeAdyacente() {
        Jugador jugador1 = new Jugador();
        Tablero tablero = new Tablero();

        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();

        v1.agregarAdyacente(v2);
        v2.agregarAdyacente(v1);

        try {
            tablero.construirPoblado(jugador1, v1);
        } catch (ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ReglaDistanciaException.class,
                () -> tablero.construirPoblado(jugador1, v2));
    }

    @Test
    void Test03produccionDePoblado() {
        Jugador A = new Jugador();
        Tablero t = new Tablero();

        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 9, vPoblado(A), vLibre(), vLibre());
        t.agregarHexagono(bosque9);
        t.repartirProduccion(9); // <-- ahora sí se cargan recursos en A

        assertEquals(1, A.CantidadRecurso(Recurso.MADERA)); // 1 por poblado



    }
    @Test
    void Test04produccionDeCiudad() {
        Jugador A = new Jugador();
        Tablero t = new Tablero();

        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 9, vCiudad(A), vLibre(), vLibre());
        t.agregarHexagono(bosque9);
        t.repartirProduccion(9); // <-- ahora sí se cargan recursos en A

        assertEquals(2, A.CantidadRecurso(Recurso.MADERA)); // 1 por poblado



    }
    @Test
    void Test05jugadorSaca6ConPobladoYCiudadProduceRecursos() {
        Jugador A = new Jugador();
        Tablero t = new Tablero();
        int cantidadMaderaEsperada = 2;
        int cantidadGranoEsperada = 1;
        Hexagono bosque9 = hex(TipoTerreno.BOSQUE, 6, vCiudad(A), vLibre(), vLibre());
        Hexagono bosque6 = hex(TipoTerreno.CAMPO, 6, vPoblado(A), vLibre(), vLibre());
        t.agregarHexagono(bosque9);
        t.agregarHexagono(bosque6);
        t.repartirProduccion(9);
        t.repartirProduccion(6);

        assertEquals(cantidadMaderaEsperada, A.CantidadRecurso(Recurso.MADERA));
        assertEquals(cantidadGranoEsperada,A.CantidadRecurso(Recurso.GRANO));



    }

}
