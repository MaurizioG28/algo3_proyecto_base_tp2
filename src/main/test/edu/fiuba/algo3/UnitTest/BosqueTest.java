package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BosqueTest {

    @Test
    public void test01BosqueNuevoTieneTipoTerrenoBosque(){
        TipoTerreno tipoBosque = TipoTerreno.BOSQUE;
        Bosque bosque = new Bosque();

        assertTrue(bosque.esTipoTerreno(tipoBosque));
    }

    @Test
    public void test02BosqueNuevoTieneCuatroMaximasCantidades(){
        int cantidadMaximaBosque = 4;
        Bosque bosque = new Bosque();

        assertTrue(bosque.esCantidadMaxima(cantidadMaximaBosque));
    }

    @Test
    public void test03BosqueNuevoTieneCeroColocados(){
        int cantidadInicialBosque = 0;
        Bosque bosque = new Bosque();

        assertTrue(bosque.esCantidadColocada(cantidadInicialBosque));
    }

    @Test
    public void test04AgregarTerrenoSumaTerrenoColocado(){
        int cantidadTerrenosColocados = 2;
        int[] fichas = {1,2,4};
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        Bosque bosque = new Bosque();

        bosque.agregarTerreno(hexagonos, fichas);
        bosque.agregarTerreno(hexagonos, fichas);


        assertTrue(bosque.esCantidadColocada(cantidadTerrenosColocados));
    }
}
