package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

import java.util.List;

public class CartaDescubrimiento extends CartaDesarrollo {

    public CartaDescubrimiento(int turno) {
        super(turno);
    }

    public void usarCarta(TipoDeRecurso primerRecurso, TipoDeRecurso segundoRecurso, Jugador jugador, ServicioComercio servicioComercio) {
        List<TipoDeRecurso> costo = List.of(primerRecurso, segundoRecurso);
        servicioComercio.entregarBonifCartaDescubrimiento(jugador, costo);
    }

}
