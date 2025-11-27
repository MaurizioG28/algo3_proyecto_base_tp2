package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Factory.Lado;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GranRutaComercial {
    private Jugador liderActual = null;
    private int longitudMaxima = 0;

    public void actualizarRutaDeJugador(Jugador jugador , int nuevaLongitud) {
        // Si no llega al mínimo, ignorar
        if (nuevaLongitud < 5) return;

        // Si nadie era líder → este lo es
        if (liderActual == null) {
            asignarLider(jugador, nuevaLongitud);
            return;
        }

        // Si empata la longitud, no cambia
        if (nuevaLongitud == longitudMaxima) {
            return;
        }

        // Si supera, entonces reemplaza
        if (nuevaLongitud > longitudMaxima) {
            removerBonus(liderActual);
            asignarLider(jugador, nuevaLongitud);
        }

    }

    private void asignarLider(Jugador nuevoLider, int longitud) {
        liderActual = nuevoLider;
        longitudMaxima = longitud;
        nuevoLider.sumarPuntoDeVictoriaPublico(2);
    }

    private void removerBonus(Jugador viejoLider) {
        viejoLider.restarPuntoDeVictoriaPublico(2);
    }


    private int dfsRuta(Lado actual, Color color, Set<Lado> visitados) {
        visitados.add(actual);

        int max = 1;

        for (Lado vecino : actual.getAdyacentes()) {

            if (!color.equals(vecino.getPropietario()))
                continue;

            if (visitados.contains(vecino))
                continue;

            if (hayBloqueoEntre(actual, vecino, color))
                continue;

            max = Math.max(max, 1 + dfsRuta(vecino, color, visitados));
        }

        visitados.remove(actual);
        return max;
    }


    private boolean hayBloqueoEntre(Lado a, Lado b, Color colorJugador) {
        for (Vertice v : a.getPuntas()) {

            if (b.getPuntas().contains(v)) {
                if (v.tieneConstruccion() &&
                        !v.colorDeConstruccionEquals(colorJugador)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int calcular(List<Lado> ladosJugador) {
        int max = 0;

        for (Lado lado : ladosJugador) {
            Set<Lado> visitados = new HashSet<>();
            max = Math.max(max, dfsRuta(lado, lado.getPropietario(), visitados));
        }

        return max;
    }
}
