package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.PuntajeDeVictoria;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

public class CasoDeUsoPV {
    private Tablero tablero;
    public CasoDeUsoPV(Tablero tablero) {
        this.tablero = tablero;
    }

    public void colocacionInicial(Jugador jugador,Coordenada coordenada1, Coordenada coordenada2) {
        try {
            tablero.colocarEnVertice(new Poblado(jugador.getColor()),coordenada1);
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
        try {
            tablero.colocarEnVertice(new Poblado(jugador.getColor()),coordenada2);
        } catch (ConstruccionExistenteException | ReglaDistanciaException e) {
            throw new RuntimeException(e);
        }
    }

    public PuntajeDeVictoria obtenerPuntosDeVictoria(Jugador jugador) {
        return tablero.calcularPuntosDeVictoriaPorConstruccion(jugador.getColor());
    }

    public void mejorarPoblado(Jugador jugador) {
        colocacionInicial(jugador,new Coordenada(1,0),new Coordenada(2,3));
        tablero.mejoraACiudadEn(new Coordenada(1,0),jugador.getColor());

    }
}
