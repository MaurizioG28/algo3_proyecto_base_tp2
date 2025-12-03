package edu.fiuba.algo3.UnitTest;

import edu.fiuba.algo3.modelo.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class SesionDeJuegoTEST {

//    @Test
//    public void iniciarPartida_creaJugadores_yDelegaaCatan() {
//        // --- Arrange ---
//        Catan catanMock = mock(Catan.class);
//        ManagerTurno managerMock = mock(ManagerTurno.class);
//
//        // cuando se llame iniciarPartida con cualquier lista → devolver managerMock
//        when(catanMock.iniciarPartida()).thenReturn(managerMock);
//
//
//        SesionDeJuego contexto =  SesionDeJuego.obtenerInstancia();
//        contexto.setCatan(catanMock);
//
//        // Datos simulados desde la vista
//        List<String> nombres = List.of("A", "B", "C", "D");
//        List<Color> colores = List.of(
//                new Color("Rojo"),
//                new Color("Azul"),
//                new Color("Verde"),
//                new Color("Amarillo")
//        );
//
//        // --- Act ---
//        contexto.iniciarPartida(nombres, colores);
//
//        // --- Assert ---
//
//        // Capturar los argumentos que se pasaron a agregarJugador
//        ArgumentCaptor<Jugador> jugadorCaptor = ArgumentCaptor.forClass(Jugador.class);
//
//        // Verificar que se llamó 4 veces
//        verify(catanMock, times(4)).agregarJugador(jugadorCaptor.capture());
//
//        // Obtener todos los jugadores capturados
//        List<Jugador> jugadoresAgregados = jugadorCaptor.getAllValues();
//
//        // Verificar que son 4 jugadores
//        assertEquals(4, jugadoresAgregados.size());
//
//        // Verificar los detalles de cada jugador
//        assertEquals("A", jugadoresAgregados.get(0).getNombre());
//        assertEquals("rojo", jugadoresAgregados.get(0).getColor().getColor());
//
//        assertEquals("B", jugadoresAgregados.get(1).getNombre());
//        assertEquals("azul", jugadoresAgregados.get(1).getColor().getColor());
//
//        assertEquals("C", jugadoresAgregados.get(2).getNombre());
//        assertEquals("verde", jugadoresAgregados.get(2).getColor().getColor());
//
//        assertEquals("D", jugadoresAgregados.get(3).getNombre());
//        assertEquals("amarillo", jugadoresAgregados.get(3).getColor().getColor());
//
//        // Verificar que se llamó a iniciarPartida
//        verify(catanMock).iniciarPartida();
//        assertEquals(managerMock, contexto.getManagerTurno());
//        assertTrue(contexto.partidaIniciada());
//    }

}
