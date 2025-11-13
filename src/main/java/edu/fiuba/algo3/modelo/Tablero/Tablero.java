package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.*;

public class Tablero {
    private Dados dados = new Dados();
    public int tirarDados(){
        return dados.tirar();
    }

    public void construirPoblado(Jugador jugadorActual, IVertice vertice) {
        vertice.validarConstruccion(jugadorActual);
    }
}
