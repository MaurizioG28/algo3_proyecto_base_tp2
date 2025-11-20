package edu.fiuba.algo3.modelo.interfaces;

import edu.fiuba.algo3.modelo.Tablero.Costo;

public interface IJugador {
    public void pagar(Costo costoCamino);
    public boolean tiene();
}
