package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Jugador;

import java.util.HashMap;
import java.util.Map;

public class GranCaballeria {

    private Jugador lider = null;
    private static final int MINIMO_NECESARIO = 3;

    public void registrarCaballeroJugado(Jugador aspirante) {
        int cantidadDelAspirante = aspirante.getCantidadCaballerosUsados();

        evaluarLiderazgo(aspirante, cantidadDelAspirante);
    }

    private void evaluarLiderazgo(Jugador aspirante, int cantidadAspirante) {

        if (cantidadAspirante < MINIMO_NECESARIO) return;

        if (lider == null) {
            asignarNuevoLider(aspirante);
            return;
        }

        if (lider.equals(aspirante)) return;

        int cantidadDelLider = lider.getCantidadCaballerosUsados();

        if (cantidadAspirante > cantidadDelLider) {
            transferirLiderazgo(aspirante);
        }
    }

    private void asignarNuevoLider(Jugador nuevoLider) {
        this.lider = nuevoLider;
        nuevoLider.sumarPuntoDeVictoriaPublico(2);
    }

    private void transferirLiderazgo(Jugador nuevoLider) {
        this.lider.restarPuntoDeVictoriaPublico(2); // Quitamos al viejo
        this.asignarNuevoLider(nuevoLider);         // Damos al nuevo
    }

    public Jugador getLider() {
        return lider;
    }
}
