package edu.fiuba.algo3.modelo.CasosDeUso;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Contruccion.Poblado;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Tablero.ConstruccionExistenteException;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.ReglaDistanciaException;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;

public class CasoDeUsoLadron {
    private Tablero tablero;
    private Jugador jugador;
    public CasoDeUsoLadron(Tablero tablero, Jugador jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
    }

    public List<Color> moverLadron(Jugador actual, int i) {
        return tablero.moverLadron(actual, i);

    }

    public void colocarVictima(Color color, Coordenada coordenada) throws ConstruccionExistenteException, ReglaDistanciaException {
        tablero.colocarEnVertice(new Poblado(color),coordenada);
    }

    public boolean robarRecursoDeVictima(Jugador victima1) {
        return jugador.robarRecurso(victima1);
    }

    public int verificarRecursoRobado(TipoDeRecurso tipoDeRecurso) {
        return jugador.CantidadRecurso(tipoDeRecurso);
    }
}
