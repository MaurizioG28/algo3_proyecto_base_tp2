package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Tablero.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CasoDeUsoColocacionInicialTest {

    @Test
    public void test01CasoDeUsoColocacionInicialDePoblados(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Poblado("Azul"), new Coordenada(2,3));
        Dividendo dividento = caso.colocarEn( new Poblado("Azul"), new Coordenada(2,3));

        Dividendo dividendosEsperadoes = new Dividendo(
                new Color("Azul" ),
                new Grano(2),
                new Ladrillo(4)
        );

        assertEquals( dividendosEsperadoes, dividento);

    }

    @Test
    public void test01CasoDeUsoColocacionInicialDePobladosValidandoLaReglaDelaDistancia(){
        var unTablero = new Tablero();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Poblado("Azul"), new Coordenada(3,5));

        assertThrows(ReglaDistanciaException.class,() ->
                caso.colocarEn( new Poblado("Azul"), new Coordenada(3,6)));
    }

    @Test
    public void test01CasoDeUsoColocacionInicialDeCaminos(){
        var unTablero = new Tablero();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Camino( new Color("Azul")), new Coordenada(2,3));

        // Completar

    }
}
