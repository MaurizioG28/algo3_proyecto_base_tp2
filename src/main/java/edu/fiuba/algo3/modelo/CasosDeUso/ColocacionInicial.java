package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Tablero.Coordenada;

import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.TableroProduccion;

public class ColocacionInicial {
    private TableroProduccion tablero;
    public ColocacionInicial(TableroProduccion unTablero) {

        this.tablero = unTablero;
    }

    public Dividendo colocarEn(Poblado poblado, Coordenada coordenada) throws ReglaDistanciaException {

            return tablero.colocarPoblado(poblado, coordenada);


    }


}
