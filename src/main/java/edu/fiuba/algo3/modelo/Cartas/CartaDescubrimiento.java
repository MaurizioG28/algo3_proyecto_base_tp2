package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;

public class CartaDescubrimiento extends CartaDesarrollo {
    private List<TipoDeRecurso> recursosDeseados;
    public CartaDescubrimiento() {
    }

    @Override
    public void ejecutarEfecto(Jugador jugadorActivo, Tablero tablero, List<Jugador> oponentes) {
        this.usar();

        if (this.recursosDeseados == null) {
            throw new IllegalStateException("Debes seleccionar qué recursos tomar antes de jugar la carta.");
        }

        for (TipoDeRecurso recurso : this.recursosDeseados) {
            jugadorActivo.agregarRecurso(recurso);
        }
    }

    public void usarCarta(Jugador jugador, ServicioComercio servicioComercio, List<TipoDeRecurso> recursosElegidos) {
        servicioComercio.entregarBonifCartaDescubrimiento(jugador, recursosElegidos);
    }
    public void setRecursosDeseados(List<TipoDeRecurso> recursos) {
        if (recursos == null || recursos.size() != 2) {
            throw new IllegalArgumentException("La carta Descubrimiento otorga exactamente 2 recursos.");
        }
        this.recursosDeseados = recursos;
    }

}
