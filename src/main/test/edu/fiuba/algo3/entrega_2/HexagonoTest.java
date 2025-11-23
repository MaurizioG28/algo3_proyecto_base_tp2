package edu.fiuba.algo3.entrega_2;
//
//import edu.fiuba.algo3.modelo.Jugador;
//import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
//import edu.fiuba.algo3.modelo.Tablero.Tablero;
//import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
//import org.junit.jupiter.api.Test;
//
//import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.BOSQUE;
//import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;
//import static org.junit.jupiter.api.Assertions.*;

public class HexagonoTest {
    //Verificar que el terreno no pueda producir recursos cuando hay un ladron.
/*
    @Test
    public void Test01TerrenoNoPuedeProducirSiUnLadronEsColocado(){
        Tablero tablero = new Tablero();

        Hexagono terreno = new Hexagono();
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 0;

        tablero.agregarHexagono(terreno);
        terreno.agregarVertice(vertice);
        vertice.colocarPoblado(jugador);

        terreno.moverLadron();
        tablero.repartirProduccion(2);

        assertEquals(totalRecursosEsperados,jugador.cantidadRecurso(Recurso.MADERA));
    }

    @Test
    public void Test02TerrenoDeberiaProducirNormalmenteSiNoTieneUnLadronBloqueando(){
        Tablero tablero = new Tablero();
        Hexagono terreno = new Hexagono(BOSQUE, 2);
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 1;

        tablero.agregarHexagono(terreno);
        terreno.agregarVertice(vertice);
        vertice.colocarPoblado(jugador);

        tablero.repartirProduccion(2);

        assertEquals(totalRecursosEsperados,jugador.cantidadRecurso(Recurso.MADERA));
    }

    @Test
    public void Test03TerrenoDeserticoNoDeberiaProducirRecursosBajoNingunaCircustancia(){
        Tablero tablero = new Tablero();
        Hexagono terreno = new Hexagono(DESIERTO, 2);
        Jugador jugador = new Jugador();
        Vertice vertice = new Vertice();
        Integer totalRecursosEsperados = 0;

        tablero.agregarHexagono(terreno);
        terreno.agregarVertice(vertice);
        vertice.colocarPoblado(jugador);

        terreno.moverLadron();
        tablero.repartirProduccion(2);
        terreno.moverLadron();
        tablero.repartirProduccion(2);

        assertEquals(totalRecursosEsperados,jugador.totalRecursos());
    }
    */
}
