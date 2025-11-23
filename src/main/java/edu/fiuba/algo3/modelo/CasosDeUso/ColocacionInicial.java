package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Contruccion.Productor;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Tablero.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;

public class ColocacionInicial {
    private Tablero tablero;
    public ColocacionInicial(Tablero unTablero) {

        this.tablero = unTablero;
    }

    public Dividendo colocarEn(Construccion pieza, Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException {

        if (pieza instanceof Productor) { // Poblado o Ciudad
            return tablero.colocarEnVertice(pieza, coordenada);
        } else { // Carretera
            return tablero.colocarEnLado(pieza, coordenada);
        }


    }




    public boolean tineCarreteraEn(Coordenada caminoEsperadoEn) {
        return tablero.tieneCarreteraEn(caminoEsperadoEn);
    }
}
