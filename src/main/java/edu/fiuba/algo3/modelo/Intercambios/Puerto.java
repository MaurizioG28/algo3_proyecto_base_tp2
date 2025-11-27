package edu.fiuba.algo3.modelo.Intercambios;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class Puerto {
    private final PoliticaDeIntercambio politica;


    public Puerto(PoliticaDeIntercambio politica) {
        this.politica = politica;

    }



    public boolean puedeIntercambiar(Jugador jugador, TipoDeRecurso recursoOfrecido) {
        return politica.aplicaA(jugador, recursoOfrecido);
    }

    public int getTasa() {
        return politica.tasa();
    }

    public PoliticaDeIntercambio getPolitica() {
        return politica;
    }


}
