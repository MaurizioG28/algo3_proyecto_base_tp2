package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServicioComercio {

    private final Banco banco;
    private final Random azar;

    public ServicioComercio(Banco banco) {
        this.banco = banco;
        this.azar = new Random();
    }
    public ServicioComercio(Banco banco, Random azar) {
        this.banco = banco;
        this.azar = azar;
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

        if (jugador.cantidadRecurso(recursoEntregado) < cantidadEntregada) {
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

    public Poblado venderPoblado(Jugador jugador) throws RecursosInsuficientesException {
        //  Definir Costo (Madera, Ladrillo, Lana, Grano)
        List<TipoDeRecurso> costo = List.of(
                new Madera(1), new Ladrillo(1), new Lana(1), new Grano(1)
        );

        procesarPago(jugador, costo);

        return new Poblado(jugador.getColor()); // Asumiendo que Jugador tiene getColor()
    }

    public Ciudad venderCiudad(Jugador jugador) throws RecursosInsuficientesException {
        List<TipoDeRecurso> costo = List.of(
                new Grano(2), new Mineral(3)
        );
        procesarPago(jugador, costo);
        return new Ciudad(jugador.getColor());
    }

    public Carretera venderCarretera(Jugador comprador){

        List<TipoDeRecurso> costo = List.of(
                new Madera(1), new Ladrillo(1)
        );
        procesarPago(comprador, costo);

        return new Carretera(comprador.getColor());

    }

    public CartaDesarrollo venderCartaDesarrollo(Jugador comprador, int turno){

        List<TipoDeRecurso> costo = List.of(
                new Grano(1), new Mineral(1),new Lana(1)
        );
        procesarPago(comprador, costo);

        return sacarCarta(turno);

    }

    // Método auxiliar para no repetir lógica de cobro
    private void procesarPago(Jugador jugador, List<TipoDeRecurso> costo) throws RecursosInsuficientesException {
        for (TipoDeRecurso r : costo) {
            if (jugador.cantidadRecurso(r.nuevo(0)) < r.obtenerCantidad()) {
                throw new RecursosInsuficientesException("No tienes suficientes recursos: " + r.nombre());
            }
        }

        for (TipoDeRecurso recurso : costo) {
            int cantidad = recurso.obtenerCantidad();

            // Jugador paga
            jugador.quitarRecurso(recurso.nuevo(0), cantidad);

            // Banco recibe
            banco.recibir(recurso.nuevo(cantidad));
        }
    }

    public void reembolsarPoblado(Jugador jugador) {
        List<TipoDeRecurso> costo = List.of(
                new Madera(1), new Ladrillo(1), new Lana(1), new Grano(1)
        );
        reembolsar(jugador, costo);
    }
    public void reembolsarCiudad(Jugador jugador) {
        List<TipoDeRecurso> costo = List.of(new Grano(2), new Mineral(3));
        reembolsar(jugador, costo);
    }

    private void reembolsar(Jugador jugador, List<TipoDeRecurso> costo) {
        for (TipoDeRecurso r : costo) {
            // El banco devuelve (entregar)
            try {
                banco.entregar(r.nuevo(0), r.obtenerCantidad());
                // El jugador recupera
                jugador.agregarRecurso(r.nuevo(r.obtenerCantidad()));
            } catch (IllegalStateException e) {
                // Manejar caso borde donde el banco no tenga (raro en reembolso inmediato)
            }
        }
    }

    public void entregarBonifCartaDescubrimiento(Jugador jugador, List<TipoDeRecurso> recursosElegidos) {
        reembolsar(jugador, recursosElegidos);
    }

    public void intercambiarConJugadores(Jugador jugador1, TipoDeRecurso recursoAentregar, int cantidadAentregar, TipoDeRecurso recursoArecibir, int cantidadArecibir, List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            try {
                jugador1.intercambiar(recursoAentregar, cantidadAentregar, jugador, recursoArecibir, cantidadArecibir);
                break;
            } catch (RecursosIsuficientesException ignored) {
            }

        }
    }


    private CartaDesarrollo sacarCarta(int turno){
        List<CartaDesarrollo> cartasDisponibles = new ArrayList<>();
        cartasDisponibles.add(new CartaCaballero(turno));
        cartasDisponibles.add(new CartaConstruccionCarreteras(turno));
        cartasDisponibles.add(new CartaDescubrimiento(turno));
        cartasDisponibles.add(new CartaMonopolio(turno));
        cartasDisponibles.add(new PuntoDeVictoria(turno));

        int NumeroAleatorio = azar.nextInt(cartasDisponibles.size());

        return cartasDisponibles.get(NumeroAleatorio);
    }





}

