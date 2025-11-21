package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class PuertoGenerico implements PoliticaDeIntercambio {

    private final int tasa; // normalmente 3

    public PuertoGenerico(int tasa) {
        this.tasa = tasa;
    }

    @Override
    public boolean aplicaA(Jugador jugador, TipoDeRecurso recursoEntregado) {
        return true; // sirve para cualquier recurso
    }

    @Override
    public int tasa() {
        return tasa;
    }
}