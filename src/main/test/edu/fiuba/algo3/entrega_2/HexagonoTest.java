package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Contruccion.Contruccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import org.junit.jupiter.api.Test;

import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.BOSQUE;
import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;
import static org.junit.jupiter.api.Assertions.*;

public class HexagonoTest {
    //Verificar que el terreno no pueda producir recursos cuando hay un ladron.

    @Test
    public void Test01TerrenoNoPuedeProducirSiUnLadronEsColocado(){
        Hexagono terreno = new Hexagono(BOSQUE, 2);
        Jugador jugador = new Jugador();
        Poblado tipo = new Poblado();
        Contruccion poblado = new Contruccion(jugador, tipo, 1);

        Integer totalRecursosEsperados = 0;

        terreno.agregarVivienda(poblado, 1);
        terreno.moverLadron();
        terreno.producirRecursos();

        assertEquals(totalRecursosEsperados,jugador.CantidadRecurso(Recurso.MADERA));
    }

    @Test
    public void Test02TerrenoDeberiaProducirNormalmenteSiNoTieneUnLadronBloqueando(){
        Hexagono terreno = new Hexagono(BOSQUE, 2);
        Jugador jugador = new Jugador();
        Poblado tipo = new Poblado();
        Contruccion poblado = new Contruccion(jugador, tipo, 1);

        Integer totalRecursosEsperados = 1;
        terreno.agregarVivienda(poblado, 1);
        terreno.producirRecursos();

        assertEquals(totalRecursosEsperados,jugador.CantidadRecurso(Recurso.MADERA));
    }

    @Test
    public void Test03TerrenoDeserticoNoDeberiaProducirRecursosBajoNingunaCircustancia(){
        Hexagono terreno = new Hexagono(DESIERTO, 2);
        Jugador jugador = new Jugador();
        Poblado tipo = new Poblado();
        Contruccion poblado = new Contruccion(jugador, tipo, 1);

        Integer totalRecursosEsperados = 0;

        terreno.agregarVivienda(poblado, 1);
        terreno.moverLadron();
        terreno.producirRecursos();
        terreno.moverLadron();
        terreno.producirRecursos();

        assertEquals(totalRecursosEsperados,jugador.totalRecursos());
    }
}
