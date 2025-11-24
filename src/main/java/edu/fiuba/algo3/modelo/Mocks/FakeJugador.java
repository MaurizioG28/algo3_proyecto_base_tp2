package edu.fiuba.algo3.modelo.Mocks;

import edu.fiuba.algo3.modelo.Jugador;
//import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Recursos.*;

public class FakeJugador extends Jugador {
    private boolean respuesta;

    public FakeJugador(boolean respuestaDefault) {
        this.respuesta = respuestaDefault;
    }

    public boolean tiene(Madera madera, Ladrillo ladrillos, Lana lana, Mineral mineral, Grano grano) {
        return respuesta;
    }

    public void quitarRecurso(TipoDeRecurso recurso, int cantidad) {

    }

}
