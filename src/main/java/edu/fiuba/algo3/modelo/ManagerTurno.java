package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Cartas.*;
import edu.fiuba.algo3.modelo.Contruccion.*;
import edu.fiuba.algo3.modelo.Intercambios.Banco;
import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Intercambios.ServicioComercio;
import edu.fiuba.algo3.modelo.Recursos.*;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Lado;
import edu.fiuba.algo3.modelo.Tablero.Factory.ReglaConstruccionException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.constructoresDeCarreteras.EstrategiaPagoGratuito;

import java.util.*;
import java.util.stream.Collectors;


public class ManagerTurno {
    private final List<Jugador> jugadores;
    private int indiceJugadorActual = 0;
    private int numeroTurnoActual = 0;
    private final Tablero tablero;
    private final Random azar;
    private ServicioComercio servicioComercio;
    private GranCaballeria granCaballeria = new GranCaballeria();
    private GranRutaComercial granRutaComercial = new GranRutaComercial();
    private OrdenTurnosIniciales ordenInicial;

    private boolean esperandoPoblado = true;
    private Coordenada ultimaCoordenadaPoblado = null;


    public ManagerTurno(List<Jugador> jugadores, Tablero tablero, Random Random) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.azar = Random;
        Banco banco = new Banco();

        banco.recibir(new Madera(10));
        banco.recibir(new Ladrillo(10));
        banco.recibir(new Grano(10));
        banco.recibir(new Lana(10));
        banco.recibir(new Mineral(10));

        this.servicioComercio = new ServicioComercio(banco, azar);

