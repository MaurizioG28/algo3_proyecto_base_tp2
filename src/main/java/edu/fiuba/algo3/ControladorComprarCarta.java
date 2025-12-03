package edu.fiuba.algo3;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControladorComprarCarta implements EventHandler<ActionEvent> {

    private final VistaTablero2 vista;

    public ControladorComprarCarta(VistaTablero2 vista) {
        this.vista = vista;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Catan.getInstance().getManagerTurno().comprarCarta();

            // Actualizamos la vista
            vista.actualizarInventario();
            vista.actualizarEstadoBotones();

            // Feedback visual simple
            System.out.println("Carta comprada exitosamente");

        } catch (Exception e) {

        }
    }

}
