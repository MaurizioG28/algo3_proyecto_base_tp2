package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Ciudad;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.MazoDeCartas;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.RecursosInsuficientesException;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ServicioComercio {
    private  List<CartaDesarrollo> cartasDisponibles;
    private final Banco banco;
    private final Random azar;
    private static final int CANTIDAD_CABALLEROS = 14;
    private static final int CANTIDAD_PUNTOS_VICTORIA = 5;
    private static final int CANTIDAD_MONOPOLIO = 2;
    private static final int CANTIDAD_DESCUBRIMIENTO = 2;
    private static final int CANTIDAD_CONSTRUCCION_CARRETERAS = 2;
    public ServicioComercio(Banco banco) {
        this.banco = banco;
        this.azar = new Random();
        llenarMazo();
    }
    public ServicioComercio(Banco banco, Random azar) {
        this.banco = banco;
        this.azar = azar;
        llenarMazo();
    }
    public ServicioComercio(Banco banco, Random azar,List<CartaDesarrollo> cartasDisponibles) {
        this.banco = banco;
        this.azar = azar;
        this.cartasDisponibles = cartasDisponibles;
    }

    private void llenarMazo(){
        List<CartaDesarrollo> cartas = new ArrayList<>();
        agregarCartas(cartas, () -> new CartaCaballero(), CANTIDAD_CABALLEROS);
        agregarCartas(cartas, () -> new PuntoDeVictoria(), CANTIDAD_PUNTOS_VICTORIA);
        agregarCartas(cartas, () -> new CartaMonopolio(), CANTIDAD_MONOPOLIO);
        agregarCartas(cartas, () -> new CartaDescubrimiento(), CANTIDAD_DESCUBRIMIENTO);
        agregarCartas(cartas, () -> new CartaConstruccionCarreteras(), CANTIDAD_CONSTRUCCION_CARRETERAS);
        Collections.shuffle(cartas, azar);

        List<CartaDesarrollo> mazoFinal = new ArrayList<CartaDesarrollo>();

        for (CartaDesarrollo carta : cartas) {
            mazoFinal.add(carta);
        }
        this.cartasDisponibles = mazoFinal;
    }



    private void agregarCartas(List<CartaDesarrollo> lista, Supplier<CartaDesarrollo> fabrica, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            lista.add(fabrica.get());
        }
    }
    public void intercambiarConBanco(Jugador jugador,
                                     TipoDeRecurso recursoEntregado,
                                     TipoDeRecurso recursoRecibido) {
        int tasa = jugador.mejorTasaPara(recursoEntregado);

        if (recursoEntregado.obtenerCantidad() < tasa || recursoEntregado.obtenerCantidad() % tasa != 0) {
            throw new IntercambioInvalidoException(
                    "Cantidad " + recursoEntregado.obtenerCantidad() + " no compatible con tasa " + tasa + ":1");
        }

        int cantidadRecibida = recursoEntregado.obtenerCantidad() / tasa;

        if (!jugador.suficienteCantidad(recursoEntregado)) {
            throw new IntercambioInvalidoException(
                    "El jugador no tiene suficientes " + recursoEntregado);
        }

        if (!banco.tieneStock(recursoRecibido, cantidadRecibida)) {
            throw new IntercambioInvalidoException(
                    "El banco no tiene suficiente " + recursoRecibido);
        }

        // Ejecutar intercambio
        jugador.quitarRecurso(recursoEntregado);
        jugador.agregarRecurso(recursoRecibido.nuevo(cantidadRecibida));

        banco.recibir(recursoEntregado.nuevo(recursoEntregado.obtenerCantidad()));
        banco.entregar(recursoRecibido, cantidadRecibida);
    }

    public FichaComprable comprarObjeto(Jugador jugador, FichaComprable comprable) throws RecursosInsuficientesException {
        List<TipoDeRecurso> costo = comprable.costoRecursos();

        if (jugador.necesitoPagar(comprable)) {
            procesarPago(jugador, costo);
        }

        return comprable;
    }

//    public Poblado venderPoblado(Jugador jugador) throws RecursosInsuficientesException {
//        //  Definir Costo (Madera, Ladrillo, Lana, Grano)
//        List<TipoDeRecurso> costo = List.of(
//                new Madera(1), new Ladrillo(1), new Lana(1), new Grano(1)
//        );
//
//        procesarPago(jugador, costo);
//
//        return new Poblado(jugador.getColor()); // Asumiendo que Jugador tiene getColor()
//    }

//    public Ciudad venderCiudad(Jugador jugador) throws RecursosInsuficientesException {
//        List<TipoDeRecurso> costo = List.of(
//                new Grano(2), new Mineral(3)
//        );
//        procesarPago(jugador, costo);
//        return new Ciudad(jugador.getColor());
//    }

//    public Carretera venderCarretera(Jugador comprador){
//
//        List<TipoDeRecurso> costo = List.of(
//                new Madera(1), new Ladrillo(1)
//        );
//        procesarPago(comprador, costo);
//
//        return new Carretera(comprador.getColor());
//
//    }

    public CartaDesarrollo venderCartaDesarrollo(Jugador comprador, int turno) {

        List<TipoDeRecurso> costo = CartaDesarrollo.costoRecursos();
        procesarPago(comprador, costo);

        return sacarCarta(turno);

    }

    // Método auxiliar para no repetir lógica de cobro
    private void procesarPago(Jugador jugador, List<TipoDeRecurso> costo) throws RecursosInsuficientesException {

        //Posible violacion de Tell Dont Ask
        //Jugador tienes esto ? -> "NO/Si" -> entonces hago esto

        //Ahora deberia estar resuelta en teoria
        for (TipoDeRecurso r : costo) {
            if (!jugador.suficienteCantidad(r)) {
                throw new RecursosInsuficientesException("No tienes suficientes recursos: " + r.nombre());
            }
        }

        jugador.pagar(costo);

        for (TipoDeRecurso recurso : costo) {
            int cantidad = recurso.obtenerCantidad();

            // Banco recibe
            banco.recibir(recurso.nuevo(cantidad));
        }
    }

    public void reembolsarPoblado(Jugador jugador) {
        List<TipoDeRecurso> costo = new Poblado(new Color("Indefinido")).costoRecursos();
        reembolsar(jugador, costo);
    }
    public void reembolsarCiudad(Jugador jugador) {
        List<TipoDeRecurso> costo = new Ciudad(new Color("Indefinido")).costoRecursos();
        reembolsar(jugador, costo);
    }
    public void reembolsarCamino(Jugador jugador) {
        List<TipoDeRecurso> costo = List.of(
                new Madera(1), new Ladrillo(1)
        );
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

    //Esto fue cambiado para que la cantidad este directamente en el recurso pasado como parametro en vez de ser un int separado.
    public void intercambiarConJugadores(Jugador jugador1, TipoDeRecurso recursoAentregar, TipoDeRecurso recursoArecibir, List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            try {
                jugador1.intercambiar(recursoAentregar, jugador, recursoArecibir);
                break;
            } catch (RecursosIsuficientesException ignored) {
            }

        }
    }


    private CartaDesarrollo sacarCarta(int turno){
        int NumeroAleatorio = azar.nextInt(this.cartasDisponibles.size());
        return cartasDisponibles.get(NumeroAleatorio);
    }





}

