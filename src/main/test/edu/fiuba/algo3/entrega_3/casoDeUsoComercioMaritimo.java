package edu.fiuba.algo3.entrega_3;
import edu.fiuba.algo3.modelo.Intercambios.*;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class casoDeUsoComercioMaritimo {
    private final TipoDeRecurso madera = new Madera(0);
    private final TipoDeRecurso grano  = new Grano(0);

    private Banco bancoConStockBasico() {
        Banco banco = new Banco();
        // Cargamos stock usando recursos con cantidad
        banco.recibir(new Madera(20));
        banco.recibir(new Grano(20));
        return banco;
    }

    // 1) Sin puertos → 4:1
    @Test
    public void test01JugadorSinPuertosIntercambia4a1ConBanco() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        // El jugador recibe 4 maderas
        jugador.agregarRecurso(new Madera(4));
        servicio.intercambiarConBanco(jugador, madera, 4, grano);
        assertEquals(0, jugador.CantidadRecurso(madera));
        assertEquals(1, jugador.CantidadRecurso(grano));
    }

    // 2) Con puerto genérico  3:1 cualquier recurso
    @Test
    public void test02JugadorConPuertoGenericoIntercambia3a1() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(3));
        jugador.agregarPolitica(new PuertoGenerico(3)); // 3:1 cualquiera

        servicio.intercambiarConBanco(jugador, madera, 3, grano);

        assertEquals(0, jugador.CantidadRecurso(madera),
                "Debe haber entregado las 3 maderas");
        assertEquals(1, jugador.CantidadRecurso(grano),
                "Debe haber recibido 1 grano (3:1 por puerto genérico)");
    }

    // 3) Puerto específico 2:1 para un recurso concreto
    @Test
    public void test03JugadorConPuertoEspecificoIntercambia2a1SoloEseRecurso() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarPolitica(new PuertoEspecifico(madera, 2)); // 2:1 madera
        jugador.agregarRecurso(new Madera(2));

        servicio.intercambiarConBanco(jugador, madera, 2, grano);

        assertEquals(0, jugador.CantidadRecurso(madera),
                "Debe haber entregado las 2 maderas");
        assertEquals(1, jugador.CantidadRecurso(grano),
                "Debe haber recibido 1 grano (2:1 por puerto específico)");
    }

    // 4) Puerto específico NO aplica a otros recursos vuelve a 4:1
    @Test
    public void test04PuertoEspecificoNoMejoraOtrosRecursos() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarPolitica(new PuertoEspecifico(madera, 2)); // solo madera
        jugador.agregarRecurso(new Grano(4));

        // Como el puerto es de MADERA, para GRANO debe aplicar la tasa base 4:1
        servicio.intercambiarConBanco(jugador, grano, 4, madera);

        assertEquals(0, jugador.CantidadRecurso(grano),
                "Debe haber entregado los 4 granos");
        assertEquals(1, jugador.CantidadRecurso(madera),
                "Debe haber recibido 1 madera (4:1, no 2:1)");
    }

    // 5) Combinar puerto genérico y específico: se usa la mejor tasa (mínimo)
    @Test
    public void test05JugadorConPuertoGenericoYEspecificoUsaLaMejorTasa() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarPolitica(new PuertoGenerico(3));             // 3:1 cualquiera
        jugador.agregarPolitica(new PuertoEspecifico(madera, 2));   // 2:1 madera
        jugador.agregarRecurso(new Madera(2));

        servicio.intercambiarConBanco(jugador, madera, 2, grano);

        assertEquals(0, jugador.CantidadRecurso(madera),
                "Debe haber entregado las 2 maderas");
        assertEquals(1, jugador.CantidadRecurso(grano),
                "Debe haber recibido 1 grano (usa mejor tasa 2:1, no 3:1)");
    }

    // 6) No permite intercambio con cantidad que no es múltiplo de la tasa
    @Test
    public void test06FallaSiCantidadNoEsMultiploDeLaTasa() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarPolitica(new PuertoGenerico(3)); // 3:1
        jugador.agregarRecurso(new Madera(4));

        assertThrows(IntercambioInvalidoException.class,
                () -> servicio.intercambiarConBanco(jugador, madera, 4, grano),
                "No debe permitir intercambiar 4 cuando la tasa es 3:1");
    }

    // 7) No permite intercambio si el jugador no tiene suficientes recursos
    @Test
    public void test07FallaSiJugadorNoTieneSuficienteRecurso() {
        Banco banco = bancoConStockBasico();
        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(3)); // menos de 4

        assertThrows(IntercambioInvalidoException.class,
                () -> servicio.intercambiarConBanco(jugador, madera, 4, grano),
                "No debe permitir intercambiar más recursos de los que tiene");
    }

    // 8) No permite intercambio si el banco no tiene stock suficiente del recurso pedido
    @Test
    public void test08FallaSiBancoNoTieneStockSuficiente() {
        Banco banco = new Banco();
        // Sólo cargamos stock de MADERA, nada de GRANO
        banco.recibir(new Madera(20));

        ServicioComercio servicio = new ServicioComercio(banco);
        Jugador jugador = new Jugador();

        jugador.agregarRecurso(new Madera(4));

        assertThrows(IntercambioInvalidoException.class,
                () -> servicio.intercambiarConBanco(jugador, madera, 4, grano),
                "No debe permitir intercambiar si el banco no tiene stock del recurso pedido");
    }
}

