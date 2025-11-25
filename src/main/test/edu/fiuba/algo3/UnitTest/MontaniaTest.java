package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Montania;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class MontaniaTest {

    @Test
    public void test01MontaniaDevuelveSuTipo(){
        Montania montania = new Montania();
        String tipoEsperado = "Montania";

        assertEquals(tipoEsperado, montania.getTipoTerreno());
    }

    @Test
    public void test02MontaniaDevueveElRecursoConLacantidadOtorgada(){
        Montania montania = new Montania();
        int cantidadDeRecurso = 5;
        Mineral recursoEsperado = new Mineral(cantidadDeRecurso);

        assertEquals(recursoEsperado, montania.recursoOtorgado(cantidadDeRecurso));

    }
}
