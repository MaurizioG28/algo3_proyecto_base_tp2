package edu.fiuba.algo3.modelo.Mocks;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

public class FakeJugador extends Jugador {
    private boolean respuesta;

    public FakeJugador(boolean respuestaDefault) {
        this.respuesta = respuestaDefault;
    }

    @Override
    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {
        return respuesta;
    }

    @Override
    public void removerRecurso(Recurso recurso, int cantidad) {

    }

}
