package edu.fiuba.algo3.modelo.Tablero;

import java.util.*;
import java.util.stream.Collectors;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;

import edu.fiuba.algo3.modelo.Intercambios.PoliticaDeIntercambio;
import edu.fiuba.algo3.modelo.Tablero.Factory.*;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;



public class Tablero {


    private final Map<Integer, Terreno> terrenos;
    private Dados dados = new Dados();
    private final Map<Coordenada, Vertice> vertices;
    private final Map<Coordenada, Lado> lados;
    private final Map<Color, Integer> pobladosColocadosPorColor = new HashMap<>();

    private Integer posicionDelLadron;

//    public Tablero(){
//    }

    public Tablero(Map<Integer, Terreno> hexagonos, Map<Coordenada, Vertice> vertices, Map<Coordenada, Lado> ladosPorCoordenada) {
        this.terrenos = hexagonos;
        this.vertices = vertices;
        this.lados= ladosPorCoordenada;

        this.posicionDelLadron= encontrarPosicionInicialDelLadron();
    }

    private Integer encontrarPosicionInicialDelLadron() {
        for (Map.Entry<Integer, Terreno> entry : terrenos.entrySet()) {
            Terreno terreno = entry.getValue();
            if (terreno.esDesierto()) {
                return entry.getKey();
            }
        }
       return 0;
    }
    public Integer getPosicionDelLadron() {
        return posicionDelLadron;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tablero)) return false;
        Tablero otro = (Tablero) o;


        if (this.terrenos.size() != otro.terrenos.size())
            return false;

        // comparar contenido terreno a terreno
        for (Integer id : terrenos.keySet()) {
            Terreno t1 = this.terrenos.get(id);
            Terreno t2 = otro.terrenos.get(id);

            if (t2 == null) return false;

            // compara tipo (Bosque, Colina, etc.)
            if (!t1.getClass().equals(t2.getClass()))
                return false;

            // compara producción (excepto desierto)
            Produccion p1 = t1.getProduccion();
            Produccion p2 = t2.getProduccion();

            if (p1 == null && p2 != null) return false;
            if (p1 != null && p2 == null) return false;
            if (p1 != null && !p1.equals(p2)) return false;

        }

        return true;
    }
    @Override
    public int hashCode() {
        return terrenos.hashCode();
    }


  
    public List<List<Dividendo>> tirarDados(Dados dados){
        int valor = dados.tirar();
        return distribuirProduccion(valor);

    }






    public void construirPoblado(Color jugador, Vertice vertice) throws ReglaDistanciaException {
//

        if (vertice.tieneConstruccion() || vertice.tieneConstruccionAdyacente()) {
            throw new ReglaDistanciaException("No se puede construir tan cerca de otro poblado.");
        }
        /*
        construye directamente, falta implementar el chequeo de recursos del jugardor.
        Con algo como jugador.recursosPoblado()
        */
        try {
            vertice.colocar(new Poblado(jugador));
        } catch (ConstruccionExistenteException e) {
            throw new RuntimeException(e);
        }

    }

    public List<List<Dividendo>> distribuirProduccion(int numeroDado) {
        // Iteramos sobre los valores del mapa 'terrenos'
        List<List<Dividendo>> listalistaDividendos = new ArrayList<>();
        for (Terreno terreno : this.terrenos.values()) {

            List<Dividendo> listaDividendos =terreno.verificarYProducir(numeroDado);
            if(listaDividendos!=null)
                listalistaDividendos.add(listaDividendos );
        }
        return listalistaDividendos;
    }


    public List<Color> moverLadron(Jugador jugadorActual, Integer posicionId) {
        Terreno nuevo = terrenos.get(posicionId);
        Terreno anterior = terrenos.get(posicionDelLadron);
        if(nuevo==anterior)
            throw new IllegalStateException("Se debe movera al Ladron.");

        // Quitar ladrón del terreno anterior (si no es desierto)
        if (anterior!=null && !anterior.esDesierto())
            anterior.moverLadronQuitar();


        nuevo.moverLadronPoner();
        posicionDelLadron = posicionId;

        // Obtener víctimas delegando en Terreno
        List<Color> victimas= nuevo.jugadoresAfectadosPorElLadron(jugadorActual);
        return victimas;
    }



    public Dividendo colocarEnVertice(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException, ReglaDistanciaException {
        Vertice verticeObjetivo = vertices.get(coordenada);
        Color colorActual = pieza.getColorActual();

        if (verticeObjetivo.tieneConstruccionAdyacente())
            throw new ReglaDistanciaException("No se puede colocar el poblado por la regla de distancia");

        verticeObjetivo.colocar(pieza);
        pobladosColocadosPorColor.put(colorActual, pobladosColocadosPorColor.getOrDefault(colorActual, 0) + 1);


        if ( esSegundoPoblado(pieza.getColorActual()))
            return calcularDividendosIniciales(coordenada,colorActual);

        return Dividendo.vacio();
    }

    private boolean esSegundoPoblado(Color color) {
        return (pobladosColocadosPorColor.get(color)==2);
    }

    private Dividendo calcularDividendosIniciales(Coordenada coord, Color colorActual) {
        Vertice v = vertices.get(coord);
        Dividendo d = new Dividendo(colorActual);

        for (Terreno terrenoActual : terrenos.values()) {
            if (terrenoActual.tieneVertice(v) && terrenoActual.sePuedeProducir()) {
                int cantidad = v.factorProduccion();
                d.agregar(terrenoActual.recursoOtorgado(cantidad));
            }
        }

        return d;
    }

    public Dividendo colocarEnLado(Construccion pieza, Coordenada coordenada) throws ConstruccionExistenteException, ReglaConstruccionException {

        Lado l = lados.get(coordenada);
        l.colocar(pieza);
        return null;
    }

    public boolean tieneCarreteraEn(Coordenada caminoEsperadoEn) {
        Lado l = lados.get(caminoEsperadoEn);
        return l.tieneConstruccion();
    }
    public Vertice obtenerVertice(Coordenada coordenada) {
        return this.vertices.get(coordenada);
    }

    public Produccion getProduccionDelLadron() {
        Terreno t= terrenos.get(posicionDelLadron);
        return t.getProduccion();
    }

    public PuntajeDeVictoria calcularPuntosDeVictoriaPorConstruccion(Color color) {
        PuntajeDeVictoria puntos=new PuntajeDeVictoria();

        for(Vertice v : new HashSet<>(vertices.values())){
            if(v.colorDeConstruccionEquals(color) ){
                puntos.agregarPuntos(v.factorProduccion());

            }
        }

        return puntos;
    }

    public void mejoraACiudadEn(Coordenada coordenada,Color colorJugador) throws IllegalStateException {
        Vertice verticeBuscado= vertices.get(coordenada);
        if(!verticeBuscado.tieneConstruccion()){
            throw new IllegalStateException("No hay ninguna construcción en el vértice seleccionado.");
        }
        // Validaciones de negocio (se pueden mover a Vertice también)
        if (verticeBuscado.getPropietario() != colorJugador) {
            throw new IllegalStateException("No puedes mejorar un edificio que no es tuyo.");
        }
        verticeBuscado.mejorarACiudad();
    }

    public List<Lado> obtenerLadosDeJugador(Color color) {
        return lados.values().stream()
                .filter(lado -> lado.colorDeConstruccionEquals(color))
                .collect(Collectors.toList());
    }


    public PoliticaDeIntercambio verificarPuerto(Coordenada coordenada) {
        Vertice vertice = vertices.get(coordenada);
        if(vertice.esPuerto()) {
            return vertice.obtenerPoliticaDeIntercambio();
        }
        return null;

    }
}

