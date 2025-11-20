package edu.fiuba.algo3.entrega_3;

import edu.fiuba.algo3.modelo.CasosDeUso.IntercambioEntreJugadores;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Recursos.RecursosIsuficientesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CasoDeUsoIntercambiarRecursos {

    @Test
    public void test01IntercambiarConRecursosDisponiblesJugador2RecibeLosRecursos(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador1.agregarRecurso(Recurso.MADERA, 10);
        jugador2.agregarRecurso(Recurso.GRANO, 1);

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        int cantidadEsperada = 6;
        try {
            caso.intercambiar(jugador1, Recurso.MADERA, 6, jugador2, Recurso.GRANO, 1);
        } catch (RecursosIsuficientesException e) {
            throw new RuntimeException(e);
        }

        assertEquals(cantidadEsperada, jugador2.totalRecursos());
    }

    @Test
    public void test02IntercambiarConRecursosDisponiblesJugador1EntregaLosRecursos(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador1.agregarRecurso(Recurso.MADERA, 10);
        jugador2.agregarRecurso(Recurso.GRANO, 1);

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        int cantidadEsperada = 5;
        try {
            caso.intercambiar(jugador1, Recurso.MADERA, 6, jugador2, Recurso.GRANO, 1);
        } catch (RecursosIsuficientesException e) {
            throw new RuntimeException(e);
        }

        assertEquals(cantidadEsperada, jugador1.totalRecursos());
    }

    @Test
    public void test03IntercambiarSinRecursosEnJugador1LanzaError(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador2.agregarRecurso(Recurso.GRANO, 1);

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();

        assertThrows(RecursosIsuficientesException.class, () -> caso.intercambiar(jugador1, Recurso.MADERA, 1, jugador2, Recurso.GRANO, 1));
    }

    @Test
    public void test03IntercambiarSinRecursosEnJugador2LanzaError(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador1.agregarRecurso(Recurso.MADERA, 10);

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();

        assertThrows(RecursosIsuficientesException.class, () -> caso.intercambiar(jugador1, Recurso.MADERA, 1, jugador2, Recurso.GRANO, 1));
    }

    @Test
    public void test04IntercambiarSinRecursosEnJugador1yJugador2MantieneSusRecursos(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador2.agregarRecurso(Recurso.GRANO, 1);
        int cantidadEsperada = 1;

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        try {
            caso.intercambiar(jugador1, Recurso.MADERA, 1, jugador2, Recurso.GRANO, 1);
        } catch (RecursosIsuficientesException ignored) {

        }

        assertEquals(cantidadEsperada,jugador2.totalRecursos());
    }

    @Test
    public void test05IntercambiarSinRecursosEnJugador2yJugador1MantieneSusRecursos(){
        Jugador jugador1 = new Jugador(new Color("Azul"));
        Jugador jugador2 = new Jugador(new Color("Rojo"));

        jugador1.agregarRecurso(Recurso.MADERA, 10);
        int cantidadEsperada = 10;

        IntercambioEntreJugadores caso = new IntercambioEntreJugadores();
        try {
            caso.intercambiar(jugador1, Recurso.MADERA, 1, jugador2, Recurso.GRANO, 1);
        } catch (RecursosIsuficientesException ignored) {

        }

        assertEquals(cantidadEsperada,jugador1.totalRecursos());
    }
}
