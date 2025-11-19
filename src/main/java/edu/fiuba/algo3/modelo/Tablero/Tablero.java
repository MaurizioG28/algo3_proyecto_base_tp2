package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;

import edu.fiuba.algo3.modelo.Contruccion.TipoConstruccion;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;


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

    //la distribucion de fichas numeradas son las siguientes: un 2 y un 12, luego dos de cada una entre 3 y 11, el 7 está excluido
    private int[] fichasNumeradas = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
    
    private Dados dados = new Dados();

    private ArrayList<Hexagono> hexagonos = new ArrayList<>();
    private List<Terreno> listaHexagonos;

    private Hexagono posicionDelLadron;

    public Tablero(){
    }

    public Tablero(List<Terreno> hexagonos, List<Produccion> fichasNumeradas) {
        this.listaHexagonos = hexagonos;
        asignarFichas(fichasNumeradas);
    }

    private void asignarFichas(List<Produccion> fichasNumeradas) {
        for(Terreno hexagono: this.listaHexagonos){
            if(!hexagono.esDesierto()){
                hexagono.setProduccion(fichasNumeradas.iterator().next());
            }
        }
    }

    @Override
    public boolean equals(Object object){
        if(this.getClass() != object.getClass()){return false;}
        return ((Tablero) object).mismosHexagonos(listaHexagonos);
    }

    private boolean mismosHexagonos(List<Terreno> hexagonos){
        for (int i = 0; i < hexagonos.size(); i++){
            if(!listaHexagonos.get(i).equals(hexagonos.get(i))){
                return false;
            }
        }
        return true;
    }

    public void setUp(){
        while(hexagonos.size() < CANTIDAD_HEXAGONOS){
            Terreno terreno = sortearTerreno();

            terreno.agregarTerreno(hexagonos, fichasNumeradas);
        }
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
//        if (!tableroInicializado) {
//            throw new IllegalStateException("El tablero debe estar inicializado antes de construir poblados.");
//        }

        if (vertice.tieneConstruccion() || vertice.tieneConstruccionAdyacente()) {
            throw new ReglaDistanciaException("No se puede construir tan cerca de otro poblado.");
        }
        /*
        construye directamente, falta implementar el chequeo de recursos del jugardor.
        Con algo como jugador.recursosPoblado()
        */
        vertice.colocarPoblado(jugador);
        List<Recurso> recursos = vertice.darRecursos();
        jugador.sumarRecursos(recursos);
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

                int cant = v.obtenerFactorProduccion();
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
        posicion.moverLadron();
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
