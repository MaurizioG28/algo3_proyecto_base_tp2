package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.List;

public class CartaDescubrimiento extends CartaDesarrollo {

    public CartaDescubrimiento() {
    }

    public void usarCarta(Jugador jugador, ServicioComercio servicioComercio, List<TipoDeRecurso> recursosElegidos) {
        servicioComercio.entregarBonifCartaDescubrimiento(jugador, recursosElegidos);
    }
}
