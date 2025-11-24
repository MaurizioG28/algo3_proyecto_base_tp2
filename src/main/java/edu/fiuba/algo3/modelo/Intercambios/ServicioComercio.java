package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class ServicioComercio {

    private final Banco banco;

    public ServicioComercio(Banco banco) {
        this.banco = banco;
    }

    public void intercambiarConBanco(Jugador jugador,
                                     TipoDeRecurso recursoEntregado,
                                     int cantidadEntregada,
                                     TipoDeRecurso recursoRecibido) {

        int tasa = jugador.mejorTasaPara(recursoEntregado);

        if (cantidadEntregada < tasa || cantidadEntregada % tasa != 0) {
            throw new IntercambioInvalidoException(
                    "Cantidad " + cantidadEntregada + " no compatible con tasa " + tasa + ":1");
        }

        int cantidadRecibida = cantidadEntregada / tasa;

        if (jugador.CantidadRecurso(recursoEntregado) < cantidadEntregada) {
            throw new IntercambioInvalidoException(
                    "El jugador no tiene suficientes " + recursoEntregado);
        }

        if (!banco.tieneStock(recursoRecibido, cantidadRecibida)) {
            throw new IntercambioInvalidoException(
                    "El banco no tiene suficiente " + recursoRecibido);
        }

        // Ejecutar intercambio
        jugador.quitarRecurso(recursoEntregado, cantidadEntregada);
        jugador.agregarRecurso(recursoRecibido.nuevo(cantidadRecibida));

        banco.recibir(recursoEntregado.nuevo(cantidadEntregada));
        banco.entregar(recursoRecibido, cantidadRecibida);
    }


}

