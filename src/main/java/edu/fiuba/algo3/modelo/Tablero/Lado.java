package edu.fiuba.algo3.modelo.Tablero;

import edu.fiuba.algo3.modelo.Contruccion.Carretera;
import edu.fiuba.algo3.modelo.Contruccion.Construccion;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public class Lado {
    private Jugador propietario;
    private ArrayList<Lado> adyacentes = new ArrayList<>();
    private Carretera construccion = null;
    public boolean tieneConstruccion() {
        return construccion != null;
    }

    public void colocar(Carretera pieza) {
        this.construccion = pieza;
    }

    public void agregarAdyacente(Lado l) {
        adyacentes.add(l);
    }
}
