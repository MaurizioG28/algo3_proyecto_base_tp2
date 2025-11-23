package edu.fiuba.algo3.modelo.Mocks;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;

public class FakeJugador extends Jugador {
    private boolean respuesta;

    public FakeJugador(boolean respuestaDefault) {
        this.respuesta = respuestaDefault;
    }


    public boolean tiene(int madera, int ladrillo, int lana, int grano, int mineral) {
        return respuesta;
    }


    public void removerRecurso(TipoDeRecurso recurso, int cantidad) {

    }

}
