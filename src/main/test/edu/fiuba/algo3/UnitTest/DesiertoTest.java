package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Desierto;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class DesiertoTest {

    @Test
    public void test01DesiertoDevuelveSuTipo(){
        Desierto desierto = new Desierto();
        String tipoEsperado = "Desierto";

        assertEquals(tipoEsperado, desierto.getTipoTerreno());
    }

    @Test
    public void test02DesiertoNoDevuelveRecursos(){
        Desierto desierto = new Desierto();
        int cantidadDeRecurso = 5;

        assertNull(desierto.recursoOtorgado(cantidadDeRecurso));
    }

    @Test
    public void test03DesiertoSabeQueEsUnDesierto(){
        Desierto desierto = new Desierto();

        assertTrue(desierto.esDesierto());
    }

    @Test
    public void test04DesiertoNoCambiaSuProduccion(){
        Desierto desierto = new Desierto();
        desierto.setProduccion(new Produccion(4));
        Produccion produccionEsperada = new Produccion(0);

        assertEquals(produccionEsperada, desierto.getProduccion());

    }
}
