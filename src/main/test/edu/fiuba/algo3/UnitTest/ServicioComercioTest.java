package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ServicioComercioTest {
    private Banco banco;
    private Jugador jugador;
    private ServicioComercio servicio;

    // Supongamos que usas un String o Enum para el color
    private final Color COLOR_ROJO =  new Color("Rojo");

    @BeforeEach
    void setUp() {
        // 1. Instanciamos las clases REALES
        banco = new Banco();
        jugador = new Jugador("TestPlayer", COLOR_ROJO); // Ajusta según tu constructor de Color
        servicio = new ServicioComercio(banco);

        // --- CARGA DE DATOS (SETUP) ---

        // 2. Seteamos recursos al BANCO (El banco necesita tener Ladrillo para darnos)
        // Le damos 10 de cada cosa al banco
        banco.recibir(new Madera(10));
        banco.recibir(new Ladrillo(10));
        banco.recibir(new Grano(10));

        // 3. Seteamos recursos al JUGADOR
        // Le damos 4 de Madera al jugador para que pueda intercambiar
        jugador.agregarRecurso(new Madera(4));
    }

    @Test
    void test01IntercambioRealConObjetos() {

        assertEquals(4, jugador.cantidadRecurso(new Madera(0)));
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo(0)));
        assertTrue(banco.tieneStock(new Ladrillo(0), 1));


        // Jugador da 4 Madera -> Pide 1 Ladrillo
        servicio.intercambiarConBanco(
                jugador,
                new Madera(4), // Recurso entregado
                4,             // Cantidad
                new Ladrillo(0) // Recurso deseado (tipo)
        );


        assertEquals(0, jugador.cantidadRecurso(new Madera(0)));
        assertEquals(1, jugador.cantidadRecurso(new Ladrillo(0)));


        // El banco tenía 10 Madera, recibió 4 => Ahora tiene 14
        assertTrue(banco.tieneStock(new Madera(0), 14));
        // El banco tenía 10 Ladrillo, entregó 1 => Ahora tiene 9
        assertTrue(banco.tieneStock(new Ladrillo(0), 9));
        // Verificamos que ya NO tenga 10 (para asegurar que restó)
        assertFalse(banco.tieneStock(new Ladrillo(0), 10));
    }

    @Test
    void test02VenderPobladoReal() throws Exception {

        // Costo: Madera, Ladrillo, Lana, Grano
        jugador.agregarRecurso(new Madera(1)); // Ya tenía 4 del setUp, ahora tiene 5
        jugador.agregarRecurso(new Ladrillo(1));
        jugador.agregarRecurso(new Lana(1));
        jugador.agregarRecurso(new Grano(1));


        servicio.venderPoblado(jugador);

        // Verificamos que se restaron los recursos
        // Ladrillo: Tenía 1, gastó 1 => Queda 0
        assertEquals(0, jugador.cantidadRecurso(new Ladrillo(0)));
        // Lana: Tenía 1, gastó 1 => Queda 0
        assertEquals(0, jugador.cantidadRecurso(new Lana(0)));

        // Verificamos que el BANCO recibió esos recursos
        // El banco empezó con 10 de Grano (en el setUp), recibió 1 del pago => 11
        assertTrue(banco.tieneStock(new Grano(0), 11));
    }

    @Test
    void test03JugadorCompraYAgregaCartaDesarrollo() {
        //El jugador necesita 1 Grano, 1 Mineral, 1 Lana
        jugador.agregarRecurso(new Grano(1));
        jugador.agregarRecurso(new Mineral(1));
        jugador.agregarRecurso(new Lana(1));

        // Compra la carta
        // El servicio cobra y nos ENTREGA la carta en la mano
        CartaDesarrollo cartaNueva = servicio.venderCartaDesarrollo(jugador, 1);

        // Simulamos la acción del juego de guardar la carta en el mazo del jugador
        jugador.agregarCarta(cartaNueva);

        //  Verificaciones

        // A) Verificamos que se cobraron los recursos (Costo: 1 de cada uno)
        assertEquals(0, jugador.cantidadRecurso(new Grano(0)));
        assertEquals(0, jugador.cantidadRecurso(new Mineral(0)));
        assertEquals(0, jugador.cantidadRecurso(new Lana(0)));

        // B) Verificamos que el banco recibió los recursos
        assertTrue(banco.tieneStock(new Grano(0), 11)); // 10 iniciales + 1 pago
        assertTrue(banco.tieneStock(new Mineral(0), 1)); // 0 iniciales + 1 pago

        // C) Verificamos que la carta está realmente dentro del jugador
        // Usamos agarrarCarta(0) asumiendo que es la primera que tiene
        CartaDesarrollo cartaEnMano = jugador.agarrarCarta(0);

        assertNotNull(cartaEnMano);
        assertEquals(cartaNueva, cartaEnMano);
    }
}
