package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.CasoColocacionRandom;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasoDeUsoCreacionRandomDeTablero {



    @Test
    void test01Crear2TableroRandomTieneMismosTerrenos(){
        CasoColocacionRandom tableroRandom = new CasoColocacionRandom();

        Tablero tablero1= tableroRandom.iniciarTablero(1234);
        Tablero tablero2= tableroRandom.iniciarTablero(1234);

        assertEquals(tablero1, tablero2);
    }
    @Test
    public void test03SeedsDistintosGeneranTablerosDiferentes() {
        CasoColocacionRandom tableroRandom = new CasoColocacionRandom();

        Tablero tablero1 = tableroRandom.iniciarTablero(1234);
        Tablero tablero2 = tableroRandom.iniciarTablero(5678);

        assert(!tablero1.equals(tablero2));
    }

    @Test
    public void test04DesiertoNoTieneFichaDeNumero() {
        CasoColocacionRandom tableroRandom = new CasoColocacionRandom();

        Tablero tablero = tableroRandom.iniciarTablero(1234);



        assertEquals(new Produccion(0),tableroRandom.buscarDesierto(tablero));
    }


}
