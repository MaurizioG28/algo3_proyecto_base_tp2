package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import org.junit.jupiter.api.Test;

import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.BOSQUE;
import static edu.fiuba.algo3.modelo.Tablero.TipoTerreno.DESIERTO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HexagonoTest {
    //Verificar que el terreno no pueda producir recursos cuando hay un ladron.

    @Test
    public void Test01TerrenoNoTieneLadronYPuedeProducirRecursos(){
        Hexagono terreno = new Hexagono(BOSQUE, 2);

        assertTrue(terreno.sePuedeProducir());
    }

    @Test
    public void Test02TerrenoNoPuedeProducirSiTieneUnLadron(){
        Hexagono terreno = new Hexagono(DESIERTO, 2);

        assertFalse(terreno.sePuedeProducir());
    }
}
