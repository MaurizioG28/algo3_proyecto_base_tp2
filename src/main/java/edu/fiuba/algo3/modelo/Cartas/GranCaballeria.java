package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;

import java.util.HashMap;
import java.util.Map;

public class GranCaballeria {

    private final Map<Jugador, Integer> caballerosJugados = new HashMap<>();
    private Jugador lider = null;

    public void registrarCaballeroJugado(Jugador jugador) {
        int nuevos = caballerosJugados.merge(jugador, 1, Integer::sum);
        evaluar(jugador, nuevos);
    }

    private void evaluar(Jugador jugador, int caballerosXJugador) {


        if (caballerosXJugador < 3) return;

        // si no hay líder → este jugador lo obtiene
        if (lider == null) {
            asignarA(jugador);
            return;
        }


        if (lider == jugador) return;

        // si supera al líder → transferencia
        int jugadosLider = caballerosJugados.get(lider);
        if (caballerosXJugador > jugadosLider) {
            transferir(aNuevoLider(jugador));
        }
    }

    private void asignarA(Jugador nuevoLider) {
        lider = nuevoLider;
        nuevoLider.sumarPuntoDeVictoriaPublico(2);
    }

    private Jugador aNuevoLider(Jugador nuevo) {
        return nuevo;
    }

    private void transferir(Jugador nuevoLider) {
        lider.restarPuntoDeVictoriaPublico(2);
        nuevoLider.sumarPuntoDeVictoriaPublico(2);
        lider = nuevoLider;
    }
}
