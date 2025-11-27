package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.CasosDeUso.ColocacionInicial;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Ladrillo;
import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Tablero.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.Test;

import java.util.*;

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
                new Lana(1)
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

    @Test
    public void test05OrdenTurnosIniciales() {
        OrdenTurnosIniciales orden = new OrdenTurnosIniciales(4);

        List<Integer> esperado = List.of(0,1,2,3, 3,2,1,0);
        List<Integer> obtenido = new ArrayList<>();

        while (!orden.haTerminado()) {
            obtenido.add(orden.indiceJugadorActual());
            orden.avanzar();
        }

        assertEquals(esperado, obtenido);
    }

    @Test
    public void testColocacionInicialCompletaDosJugadores()
            throws ConstruccionExistenteException, ReglaDistanciaException, ReglaConstruccionException {

        // --- Setup del juego ---
        Catan catan = new Catan();
        Tablero tablero = catan.crearTablero();

        Jugador j1 = new Jugador("j1", new Color("azul"));
        Jugador j2 = new Jugador("j2", new Color("rojo"));

        ManagerTurno manager = new ManagerTurno( Arrays.asList(j1, j2), tablero,new Random(123));

        // --- RONDA 1 ---

        // JUGADOR 1 coloca poblado
        manager.colocacionInicial(new Coordenada(1, 0));

        assertEquals(j1, manager.getJugadorActual()); // sigue siendo j1
        assertEquals(0,j1.totalRecursos());              // primer poblado → sin recursos

        // JUGADOR 1 coloca carretera
        manager.colocacionInicial(new Coordenada(1, 0));

        assertEquals(j2, manager.getJugadorActualInicial()); // turno del jugador 2

        // --- JUGADOR 2 turno ---

        // JUGADOR 2 coloca poblado
        manager.colocacionInicial(new Coordenada(2, 0));

        assertEquals(j2, manager.getJugadorActualInicial());
        assertEquals(0,j2.totalRecursos());

        // JUGADOR 2 coloca carretera
        manager.colocacionInicial(new Coordenada(2, 0));

        assertEquals(j2, manager.getJugadorActualInicial()); // ¡Comienza la segunda ronda con jugador 2!

        // --- RONDA 2 ---

        // JUGADOR 2 coloca segundo poblado → obtiene recursos iniciales
        manager.colocacionInicial(new Coordenada(3, 0));

        assertTrue(0!=j2.totalRecursos());  // debería haber recibido varios recursos

        // JUGADOR 2 coloca carretera
        manager.colocacionInicial(new Coordenada(3, 0));

        assertEquals(j1, manager.getJugadorActualInicial()); // vuelve a j1

        // --- JUGADOR 1 turno final ---

        // JUGADOR 1 segundo poblado → obtiene recursos
        manager.colocacionInicial(new Coordenada(4, 0));

        assertTrue(0!=j1.totalRecursos());

        // JUGADOR 1 carretera final
        manager.colocacionInicial(new Coordenada(4, 0));
        Jugador jugadorEsperado = manager.getJugadorActualInicial();
        assertEquals(j1, manager.getJugadorActualInicial()); // comienza el juego normal

        // --- Verificaciones finales ---

        // Cada jugador tiene 2 poblados y 2 carreteras
        int pobladosj1=tablero.obtenerPobladosPorColor(j1.getColor());
        assertEquals(2, tablero.obtenerPobladosPorColor(j1.getColor()));
        assertEquals(2, tablero.obtenerPobladosPorColor(j2.getColor()));

        List<Lado> ladosj1=tablero.obtenerLadosDeJugador(j1.getColor());
        assertEquals(2, tablero.obtenerLadosDeJugador(j1.getColor()).size());
        assertEquals(2, tablero.obtenerLadosDeJugador(j2.getColor()).size());
    }

}
