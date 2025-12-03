package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.vistas.botones.BotonVertice;

public class ControladorVertice {
    private final BotonVertice vista;
    private final ManagerTurno manager;
    private final Catan catan = Catan.getInstance();

    public ControladorVertice(BotonVertice vista, ManagerTurno manager) {
        this.vista = vista;
        this.manager = manager;

        if(catan.getTablero().obtenerVertice(vista.getCoordenada()).esPuerto()){
            this.vista.setStyle("-fx-background-radius: 100%; -fx-background-color: #79180d;-fx-opacity: 1.0;");
            System.out.println("puerto en: (" + vista.getCoordenada().numHex()+","+vista.getCoordenada().indice()+")");
        }
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.setOnAction(e -> manejarClick());
    }

    private void manejarClick() {
//        try {
//            // Aquí construimos un poblado por ejemplo
//            manager.colocarPobladoInicial(vista.getCoordenada());
//        } catch (Exception ex) {
//            System.out.println("No se pudo colocar construcción: " + ex.getMessage());
//        }
    }
}
