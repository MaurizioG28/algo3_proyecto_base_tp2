package edu.fiuba.algo3.modelo.Mocks;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;

import java.util.List;

public class FakeJugador extends Jugador {
    private boolean respuesta;
    private TipoDeRecurso eleccionRecurso1;
    private TipoDeRecurso eleccionRecurso2;

    public FakeJugador(boolean respuestaDefault) {
        super("nombre1", new Color("Rojo"));
        this.respuesta = respuestaDefault;
        this.eleccionRecurso1 = new Madera(1);
        this.eleccionRecurso2 = new Madera(1);
    }

    public FakeJugador(boolean respuestaDefault, TipoDeRecurso eleccionRecurso1, TipoDeRecurso eleccionRecurso2) {
        super("nombre1", new Color("Rojo"));
        this.respuesta = respuestaDefault;
        this.eleccionRecurso1 = eleccionRecurso1;
        this.eleccionRecurso2 = eleccionRecurso2;
    }


    public boolean tiene(Madera madera, Ladrillo ladrillos, Lana lana, Mineral mineral, Grano grano) {
        return respuesta;
    }

    public void quitarRecurso(TipoDeRecurso recurso, int cantidad) {

    }

    @Override
    public List<TipoDeRecurso> pedirRecursos() {
        return List.of(eleccionRecurso1, eleccionRecurso2);
    }

    @Override
    public int pedirCoordenada() {
        // Deberia elegir una posicion desde la interfaz para mover al ladron desde la interfaz
        return 1;
    }

}
