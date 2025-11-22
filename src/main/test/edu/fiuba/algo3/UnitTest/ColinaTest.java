package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class ColinaTest {

    @Test
    public void test01ColinaNuevoTieneTipoTerrenoColina(){
        TipoTerreno tipoColina = TipoTerreno.COLINA;
        Colina colina = new Colina();

        assertTrue(colina.esTipoTerreno(tipoColina));
    }

    @Test
    public void test02ColinaNuevoTieneTresMaximasCantidades(){
        int cantidadMaximaColina = 3;
        Colina Colina = new Colina();

        assertTrue(Colina.esCantidadMaxima(cantidadMaximaColina));
    }

    @Test
    public void test03ColinaNuevoTieneCeroColocados(){
        int cantidadInicialColina = 0;
        Colina Colina = new Colina();

        assertTrue(Colina.esCantidadColocada(cantidadInicialColina));
    }

    @Test
    public void test04AgregarTerrenoSumaTerrenoColocado(){
        int cantidadTerrenosColocados = 2;
        int[] fichas = {1,2,4};
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        Colina Colina = new Colina();

        Colina.agregarTerreno(hexagonos, fichas);
        Colina.agregarTerreno(hexagonos, fichas);


        assertTrue(Colina.esCantidadColocada(cantidadTerrenosColocados));
    }
}
