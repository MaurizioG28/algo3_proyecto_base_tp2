package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.CasosDeUso.IntercambioEntreJugadores;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Madera;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoIntercambiarRecursos {
    private final TipoDeRecurso madera = new Madera(0);
    private final TipoDeRecurso grano  = new Grano(0);

    @Test
    public void test01IntercambiarConRecursosDisponiblesAmbosJugadoresCambianSusMateriales() {
        Jugador jugador1 = new Jugador("Jugador1", new Color("Azul"));
        Jugador jugador2 = new Jugador("Jugador2", new Color("Rojo"));

        jugador1.agregarRecurso(new Madera(10));
        jugador2.agregarRecurso(new Grano(1));

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        try {
            caso.intercambiar(jugador1, new Madera(0), 6, jugador2, new Grano(0), 1);
        } catch (RecursosIsuficientesException ignored) {
        }

        int maderaJugador1 = 4;
        int granoJugador1 = 1;

        int maderaJugador2 = 6;
        int granoJugador2 = 0;

        assertEquals(maderaJugador1, jugador1.cantidadRecurso(madera));
        assertEquals(granoJugador1, jugador1.cantidadRecurso(grano));
        assertEquals(maderaJugador2, jugador2.cantidadRecurso(madera));
        assertEquals(granoJugador2, jugador2.cantidadRecurso(grano));

    }

    @Test
    public void test02Jugador1ConMaterialesIsuficientesNoIntercambia() {
        Jugador jugador1 = new Jugador("Jugador1", new Color("Azul"));
        Jugador jugador2 = new Jugador("Jugador2", new Color("Rojo"));

        jugador2.agregarRecurso(new Grano(1));

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        try {
            caso.intercambiar(jugador1, new Madera(0), 6, jugador2, new Grano(0), 1);
        } catch (RecursosIsuficientesException ignored) {
        }

        int maderaJugador1 = 0;
        int granoJugador1 = 0;

        int maderaJugador2 = 0;
        int granoJugador2 = 1;

        assertEquals(maderaJugador1, jugador1.cantidadRecurso(madera));
        assertEquals(granoJugador1, jugador1.cantidadRecurso(grano));
        assertEquals(maderaJugador2, jugador2.cantidadRecurso(madera));
        assertEquals(granoJugador2, jugador2.cantidadRecurso(grano));

    }

    @Test
    public void test03Jugador2ConMaterialesIsuficientesNoIntercambia() {
        Jugador jugador1 = new Jugador("Jugador1", new Color("Azul"));
        Jugador jugador2 = new Jugador("Jugador2", new Color("Rojo"));

        jugador1.agregarRecurso(new Madera(10));

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        try {
            caso.intercambiar(jugador1, new Madera(0), 6, jugador2, new Grano(0), 1);
        } catch (RecursosIsuficientesException ignored) {
        }

        int maderaJugador1 = 10;
        int granoJugador1 = 0;

        int maderaJugador2 = 0;
        int granoJugador2 = 0;

        assertEquals(maderaJugador1, jugador1.cantidadRecurso(madera));
        assertEquals(granoJugador1, jugador1.cantidadRecurso(grano));
        assertEquals(maderaJugador2, jugador2.cantidadRecurso(madera));
        assertEquals(granoJugador2, jugador2.cantidadRecurso(grano));

    }
}
