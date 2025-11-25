package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Campo;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class CampoTest {
    @Test
    public void test01CampoDevuelveSuTipo(){
        Campo campo = new Campo();
        String tipoEsperado = "Campo";

        assertEquals(tipoEsperado, campo.getTipoTerreno());
    }

    @Test
    public void test02CampoDevueveElRecursoConLacantidadOtorgada(){
        Campo campo = new Campo();
        int cantidadDeRecurso = 5;
        Grano recursoEsperado = new Grano(cantidadDeRecurso);

        assertEquals(recursoEsperado, campo.recursoOtorgado(cantidadDeRecurso));

    }
}
