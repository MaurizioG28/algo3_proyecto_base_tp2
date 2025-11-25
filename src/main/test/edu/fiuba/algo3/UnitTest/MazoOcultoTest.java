package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.Cartas.CartaDesarrollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.MazoOculto;
//import edu.fiuba.algo3.modelo.Mocks.FakeJugador;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.ReglaDeCompraYUsoException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MazoOcultoTest {
    @Test
    public void Test01UnMazoDebeDevolverUnaCartaSiSeTienenLosRecursosNecesarios() {
//        Jugador jugador = new FakeJugador(true);
//        Random random = new Random();
//        MazoOculto mazo = new MazoOculto(random);
//
//        CartaDesarrollo carta = mazo.comprarCarta(jugador, 0);
//
//        assertSame(CartaDesarrollo.class, carta.getClass().getSuperclass());
    }

    @Test
    public void Test02UnMazoNoTeDejaComprarCartasSinLosRecursosNecesarios() {
//        Jugador jugador = new FakeJugador(false);
//        Random random = new Random();
//        MazoOculto mazo = new MazoOculto(random);
//
//        assertThrows(RecursosInsuficientesException.class,
//                () -> mazo.comprarCarta(jugador, 0));
    }
}
