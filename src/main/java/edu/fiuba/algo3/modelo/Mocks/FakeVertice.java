package edu.fiuba.algo3.modelo.Mocks;
import edu.fiuba.algo3.modelo.ICelda;
import edu.fiuba.algo3.modelo.IVertice;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.LinkedList;
import java.util.List;

public class FakeVertice implements IVertice {

    private boolean noValido;
    private List<ICelda> celdasAdyacentes;

    public FakeVertice(boolean valido, List<ICelda> celdas) {
        this.noValido = valido;
        this.celdasAdyacentes = celdas;
    }

    @Override
    public List<ICelda> obtenerCeldasAdyacentes() {
        return celdasAdyacentes;
    }

    @Override
    public List<Recurso> darRecursos() {
        List<Recurso> recursos = new LinkedList<>();
        for (ICelda celda : celdasAdyacentes) {
            recursos.add(celda.darRecurso());
        }
        return recursos;
    }

    @Override
    public boolean tieneConstruccionAdyacente() {
        return false;
    }

    @Override
    public boolean tieneConstruccion() {
        return noValido;
    }

    @Override
    public void colocarPoblado(Jugador jugador) {

    }

}
