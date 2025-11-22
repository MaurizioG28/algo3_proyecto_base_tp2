package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Dividendo;
import edu.fiuba.algo3.modelo.Tablero.*;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;

public class ColocacionInicial {
    private TableroProduccion tablero;
    public ColocacionInicial(TableroProduccion unTablero) {

        this.tablero = unTablero;
    }

    public Dividendo colocarEn(Poblado poblado, Coordenada coordenada) throws ReglaDistanciaException, ConstruccionExistenteException {

            return tablero.colocarPoblado(poblado, coordenada);


    }


    public void colocarCarretera(Carretera azul, Coordenada coordenada) throws PosInvalidaParaConstruirException {
        tablero.colocarCarretera(azul, coordenada);
    }

    public boolean tineCarreteraEn(Coordenada caminoEsperadoEn) {
        return tablero.tieneCarreteraEn(caminoEsperadoEn);
    }
}
