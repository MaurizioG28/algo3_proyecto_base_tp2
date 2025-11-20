package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Tablero.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CasoDeUsoColocacionInicialTest {

    @Test
    public void test01CasoDeUsoColocacionInicialDePoblados(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(2,3));
        Dividendo dividendo = caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(2,3));

       Dividendo dividendosEsperadoes = new Dividendo(
                new Color("Azul" ),
                new Grano(2),
                new Ladrillo(4)
        );

        assertEquals( dividendosEsperadoes, dividendo);

    }

    @Test
    public void test01CasoDeUsoColocacionInicialDePobladosValidandoLaReglaDelaDistancia(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(3,5));
        } catch (ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ReglaDistanciaException.class,() ->
                caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(3,6)));
    }

    @Test
    public void test01CasoDeUsoColocacionInicialDeCaminos(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Carretera( new Color("Azul")), new Coordenada(2,3));

        // Completar

    }
}
