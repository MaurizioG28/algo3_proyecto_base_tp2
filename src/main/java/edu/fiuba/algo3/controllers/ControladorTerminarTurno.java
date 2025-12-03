package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.vistas.botones.BotonLanzarDados;
import edu.fiuba.algo3.vistas.botones.BotonTerminarTurno;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControladorTerminarTurno implements EventHandler<ActionEvent> {

    private final BotonLanzarDados botonLanzar;
    private BotonTerminarTurno botonTerminar;
    private final VistaTablero2 vista; // <--- Referencia necesaria para actualizar la GUI

    // Constructor actualizado
    public ControladorTerminarTurno(BotonLanzarDados botonLanzar, VistaTablero2 vista) {
        this.botonLanzar = botonLanzar;
        this.vista = vista;
    }

    public void setBotonTerminar(BotonTerminarTurno btnTerminar) {
        this.botonTerminar = btnTerminar;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println(">>> Fin de turno");

        // 1. Avanzar turno en el Modelo
        Catan.getInstance().getManagerTurno().siguienteTurno();

        // 2. IMPORTANTE: Actualizar la Vista
        // Esto hace que cambie el nombre, el color y las cartas del panel inferior
        vista.actualizarInventario();
        vista.actualizarEstadoBotones();
        vista.deshabilitarBotonesJuegoNormal();

        // 3. Gestionar botones
        this.botonLanzar.setDisable(false); // Habilitar dados para el nuevo jugador

        if (this.botonTerminar != null) {
            this.botonTerminar.setDisable(true); // Deshabilitar terminar hasta que lance
        }
    }
}
