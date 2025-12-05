package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoEstandar;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.IEstrategiaDePago;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Jugador {

    private MazoDeCartas cartas;
    private AlmacenDeRecursos almacenJugador;
    private final List<PoliticaDeIntercambio> politicas = new ArrayList<>();
    private Color color;
    private String nombre;
    private PuntajeDeVictoria puntos;
    private IEstrategiaDePago estrategiaDePago;
    private int cantidadCaballerosUsados = 0;

    public Jugador(String nombre, Color color){
        this.almacenJugador = new AlmacenDeRecursos(new Random());
        this.cartas = new MazoDeCartas();
        this.color= color;
        this.nombre = nombre;
        this.puntos = new PuntajeDeVictoria();
        this.estrategiaDePago = new EstrategiaPagoEstandar();

    }

    public boolean esDelColor(Color colorAComparar) {
        return this.color.equals(colorAComparar);
    }


    public int cantidadRecurso(TipoDeRecurso tipo) {
        return almacenJugador.cantidadDe(tipo);
    }


    public Color getColor(){
        return color;
    }
    public boolean tiene(Madera madera, Ladrillo ladrillos, Lana lana, Mineral mineral, Grano grano) {
        return (
                (madera.obtenerCantidad() >= cantidadRecurso(madera)) &
                        (ladrillos.obtenerCantidad() >= cantidadRecurso(ladrillos)) &
                        (lana.obtenerCantidad() >= cantidadRecurso(lana)) &
                        (mineral.obtenerCantidad() >= cantidadRecurso(mineral)) &
                        (grano.obtenerCantidad() >= cantidadRecurso(grano))
        );
    }

    public void agregarRecurso(TipoDeRecurso recurso) {
        almacenJugador.agregarRecurso(recurso);
    }
    public void agregarCarta(CartaDesarrollo cartaNueva) {
        cartas.agregarCarta(cartaNueva);
        if (cartaNueva instanceof CartaProductora) {
            puntos.agregarPuntosOcultos(((CartaProductora) cartaNueva).obtenerCantidadPV());
        }
    }

    public CartaDesarrollo agarrarCarta(int indice) {
        return cartas.agarrarCarta(indice);
    }

    public void quitarRecurso(TipoDeRecurso tipo, int cantidad) {
        if (!almacenJugador.quitar(tipo, cantidad)) {
            throw new IllegalStateException("El jugador no tiene suficientes " + tipo.nombre());
        }
    }

    public void quitarRecurso(TipoDeRecurso recursoEntregado) {
        if (!almacenJugador.quitar(recursoEntregado)) {
            throw new IllegalStateException("El jugador no tiene suficientes " + recursoEntregado.nombre());
        }
    }

    public List<TipoDeRecurso> pedirRecursos() {
        //Aca uno deberá elegir los recursos desde la interfaz

        List<TipoDeRecurso> recursos = List.of(new Madera(1), new Lana(1));
        return recursos;
    }

    public int pedirPosicion() {
        // Deberia elegir una posicion desde la interfaz para mover al ladron desde la interfaz
        return 1;
    }

    public int mejorTasaPara(TipoDeRecurso recursoEntregado) {
        return politicas.stream()
                .filter(p -> p.aplicaA(this, recursoEntregado))
                .mapToInt(PoliticaDeIntercambio::tasa)
                .min()
                .orElse(4); // base 4:1
    }
    public void agregarPolitica(PoliticaDeIntercambio politica) {
        politicas.add(politica);
    }

    public Map<TipoDeRecurso, Integer> descartarMitadDeRecursos() {
        return this.almacenJugador.descartarPorReglaDelSiete();
    }

    public int totalRecursos() {
        return this.almacenJugador.totalRecursos();
    }

    private TipoDeRecurso entregarRecursoAleatorio() {
        return this.almacenJugador.robarRecursoAleatorio();
    }

    public boolean robarRecurso(Jugador victima) {
        TipoDeRecurso recursoRobado = victima.entregarRecursoAleatorio();
        if(recursoRobado != null){
            this.almacenJugador.agregarRecurso(recursoRobado);
            return true;
        }
        return false;
    }

    public int totalPuntos() {
        // Falta agregar mas implementaciones

        return puntos.obtenerPuntos();
    }

    public void intercambiar(TipoDeRecurso recursoEntregar, Jugador jugador2, TipoDeRecurso recursoRecibir) throws RecursosIsuficientesException {
        if(!jugador2.cambiar(recursoRecibir, recursoEntregar)){
            throw new RecursosIsuficientesException("El segundo jugador no tiene suficientes recursos.");
        }
        if(!this.cambiar(recursoEntregar, recursoRecibir)){
            jugador2.cambiar(recursoEntregar, recursoRecibir);
            throw new RecursosIsuficientesException("El primer jugador no tiene suficientes recursos.");
        };
    }

    public boolean cambiar(TipoDeRecurso recursoEntregar, TipoDeRecurso recursoRecibir) {
        if(!this.almacenJugador.quitar(recursoEntregar)) {
            return false;
        }
        this.almacenJugador.agregarRecurso(recursoRecibir);
        return true;
    }
    public int cantidadMadera() {
        return this.cantidadRecurso(new Madera(0));
    }

    public int cantidadGrano() {
        return this.cantidadRecurso(new Grano(0));
    }

    public int cantidadLadrillo() {
        return this.cantidadRecurso(new Ladrillo(0));
    }

    public int cantidadLana() {
        return this.cantidadRecurso(new Lana(0));
    }

    public int cantidadMineral() {
        return this.cantidadRecurso(new Mineral(0));
    }

    public int cantidadCartasCaballero() {
        return cartas.contarPor(c -> c instanceof CartaCaballero);
    }

    public int cantidadCartasMonopolio() {
        return cartas.contarPor(c -> c instanceof CartaMonopolio);
    }

    public int cantidadCartasDescubrimiento() {
        return cartas.contarPor(c -> c instanceof CartaDescubrimiento);
    }

    public int cantidadCartasCarreteras() {
        return cartas.contarPor(c -> c instanceof CartaConstruccionCarreteras);
    }

    public int cantidadCartasPuntoVictoria() {
        return cartas.contarPor(c -> c instanceof PuntoDeVictoria);
    }



    public void actualizarPuntosDeVictoria(PuntajeDeVictoria pv) {
        if(pv.getPuntosPublicos()< this.puntos.getPuntosPublicos()){
            return;
        }
        this.puntos.setPuntosPublicos(pv.getPuntosPublicos());

    }

    public void sumarPuntoDeVictoriaOculto() {
        this.puntos.agregarPuntosOcultos(1);
    }

    public void suscribirACatan(Catan catan) {
         this.puntos.addListener(catan);
    }

    public void sumarPuntoDeVictoriaPublico(int i) {
        this.puntos.agregarPuntos(i);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean mismoPuntaje(PuntajeDeVictoria puntaje) {
        return this.puntos.equals(puntaje);
    }

    public void restarPuntoDeVictoriaPublico(int i) {
        this.puntos.restarPuntosPublicos(i);
    }

    public void pagar(List<TipoDeRecurso> costo) {
        this.estrategiaDePago = estrategiaDePago.pagar(this.almacenJugador, costo);

    }
    public void setEstrategiaDePago(IEstrategiaDePago estrategiaDePago) {
        this.estrategiaDePago = estrategiaDePago;
    }
    public int entregarTodo(TipoDeRecurso recursoEntregar) {
        return this.almacenJugador.entregarTodo(recursoEntregar);
    }
    public void recibirBotin(TipoDeRecurso tipo, int cantidad) {
        if (cantidad > 0) {
            this.almacenJugador.agregarRecurso(tipo.nuevo(cantidad));
        }
    }

    public boolean suficienteCantidad(TipoDeRecurso recursoEntregado) {
        if (cantidadRecurso(recursoEntregado) >= recursoEntregado.obtenerCantidad()) {
            return true;
        }
        return false;
    }

    public void terminarTurno() {
        this.cartas.actualizarEstadoDeCartas();
    }

    public void sumarCaballero() {
        this.cantidadCaballerosUsados++;
    }
    public int getCantidadCaballerosUsados() {
        return this.cantidadCaballerosUsados;
    }
    public int contarRecursos() {
        return this.almacenJugador.totalRecursos();
    }

    public void construirCarretera(Tablero tablero, Coordenada coordenada) throws ConstruccionExistenteException, ReglaConstruccionException {
        List<TipoDeRecurso> costo = List.of(new Madera(1), new Ladrillo(1));

        this.pagar(costo);

        tablero.colocarEnLado(new Carretera(this.color), coordenada);
    }

    public boolean necesitoPagar(FichaComprable comprable) {
        return estrategiaDePago.seDebePagar(comprable);
    }

    // Usado por el Controlador
    public CartaDesarrollo buscarCartaParaJugar(String nombreCarta) {
        return cartas.buscarCartaJugable(nombreCarta);
    }

    // Usado por la Vista
    public boolean tieneCartaHabilitada(String nombreCarta) {
        return cartas.existeCartaJugable(nombreCarta);
    }
}
