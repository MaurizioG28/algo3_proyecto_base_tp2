package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Vertice;

public class ConsumoRecursos {

    public void colocarPoblado(Jugador jugador, Vertice vertice) throws ConstruccionExistenteException,ReglaDistanciaException {
        if(vertice.tieneConstruccionAdyacente()){throw new ReglaDistanciaException("Un vertice adyacente tiene contruccion");}
        Construccion poblado = jugador.comprarPoblado();
        if(poblado != null){
            vertice.colocar(poblado);
        }

    }
}
