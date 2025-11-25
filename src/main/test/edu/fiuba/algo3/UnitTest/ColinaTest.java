package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Colina;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class ColinaTest {

    @Test
    public void test01ColinaDevuelveSuTipo(){
        Colina colina = new Colina();
        String tipoEsperado = "Colina";

        assertEquals(tipoEsperado, colina.getTipoTerreno());
    }

    @Test
    public void test02ColinaDevueveElRecursoConLacantidadOtorgada(){
        Colina colina = new Colina();
        int cantidadDeRecurso = 5;
        Ladrillo recursoEsperado = new Ladrillo(cantidadDeRecurso);

        assertEquals(recursoEsperado, colina.recursoOtorgado(cantidadDeRecurso));

    }
}
