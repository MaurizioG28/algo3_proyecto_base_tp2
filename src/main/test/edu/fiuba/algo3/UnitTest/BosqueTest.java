package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Bosque;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BosqueTest {

    @Test
    public void test01BosqueDevuelveSuTipo(){
        Bosque bosque = new Bosque();
        String tipoEsperado = "Bosque";

        assertEquals(tipoEsperado, bosque.getTipoTerreno());
    }

    @Test
    public void test02BosqueDevueveElRecursoConLacantidadOtorgada(){
        Bosque bosque = new Bosque();
        int cantidadDeRecurso = 5;
        Madera recursoEsperado = new Madera(cantidadDeRecurso);

        assertEquals(recursoEsperado, bosque.recursoOtorgado(cantidadDeRecurso));

    }

    @Test
    public void test03BosquePuedeConfigurarSuProduccion(){
        Bosque bosque = new Bosque();
        bosque.setProduccion(new Produccion(2));

        Produccion produccionEsperada = new Produccion(2);

        assertEquals(produccionEsperada, bosque.getProduccion());

    }

    @Test
    public void test04BosqueSabeQueNoEsDesierto(){
        Bosque bosque = new Bosque();

        assertFalse(bosque.esDesierto());
    }

}
