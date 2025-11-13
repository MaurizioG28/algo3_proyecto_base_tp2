package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Tablero.Montania;
import edu.fiuba.algo3.modelo.Tablero.Hexagono;
import edu.fiuba.algo3.modelo.Tablero.TipoTerreno;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MontaniaTest {

    @Test
    public void test01MontaniaNuevoTieneTipoTerrenoMontania(){
        TipoTerreno tipoMontania = TipoTerreno.MONTANIA;
        Montania Montania = new Montania();

        assertTrue(Montania.esTipoTerreno(tipoMontania));
    }

    @Test
    public void test02MontaniaNuevoTieneTresMaximasCantidades(){
        int cantidadMaximaMontania = 3;
        Montania Montania = new Montania();

        assertTrue(Montania.esCantidadMaxima(cantidadMaximaMontania));
    }

    @Test
    public void test03MontaniaNuevoTieneCeroColocados(){
        int cantidadInicialMontania = 0;
        Montania Montania = new Montania();

        assertTrue(Montania.esCantidadColocada(cantidadInicialMontania));
    }

    @Test
    public void test04AgregarTerrenoSumaTerrenoColocado(){
        int cantidadTerrenosColocados = 2;
        int[] fichas = {1,2,4};
        ArrayList<Hexagono> hexagonos = new ArrayList<>();
        Montania Montania = new Montania();

        Montania.agregarTerreno(hexagonos, fichas);
        Montania.agregarTerreno(hexagonos, fichas);


        assertTrue(Montania.esCantidadColocada(cantidadTerrenosColocados));
    }
}
