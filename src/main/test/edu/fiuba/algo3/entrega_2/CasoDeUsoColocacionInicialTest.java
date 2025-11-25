package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Tablero.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoColocacionInicialTest {

    List<Terreno> hexagonos = Arrays.asList(
            new Bosque(),
            new Campo(),
            new Bosque(),
            new Pastizal(),
            new Bosque(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Montania(),
            new Campo(),
            new Colina(),
            new Colina(),
            new Desierto(),
            new Colina(),
            new Pastizal(),
            new Montania(),
            new Pastizal(),
            new Bosque(),
            new Pastizal()
    );

    List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
            new Produccion(2),
            new Produccion(3),
            new Produccion(3),
            new Produccion(4),
            new Produccion(4),
            new Produccion(5),
            new Produccion(5),
            new Produccion(6),
            new Produccion(6),
            new Produccion(8),
            new Produccion(8),
            new Produccion(9),
            new Produccion(9),
            new Produccion(10),
            new Produccion(10),
            new Produccion(11),
            new Produccion(11),
            new Produccion(12)

    ));

    @Test
    public void test01CasoDeUsoColocacionInicialDePoblados() throws ReglaDistanciaException, ConstruccionExistenteException, ReglaConstruccionException {
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);

        ColocacionInicial caso = new ColocacionInicial(unTablero);

        caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,0));
        Dividendo dividendo = caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,3));

       Dividendo dividendosEsperadoes = new Dividendo(
                new Color("Azul" ),
                new Madera(1),
                new Grano(1)
        );

        assertEquals( dividendosEsperadoes, dividendo);

    }

    @Test
    public void test02CasoDeUsoColocacionInicialDePobladosValidandoLaReglaDelaDistancia(){
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);


        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,0));
        } catch (ReglaDistanciaException | ConstruccionExistenteException | ReglaConstruccionException e) {
            throw new RuntimeException(e);
        }

        assertThrows(ReglaDistanciaException.class,() ->
                caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(1,1)));
    }

    @Test
    public void test03CasoDeUsoColocacionInicialDeCaminos(){
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);


        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(2,3));
        } catch (ReglaDistanciaException | ConstruccionExistenteException | ReglaConstruccionException e) {
            throw new RuntimeException(e);
        }

        try {
            caso.colocarEn( new Carretera( new Color("Azul")), new Coordenada(2,3));
        } catch (ConstruccionExistenteException | ReglaDistanciaException | ReglaConstruccionException e) {
            throw new RuntimeException(e);
        }
        Coordenada caminoEsperadoEn = new Coordenada(2,3);

        assertTrue(caso.tineCarreteraEn(caminoEsperadoEn));

    }

    @Test
    public void test04CasoDeUsoColocacionInicialDeCaminosDebeFallar(){
        Tablero unTablero = TableroFactory.crear(hexagonos, fichasNumeradas);


        ColocacionInicial caso = new ColocacionInicial(unTablero);

        try {
            caso.colocarEn( new Poblado(new Color("Azul" )), new Coordenada(2,3));
        } catch (ReglaDistanciaException | ConstruccionExistenteException | ReglaConstruccionException e) {
            throw new RuntimeException(e);
        }






        assertThrows(ReglaConstruccionException.class,() ->caso.colocarEn( new Carretera( new Color("Azul")), new Coordenada(1,0)));

    }
}