        this.ordenInicial = new OrdenTurnosIniciales(jugadores.size());
    }

    public void comprarCarta() {
        Jugador jugador = getJugadorActual();
        CartaDesarrollo cartaComprada = servicioComercio.venderCartaDesarrollo(jugador, numeroTurnoActual);
        jugador.agregarCarta(cartaComprada);
        if(cartaComprada instanceof PuntoDeVictoria){
            jugador.sumarPuntoDeVictoriaOculto();
        }
    }

    public void construirCarretera(Coordenada coordenada) throws ConstruccionExistenteException, ReglaConstruccionException {

        // 1. El servicio valida recursos, cobra al jugador y guarda en el Banco
        Carretera carretera = servicioComercio.venderCarretera(getJugadorActual());

        try {
            Jugador jugadorActual = getJugadorActual();
            tablero.colocarEnLado(carretera, coordenada);
            List<Lado> ladosJugador = tablero.obtenerLadosDeJugador(jugadorActual.getColor());
            int longitud = granRutaComercial.calcular(ladosJugador);
            granRutaComercial.actualizarRutaDeJugador(jugadorActual, longitud);

        } catch (ConstruccionExistenteException| ReglaConstruccionException e){
            // 3. ¡Error! El lugar estaba ocupado o muy cerca. Devolvemos la plata.
            servicioComercio.reembolsarPoblado(getJugadorActual());
            throw e; // Avisamos a la vista

        }


    }

    public void usarUnaCarta(int indice) {
        CartaDesarrollo cartaSeleccionada = getJugadorActual().agarrarCarta(indice);

        if (!cartaSeleccionada.SePuedeUsar(numeroTurnoActual)) {
            throw new ReglaDeCompraYUsoException("La carta no puede ser usada el mismo turno en el que se compra.");
        }
        if(cartaSeleccionada instanceof CartaCaballero ){
            granCaballeria.registrarCaballeroJugado(getJugadorActual());
            //moverLadron(pedir terreno al jugador);


            int posicion = getJugadorActual().pedirPosicion();

            List<Color> coloresDeVictimas= tablero.moverLadron(getJugadorActual(), posicion);
            List<Jugador> victimas =
                    coloresDeVictimas.stream()
                            .map(this::getJugadorPorColor)
                            .collect(Collectors.toList());

            ((CartaCaballero) cartaSeleccionada).usarCarta(getJugadorActual(), victimas);
        }
        if (cartaSeleccionada instanceof CartaDescubrimiento) {
            List<TipoDeRecurso> recursos = getJugadorActual().pedirRecursos();
            ((CartaDescubrimiento) cartaSeleccionada).usarCarta(getJugadorActual(), servicioComercio, recursos);
        }
        if(cartaSeleccionada instanceof CartaConstruccionCarreteras) {
            EstrategiaPagoGratuito modoFree = new EstrategiaPagoGratuito();
            this.getJugadorActual().setEstrategiaDePago(modoFree);
        }
        if(cartaSeleccionada instanceof CartaMonopolio){
            ((CartaMonopolio) cartaSeleccionada).ejecutarMonopolio(this.getJugadorActual(), this.jugadores);
        }

        // Utilidad de las cartas
    }



    public Jugador getJugadorActual() {
        if (!ordenInicial.haTerminado()) {
            return jugadores.get(ordenInicial.indiceJugadorActual());
        }
        return jugadores.get(indiceJugadorActual);
    }

    public void siguienteTurno() {
        contarPuntos();
        if (!ordenInicial.haTerminado()) {
            ordenInicial.avanzar();


            return;
        }
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        numeroTurnoActual += 1;
    }
    public void contarPuntos(){
        Jugador jugador = getJugadorActual();
        PuntajeDeVictoria pv= tablero.calcularPuntosDeVictoriaPorConstruccion(jugador.getColor());
        jugador.actualizarPuntosDeVictoria(pv);
    }

    public void repartirDividendos(int sumaDeDados) {
        List<List<Dividendo>> listaDividendosPorTerreno = this.tablero.distribuirProduccion(sumaDeDados);
        listaDividendosPorTerreno.forEach(listaDividendos -> {

            listaDividendos.forEach(dividendo -> {
                if(dividendo==null) {return;}
                Jugador jugador = getJugadorPorColor(dividendo.getColor());
                List<TipoDeRecurso> recursosPorDividendo = dividendo.getRecursos();
                recursosPorDividendo.forEach(jugador::agregarRecurso);

            });
        });
    }
    public void construirPoblado(Coordenada coordenada) {
        try {
            // 1. El servicio valida recursos, cobra al jugador y guarda en el Banco
            Poblado poblado = servicioComercio.venderPoblado(getJugadorActual());

            try {

                Jugador jugadorActual = getJugadorActual();
                tablero.colocarEnVertice(poblado, coordenada);
                PoliticaDeIntercambio politica= tablero.verificarPuerto(coordenada);
                if(politica!=null) {
                    jugadorActual.agregarPolitica(politica);
                }
            } catch (ReglaDistanciaException | ConstruccionExistenteException e){
                // 3. ¡Error! El lugar estaba ocupado o muy cerca. Devolvemos la plata.
                servicioComercio.reembolsarPoblado(getJugadorActual());
                throw e; // Avisamos a la vista
            }

        } catch (ReglaDistanciaException | ConstruccionExistenteException e) {
            System.out.println("No alcanza la plata: " + e.getMessage());
        }
    }

    public void moverLadron(Integer posicion){
        Jugador jugadorActual = getJugadorActual();
        List<Color> coloresDeVictimas= tablero.moverLadron(jugadorActual, posicion);
        List<Jugador> victimas =
                coloresDeVictimas.stream()
                        .map(this::getJugadorPorColor)
                        .collect(Collectors.toList());

        if(!victimas.isEmpty()){
            Jugador victima = victimas.get(azar.nextInt(victimas.size()));
            //Selecciona una victima al azar por ahora, depues vemos como hacer para que el jugador elija desde la interfaz
            jugadorActual.robarRecurso(victima);
        }

    }

    private Jugador getJugadorPorColor(Color color) {
        return jugadores.stream()
                .filter(jugador -> jugador.getColor().equals(color) )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un jugador con el color especificado: " + color));
    }
    public void mejorarACiudad(Coordenada coordenada) {
        Jugador jugadorActual = getJugadorActual();

        Ciudad nuevaCiudad = servicioComercio.venderCiudad(jugadorActual);

        try {
            tablero.mejoraACiudadEn(coordenada,jugadorActual.getColor());
        } catch (IllegalStateException e) {
            // 3. ROLLBACK: Si falló (no era dueño, no había poblado, etc.), devolvemos la plata.
            servicioComercio.reembolsarCiudad(jugadorActual);
            throw e; // Avisar a la vista del error
        }

    }

    public void setServicioComercio(ServicioComercio servicioComercio) {
        this.servicioComercio = servicioComercio;
    }

    public void intercambiarConJugadores(Jugador jugador1, TipoDeRecurso recursoAentregar, int cantidadAentregar, TipoDeRecurso recursoArecibir, int cantidadArecibir, List<Jugador> jugadores){
        servicioComercio.intercambiarConJugadores(jugador1,
                recursoAentregar,
                cantidadAentregar,
                recursoArecibir,
                cantidadArecibir,
                jugadores);
    }

    public void colocacionInicial( Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException, ReglaConstruccionException {
        Jugador jugador = getJugadorActual();

        if (esperandoPoblado) {

            // 1) Colocar poblado
            Poblado poblado = new Poblado(jugador.getColor());
            Dividendo dividendo = colocarEn(poblado, coordenada);

            ultimaCoordenadaPoblado = coordenada;
            esperandoPoblado = false; // la próxima acción será colocar carretera

            // Recursos solo si es la segunda ronda (tu OrdenInicial puede contestar esto)
            if (ordenInicial.esSegundoPoblado()) {

                List<TipoDeRecurso> recursos = dividendo.getRecursos();
                for (TipoDeRecurso recurso : recursos) {
                    jugador.agregarRecurso(recurso);
                }
            }

        } else {

            // 2) Colocar carretera conectada al último poblado
            Carretera carretera = new Carretera(jugador.getColor());

            colocarEn(carretera, coordenada);

            // Validar que la carretera realmente esté adyacente al poblado recién colocado
            if (!tablero.ladoConectaConVertice(coordenada, ultimaCoordenadaPoblado)) {
                throw new ReglaConstruccionException("La carretera debe conectarse al poblado inicial.");
            }

            // Reiniciar estado
            esperandoPoblado = true;
            ultimaCoordenadaPoblado = null;

            // Avanzar turno en orden 12344321
            siguienteTurno();
        }

    }

    public Dividendo colocarEn(Construccion pieza, Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException, ReglaConstruccionException {

        if (pieza instanceof Productor) { // Poblado o Ciudad
            return tablero.colocarEnVertice(pieza, coordenada);
        } else { // Carretera
            return tablero.colocarEnLado(pieza, coordenada);
        }

    }

}
