package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Campo;
import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.TipoTerreno;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTest {

    @Test
    public void test01CampoNuevoTieneTipoTerrenoCampo(){
        TipoTerreno tipoCampo = TipoTerreno.CAMPO;
        Campo Campo = new Campo();

        assertTrue(Campo.esTipoTerreno(tipoCampo));
    }

    @Test
    public void test02CampoNuevoTieneCuatroMaximasCantidades(){
        int cantidadMaximaCampo = 4;
        Campo Campo = new Campo();

        assertTrue(Campo.esCantidadMaxima(cantidadMaximaCampo));
    }

    @Test
    public void test03CampoNuevoTieneCeroColocados(){
        int cantidadInicialCampo = 0;
        Campo Campo = new Campo();

        assertTrue(Campo.esCantidadColocada(cantidadInicialCampo));
    }

    @Test
    public void test04AgregarTerrenoSumaTerrenoColocado(){
        int cantidadTerrenosColocados = 2;
        int[] fichas = {1,2,4};
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        Campo Campo = new Campo();

        Campo.agregarTerreno(hexagonos, fichas);
        Campo.agregarTerreno(hexagonos, fichas);


        assertTrue(Campo.esCantidadColocada(cantidadTerrenosColocados));
    }
}
