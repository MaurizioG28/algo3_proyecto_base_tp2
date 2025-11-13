package edu.fiuba.algo3.modelo.Mocks;
import edu.fiuba.algo3.modelo.ICelda;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.LinkedList;
import java.util.List;

public class FakeVertice implements IVertice {

    private boolean valido;
    private List<ICelda> celdas;

    public FakeVertice(boolean valido, List<ICelda> celdas) {
        this.valido = valido;
        this.celdas = celdas;
    }

    @Override
    public boolean validarConstruccion(Jugador jugador) {
        return valido;
    }

    @Override
    public List<ICelda> obtenerCeldasAdyacentes() {
        return celdas;
    }

    @Override
    public List<Recurso> darRecursos() {
        List<Recurso> recursos = new LinkedList<>();
        for (ICelda celda : celdas) {
            recursos.add(celda.darRecurso());
        }
        return recursos;
    }

}
