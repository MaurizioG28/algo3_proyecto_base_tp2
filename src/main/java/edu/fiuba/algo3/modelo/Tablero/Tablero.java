package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;

import edu.fiuba.algo3.modelo.Contruccion.TipoConstruccion;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;


public class Tablero {

    private static final int CANTIDAD_HEXAGONOS = 19;
    private final Map<TipoTerreno, Terreno> terrenos = Map.of(
            TipoTerreno.BOSQUE, new Bosque(),
            TipoTerreno.COLINA, new Colina(),
            TipoTerreno.PRADERA, new Pastizal(),
            TipoTerreno.CAMPO, new Campo(),
            TipoTerreno.MONTANIA, new Montania(),
            TipoTerreno.DESIERTO, new Desierto()
        );

    boolean tableroInicializado = false;
    //la distribucion de fichas numeradas son las siguientes: un 2 y un 12, luego dos de cada una entre 3 y 11, el 7 está excluido
    private int[] fichasNumeradas = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
    
    private Dados dados = new Dados();

    private final ArrayList<Hexagono> hexagonos = new ArrayList<>();

    private Hexagono posicionDelLadron;

    public Tablero(){
    }

    public void setUp(){
        while(hexagonos.size() < CANTIDAD_HEXAGONOS){
            Terreno terreno = sortearTerreno();

            terreno.agregarTerreno(hexagonos, fichasNumeradas);
        }
        this.tableroInicializado = true;
        this.posicionDelLadron = buscarHexagonoDesierto();
        if(this.posicionDelLadron == null){
            throw new IllegalStateException("No se encontro el Ladron");
        }
    }
  
    public int tirarDados(){
        return dados.tirar();
    }

    private Hexagono buscarHexagonoDesierto() {
        for (Hexagono hex : hexagonos) {
            if (hex.getTipo() == TipoTerreno.DESIERTO) {
                return hex;
            }
        }
        return null;
    }

    private Terreno sortearTerreno(){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(terrenos.size());
        TipoTerreno tipoSorteado = (TipoTerreno) terrenos.keySet().toArray()[numeroAleatorio];

        return terrenos.get(tipoSorteado);
    }

    public boolean tableroCorrectamenteInicializado(){
        boolean correcto = true;
        Collection<Terreno> todosTerrenos = terrenos.values();
        for (Terreno terreno : todosTerrenos) {
            if (!terreno.todosColocados()) {
                correcto = false;
            }
        }

        return correcto;
    }

    public void construirPoblado(Jugador jugador, IVertice vertice) throws ReglaDistanciaException {

        if (vertice.tieneConstruccion() || vertice.tieneConstruccionAdyacente()) {
            throw new ReglaDistanciaException("No se puede construir tan cerca de otro poblado.");
        }

        vertice.colocarPoblado(jugador);

        // Ahora el vértice debe devolver List<TipoDeRecurso>
        List<TipoDeRecurso> recursos = vertice.darRecursos(); // cada uno con cantidad 1

        for (TipoDeRecurso r : recursos) {
            jugador.agregarRecurso(r); // new Madera(1), new Grano(1), etc.
        }
    }

    private Map<Jugador, EnumMap<Recurso, Integer>> calcularProduccion(int numeroLanzado){
        Map<Jugador, EnumMap<Recurso, Integer>> produccion = new HashMap<>();

        for (Hexagono h : hexagonos) {
            if (h.getNumero() != numeroLanzado) continue;
            if (!h.getTipo().produceAlgo()) continue;    // desierto no produce
            if (!h.sePuedeProducir()) continue;          // si usás ladrón

            Recurso r = h.getTipo().recursoOtorgado();

            for (Vertice v : h.getVertices()) {
                if (!v.tieneConstruccion()) continue;

                int cant = (v.getTipoConstruccion() == TipoConstruccion.CIUDAD) ? 2 : 1;
                Jugador j = v.getPropietario();

                produccion
                        .computeIfAbsent(j, k -> new EnumMap<>(Recurso.class))
                        .merge(r, cant, Integer::sum);
            }
        }
        return produccion;
    }

    public void repartirProduccion(int numeroLanzado){
        Map<Jugador, EnumMap<Recurso, Integer>> bolsa = calcularProduccion(numeroLanzado);
        bolsa.forEach((jug, mapa) -> mapa.forEach(jug::agregarRecurso)); // (recurso,cantidad)
    }

    public void agregarHexagono(Hexagono h) {
        this.hexagonos.add(h);
    }

    public List<Jugador> moverLadron(Jugador jugadorActual, Hexagono posicion) {
//        if (!tableroInicializado) {
//            throw new IllegalStateException("El tablero debe estar inicializado antes de mover al ladrón.");
//        }

        this.posicionDelLadron = posicion;
        List<Jugador> victimas = new ArrayList<>();
        for (Vertice v : posicion.getVertices()) {
            if (!v.tieneConstruccion()) continue;
            Jugador propietario = v.getPropietario();
            if (propietario == null) continue;
            if (propietario.equals(jugadorActual)) continue;
            if (!victimas.contains(propietario)) {
                victimas.add(propietario);
            }
        }
        return victimas;
    }

}
