package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoLadronEnTerrenoTest {
    //Verificar que el terreno no pueda producir recursos cuando hay un ladron.

    @Test
    public void Test01TerrenoNoPuedeProducirSiUnLadronEsColocado(){
        Terreno terreno = new Bosque();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 0;

        vertice.colocarPoblado(jugador);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));
        hexagono.moverLadron();

        terreno.producirRecurso();

        assertEquals(totalRecursosEsperados,jugador.cantidadRecurso(new Madera(0)));
    }

    @Test
    public void Test02TerrenoDeberiaProducirNormalmenteSiNoTieneUnLadronBloqueando(){
        Terreno terreno = new Bosque();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        int totalRecursosEsperados = 1;

        vertice.colocarPoblado(jugador);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));

        terreno.producirRecurso();

        assertEquals(totalRecursosEsperados,jugador.cantidadRecurso(new Madera(0)));
    }

    @Test
    public void Test03TerrenoDeserticoNoDeberiaProducirRecursosBajoNingunaCircustancia(){
        Terreno terreno = new Desierto();
        Hexagono hexagono = new Hexagono();
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 0;

        vertice.colocarPoblado(jugador);
        hexagono.agregarVertice(vertice);
        terreno.asignarHexagono(hexagono);
        terreno.setProduccion(new Produccion(2));

        terreno.producirRecurso();

        assertEquals(totalRecursosEsperados,jugador.cantidadRecurso(new Madera(0)));
    }

}
