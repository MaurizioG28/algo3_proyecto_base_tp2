package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Factory.Hexagono;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PastizalTest {

    @Test
    public void test01PastizalNuevoTieneTipoTerrenoPastizal(){
        TipoTerreno tipoPastizal = TipoTerreno.PRADERA;
        Pastizal Pastizal = new Pastizal();

        assertTrue(Pastizal.esTipoTerreno(tipoPastizal));
    }

    @Test
    public void test02PastizalNuevoTieneCuatroMaximasCantidades(){
        int cantidadMaximaPastizal = 4;
        Pastizal Pastizal = new Pastizal();

        assertTrue(Pastizal.esCantidadMaxima(cantidadMaximaPastizal));
    }

    @Test
    public void test03PastizalNuevoTieneCeroColocados(){
        int cantidadInicialPastizal = 0;
        Pastizal Pastizal = new Pastizal();

        assertTrue(Pastizal.esCantidadColocada(cantidadInicialPastizal));
    }

    @Test
    public void test04AgregarTerrenoSumaTerrenoColocado(){
        int cantidadTerrenosColocados = 2;
        int[] fichas = {1,2,4};
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        Pastizal Pastizal = new Pastizal();

        Pastizal.agregarTerreno(hexagonos, fichas);
        Pastizal.agregarTerreno(hexagonos, fichas);


        assertTrue(Pastizal.esCantidadColocada(cantidadTerrenosColocados));
    }
}
