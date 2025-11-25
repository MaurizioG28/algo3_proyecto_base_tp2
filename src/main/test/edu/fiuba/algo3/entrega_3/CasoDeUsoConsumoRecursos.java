package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.CasosDeUso.ConsumoRecursos;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Produccion;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CasoDeUsoConsumoRecursos {
    List<Terreno> hexagonos = Arrays.asList(
            new Bosque(), new Campo(), new Bosque(), new Pastizal(), new Bosque(),
            new Campo(), new Montania(),
            new Campo(),    // ID 8 -> Ficha 6 (Grano)
            new Montania(), // ID 9 -> Ficha 6 (Mineral)
            new Campo(), new Colina(), new Colina(), new Desierto(), new Colina(),
            new Pastizal(), new Montania(), new Pastizal(), new Bosque(), new Pastizal()
    );

    List<Produccion> fichasNumeradas = new LinkedList<>(Arrays.asList(
            new Produccion(2), new Produccion(3), new Produccion(3), new Produccion(4),
            new Produccion(4), new Produccion(5), new Produccion(5),
            new Produccion(6), // ID 8 (Campo)
            new Produccion(6), // ID 9 (Montaña)
            new Produccion(8), new Produccion(8), new Produccion(9), new Produccion(9),
            new Produccion(10), new Produccion(10), new Produccion(11), new Produccion(11),
            new Produccion(12)
    ));
    Banco banco = new Banco();
    Jugador jugador = new Jugador("Constructor", new Color("Rojo"));
    Madera madera = new Madera(1);
    Ladrillo ladrillo = new Ladrillo(1);
    Lana lana = new Lana(1);
    Grano grano = new Grano(1);

    @BeforeEach
    public void setUp() {
        banco.recibir(new Grano(20));
        banco.recibir(new Madera(20));
        banco.recibir(new Mineral(20));
        banco.recibir(new Lana(20));
        banco.recibir(new Ladrillo(20));


    }


    @Test
    public void test01JugadorConstruyeUnPobladoExitosamente() throws Exception {
        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        Jugador jugador = new Jugador("Constructor", new Color("Rojo"));
        jugador.agregarRecurso(madera);
        jugador.agregarRecurso(ladrillo);
        jugador.agregarRecurso(lana);
        jugador.agregarRecurso(grano);
        Coordenada coord = new Coordenada(5, 0);
        ConsumoRecursos caso = new ConsumoRecursos();

        try {
            caso.construirPoblado(jugador, tablero, coord);
        } catch (RecursosIsuficientesException e) {
            throw new RuntimeException(e);
        }

        int maderaEsperada = 0;
        int ladrilloEsperado = 0;
        int lanaEsperada = 0;
        int granoEsperado = 0;

        assertEquals(maderaEsperada, jugador.cantidadMadera());
        assertEquals(ladrilloEsperado, jugador.cantidadLadrillo());
        assertEquals(lanaEsperada, jugador.cantidadLana());
        assertEquals(granoEsperado, jugador.cantidadGrano());
    }

    @Test
    public void test02JugadorConstruyeUnPobladoSinTodosLosRecursos() {
        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        Jugador jugador = new Jugador("Constructor", new Color("Rojo"));
        jugador.agregarRecurso(madera);
        jugador.agregarRecurso(ladrillo);
        jugador.agregarRecurso(lana);
        Coordenada coord = new Coordenada(5, 0);
        ConsumoRecursos caso = new ConsumoRecursos();

        assertThrows(RecursosInsuficientesException.class, () -> caso.construirPoblado(jugador, tablero, coord));

        int maderaEsperada = 1;
        int ladrilloEsperado = 1;
        int lanaEsperada = 1;
        int granoEsperado = 0;

        assertEquals(maderaEsperada, jugador.cantidadMadera());
        assertEquals(ladrilloEsperado, jugador.cantidadLadrillo());
        assertEquals(lanaEsperada, jugador.cantidadLana());
        assertEquals(granoEsperado, jugador.cantidadGrano());
    }

    @Test
    public void test03NoSePuedeConstruirSobreUnPobladoLosRecursosNoSeConsumen() throws RecursosIsuficientesException {

        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);

        Jugador jugador = new Jugador("Constructor", new Color("Rojo"));
        Coordenada coord = new Coordenada(5, 0);
        Vertice vertice = tablero.obtenerVertice(coord);
        Poblado poblado = new Poblado(jugador.getColor());
        try {
            vertice.colocar(poblado); // Poblado inicial
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }

        jugador.agregarRecurso(madera);
        jugador.agregarRecurso(ladrillo);
        jugador.agregarRecurso(lana);
        jugador.agregarRecurso(grano);

        ConsumoRecursos caso = new ConsumoRecursos();
        caso.construirPoblado(jugador, tablero, coord);


        int maderaEsperada = 1;
        int ladrilloEsperado = 1;
        int lanaEsperada = 1;
        int granoEsperado = 1;

        assertEquals(maderaEsperada, jugador.cantidadMadera());
        assertEquals(ladrilloEsperado, jugador.cantidadLadrillo());
        assertEquals(lanaEsperada, jugador.cantidadLana());
        assertEquals(granoEsperado, jugador.cantidadGrano());


    }

    @Test
    public void test04NoSePuedeConstruirConPobladoAdyacenteLosRecursosNoSeConsumen() throws RecursosIsuficientesException {

        Tablero tablero = TableroFactory.crear(hexagonos, fichasNumeradas);
        ServicioComercio servicio = new ServicioComercio(banco);

        Jugador jugador = new Jugador("Constructor", new Color("Rojo"));

        // Setup: El jugador YA tiene un poblado en el tablero
        Coordenada coord = new Coordenada(5, 0);
        Coordenada coordAdyacente = new Coordenada(5, 1);
        Vertice vertice = tablero.obtenerVertice(coord);
        Poblado poblado = new Poblado(jugador.getColor());
        try {
            vertice.colocar(poblado); // Poblado inicial
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }

        jugador.agregarRecurso(madera);
        jugador.agregarRecurso(ladrillo);
        jugador.agregarRecurso(lana);
        jugador.agregarRecurso(grano);

        ConsumoRecursos caso = new ConsumoRecursos();
        caso.construirPoblado(jugador, tablero, coordAdyacente);


        int maderaEsperada = 1;
        int ladrilloEsperado = 1;
        int lanaEsperada = 1;
        int granoEsperado = 1;

        assertEquals(maderaEsperada, jugador.cantidadMadera());
        assertEquals(ladrilloEsperado, jugador.cantidadLadrillo());
        assertEquals(lanaEsperada, jugador.cantidadLana());
        assertEquals(granoEsperado, jugador.cantidadGrano());


    }
}
