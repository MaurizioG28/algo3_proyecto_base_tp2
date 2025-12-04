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
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.interfaces.FichaComprable;

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

        banco.recibir(new Madera(20));
        banco.recibir(new Ladrillo(20));
        banco.recibir(new Grano(20));
        banco.recibir(new Lana(20));
        banco.recibir(new Mineral(20));

        this.servicioComercio = new ServicioComercio(banco, azar);

        this.ordenInicial = new OrdenTurnosIniciales(jugadores.size());
    }
    public ManagerTurno(List<Jugador> jugadores, Tablero tablero, Random Random, ServicioComercio servicioComercio) {
        this.jugadores = jugadores;
        this.tablero = tablero;
        this.azar = Random;
        Banco banco = new Banco();

        banco.recibir(new Madera(10));
        banco.recibir(new Ladrillo(10));
        banco.recibir(new Grano(10));
        banco.recibir(new Lana(10));
        banco.recibir(new Mineral(10));

        this.servicioComercio = servicioComercio;
    }

    public void comprarCarta() {
        Jugador jugador = getJugadorActual();
        CartaDesarrollo cartaComprada = servicioComercio.venderCartaDesarrollo(jugador, numeroTurnoActual);
        cartaComprada.setTurnoDeCompra(this.numeroTurnoActual);
        jugador.agregarCarta(cartaComprada);
        if(cartaComprada instanceof PuntoDeVictoria){
            jugador.sumarPuntoDeVictoriaOculto();
        }
    }

    public void construirCarretera(Coordenada coordenada) throws ConstruccionExistenteException, ReglaConstruccionException {

        // 1. El servicio valida recursos, cobra al jugador y guarda en el Banco
        FichaComprable carretera = servicioComercio.comprarObjeto(getJugadorActual(), new Carretera(getJugadorActual().getColor()));

        try {
            Jugador jugadorActual = getJugadorActual();
            tablero.colocarEnLado((Construccion) carretera, coordenada);
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
        Jugador jugadorActual = getJugadorActual();
        CartaDesarrollo cartaSeleccionada = jugadorActual.agarrarCarta(indice);
        if (!cartaSeleccionada.sePuedeUsar(this.numeroTurnoActual)) {
            throw new RuntimeException("No puedes usar una carta el mismo turno que la compraste.");
        }
        try {
            cartaSeleccionada.ejecutarEfecto(jugadorActual, this.tablero, this.jugadores);


            cartaSeleccionada.marcarComoUsada();

            if(cartaSeleccionada instanceof CartaCaballero){
                this.granCaballeria.registrarCaballeroJugado(jugadorActual);
            }
        } catch (RuntimeException e){
            throw e;
        }
    }

    public void intercambiarConBanca(TipoDeRecurso recursoDoy, TipoDeRecurso recursoRecibo) {
        Jugador jugadorActual = getJugadorActual();


        int tasa = jugadorActual.mejorTasaPara(recursoDoy);

        TipoDeRecurso aEntregar = recursoDoy.nuevo(tasa);

        TipoDeRecurso aRecibir = recursoRecibo.nuevo(1);

        servicioComercio.intercambiarConBanco(jugadorActual, aEntregar, aRecibir);
    }


    public Jugador getJugadorActual() {

        return jugadores.get(indiceJugadorActual);
    }

    public Jugador getJugadorActualInicial(){
        if (!ordenInicial.haTerminado()) {
            return jugadores.get(ordenInicial.indiceJugadorActual());
        }
        return jugadores.get(indiceJugadorActual);
    }

    public void siguienteTurno() {
        getJugadorActual().terminarTurno();
        contarPuntos();
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        numeroTurnoActual += 1;
    }
    public void siguienteTurnoInicial(){
        contarPuntos();
        if (!ordenInicial.haTerminado()) {
            ordenInicial.avanzar();

        }
        else {
            indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
            numeroTurnoActual += 1;}

    }
    public void contarPuntos(){
//        Jugador jugador = getJugadorActual();
//        PuntajeDeVictoria pv= tablero.calcularPuntosDeVictoriaPorConstruccion(jugador.getColor());
//        jugador.actualizarPuntosDeVictoria(pv);
        Jugador jugadorAPuntuar = ordenInicial.haTerminado() ? getJugadorActual() : getJugadorActualInicial();

        PuntajeDeVictoria pv = tablero.calcularPuntosDeVictoriaPorConstruccion(jugadorAPuntuar.getColor());
        jugadorAPuntuar.actualizarPuntosDeVictoria(pv);
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
            //El servicio valida recursos, cobra al jugador y guarda en el Banco
            FichaComprable poblado = servicioComercio.comprarObjeto(getJugadorActual(), new Poblado(getJugadorActual().getColor()));

            try {

                Jugador jugadorActual = getJugadorActual();
                tablero.colocarEnVertice((Construccion) poblado, coordenada);
                PoliticaDeIntercambio politica= tablero.verificarPuerto(coordenada);
                if(politica!=null) {
                    jugadorActual.agregarPolitica(politica);
                }
            } catch (ReglaDistanciaException | ConstruccionExistenteException e){
                //¡Error! El lugar estaba ocupado o muy cerca. Devolvemos la plata.
                servicioComercio.reembolsarPoblado(getJugadorActual());
                throw e; // Avisamos a la vista
            }

        } catch (ReglaDistanciaException | ConstruccionExistenteException e) {
            System.out.println("No alcanza la plata: " + e.getMessage());
        }
    }

    public void moverLadron(Integer posicion){
        Jugador jugadorActual = getJugadorActual();
        List<Color> coloresDeVictimas = tablero.moverLadron(jugadorActual, posicion);

        // Convertir colores a jugadores
        List<Jugador> victimas = coloresDeVictimas.stream()
                .map(this::getJugadorPorColor)
                .collect(Collectors.toList());

        if(!victimas.isEmpty()){
            // Elegir víctima al azar
            Jugador victima = victimas.get(azar.nextInt(victimas.size()));

            // Ejecutar robo
            boolean roboExitoso = jugadorActual.robarRecurso(victima);

            if (roboExitoso) {
                System.out.println("Se robó un recurso a " + victima.getNombre());
            } else {
                System.out.println("La víctima no tenía recursos para robar.");
            }
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

        FichaComprable nuevaCiudad = servicioComercio.comprarObjeto(jugadorActual, new Ciudad(jugadorActual.getColor()));

        try {
            tablero.mejoraACiudadEn(coordenada,jugadorActual.getColor());
        } catch (IllegalStateException e) {
            // ROLLBACK: Si falló (no era dueño, no había poblado, etc.), devolvemos la plata.
            servicioComercio.reembolsarCiudad(jugadorActual);
            throw e; // Avisar a la vista del error
        }

    }

    public void setServicioComercio(ServicioComercio servicioComercio) {
        this.servicioComercio = servicioComercio;
    }

    public void intercambiarConJugadores(Jugador jugador1, TipoDeRecurso recursoAentregar, TipoDeRecurso recursoArecibir, List<Jugador> jugadores){
        servicioComercio.intercambiarConJugadores(jugador1,
                recursoAentregar,
                recursoArecibir,
                jugadores);
    }

    public void colocacionInicial( Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException, ReglaConstruccionException {
        Jugador jugador = getJugadorActualInicial();

        if (esperandoPoblado) {

            //Colocar poblado
            Poblado poblado = new Poblado(jugador.getColor());
            Dividendo dividendo = colocarEn(poblado, coordenada);

            contarPuntos();

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
            siguienteTurnoInicial();
        }

    }

    public Dividendo colocarEn(Construccion pieza, Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException, ReglaConstruccionException {

        if (pieza instanceof Productor) { // Poblado o Ciudad
            return tablero.colocarEnVertice(pieza, coordenada);
        } else { // Carretera
            return tablero.colocarEnLado(pieza, coordenada);
        }

    }
    public Tablero getTablero() {
        return tablero;
    }

    public Jugador buscarJugador(String nombre) {
        return this.jugadores.stream()
                .filter(jugador -> jugador.getNombre().equalsIgnoreCase(nombre)) // Compara ignorando mayúsculas
                .findFirst()
                .orElse(null); // Retorna null si no existe
    }

    public boolean estaEsperandoPobladoInicial() {
        return this.esperandoPoblado;
    }

    public boolean haTerminadoFaseInicial() {
        return ordenInicial.haTerminado();
    }

    // En ManagerTurno.java
    public Coordenada getUltimaCoordenadaPoblado() {
        return this.ultimaCoordenadaPoblado;
    }

    public String manejarLanzamientoDados(int suma) {
        if (suma == 7) {
            // Devuelve el reporte de quién perdió cartas
            return aplicarReglaDescartePorSiete();
        } else {
            repartirDividendos(suma);
            return "Se produjeron recursos.";
        }
    }

    private String aplicarReglaDescartePorSiete() {
        StringBuilder reporte = new StringBuilder();
        boolean alguienDescarto = false;

        for (Jugador j : jugadores) {
            // Usamos el método existente en tu clase Jugador
            if (j.totalRecursos() > 7) {
                // Este método ya hace la lógica de borrar del almacén y retorna qué borró
                Map<TipoDeRecurso, Integer> descartado = j.descartarMitadDeRecursos();

                // Calculamos cuánto perdió para el mensaje
                int cantidadPerdida = descartado.values().stream().mapToInt(Integer::intValue).sum();

                reporte.append("- ").append(j.getNombre())
                        .append(" descartó ").append(cantidadPerdida).append(" cartas.\n");
                alguienDescarto = true;
            }
        }

        if (!alguienDescarto) {
            return "Nadie tenía más de 7 cartas.";
        }
        return reporte.toString();
    }

    public Jugador getRutaComercialLider() {
        return granRutaComercial.getLider();
    }

    public Jugador getGranCaballeriaLider() {
        return granCaballeria.getLider();
    }
}
