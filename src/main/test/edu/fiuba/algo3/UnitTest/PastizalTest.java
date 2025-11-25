package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Campo;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Pastizal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PastizalTest {
    @Test
    public void test01PastizalDevuelveSuTipo(){
        Pastizal pastizal = new Pastizal();
        String tipoEsperado = "Pastizal";

        assertEquals(tipoEsperado, pastizal.getTipoTerreno());
    }

    @Test
    public void test02PastizalDevueveElRecursoConLacantidadOtorgada(){
        Pastizal pastizal = new Pastizal();
        int cantidadDeRecurso = 5;
        Lana recursoEsperado = new Lana(cantidadDeRecurso);

        assertEquals(recursoEsperado, pastizal.recursoOtorgado(cantidadDeRecurso));

    }
}
