package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Tablero.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoColocacionInicialTest {

    @Test
    public void test01CasoDeUsoColocacionInicialDePoblados() throws ReglaDistanciaException, ConstruccionExistenteException {
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,0));
        Dividendo dividendo = caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,3));

        Dividendo dividendosEsperadoes = new Dividendo(
                new Color("Azul" ),
                new Grano(1),
                new Ladrillo(2)
        );

        assertEquals( dividendosEsperadoes, dividendo);

    }

    @Test
    public void test02CasoDeUsoColocacionInicialDePobladosValidandoLaReglaDelaDistancia(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,0));
        } catch (ReglaDistanciaException | ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ReglaDistanciaException.class,() ->
                caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,1)));
    }

    @Test
    public void test03CasoDeUsoColocacionInicialDeCaminos(){
        var unTablero = new TableroProduccion();

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarCarretera( new Carretera( new Color("Azul")), new Coordenada(2,3));
        } catch (PosInvalidaParaConstruirException e) {
            throw new RuntimeException(e);
        }
        Coordenada caminoEsperadoEn = new Coordenada(2,3);

        assertTrue(caso.tineCarreteraEn(caminoEsperadoEn));

    }
}
