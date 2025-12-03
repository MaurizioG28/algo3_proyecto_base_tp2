package edu.fiuba.algo3.modelo.Cartas;

import edu.fiuba.algo3.modelo.Recursos.Grano;
import edu.fiuba.algo3.modelo.Recursos.Lana;
import edu.fiuba.algo3.modelo.Recursos.Mineral;
import edu.fiuba.algo3.modelo.Recursos.TipoDeRecurso;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Tablero;

import java.util.List;

abstract public class CartaDesarrollo {
    private IEstadoCarta estado;
    public  CartaDesarrollo() {
        this.estado = new EstadoCartaRecienComprada();
    }

    public static List<TipoDeRecurso> costoRecursos() {
        return List.of(
                new Grano(1), new Mineral(1),new Lana(1)
        );
    }

    protected CartaDesarrollo(IEstadoCarta estado) {
        this.estado = estado;
    }
    public void nuevoTurno() {
        this.estado = this.estado.actualizarEstado();
    }
    public void usar() {
        this.estado.comprobarUso();
    }
    public abstract void ejecutarEfecto(Jugador jugador, Tablero tablero, List<Jugador> oponentes);
}
