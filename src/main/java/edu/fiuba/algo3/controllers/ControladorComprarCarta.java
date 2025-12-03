package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.vistas.vistas.VistaTablero2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ControladorComprarCarta  implements EventHandler<ActionEvent> {
    private VistaTablero2 vista;

    public ControladorComprarCarta(VistaTablero2 vista) {
        this.vista = vista;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Catan.getInstance().getManagerTurno().comprarCarta();

            // 2. Feedback visual
            mostrarAlerta("¡Compra Exitosa!", "Has adquirido una nueva Carta de Desarrollo.");

            // 3. Actualizar la vista (recursos y cartas)
            vista.actualizarInventario();
            vista.actualizarEstadoBotones(); // Para deshabilitar el botón si te quedaste sin recursos

        } catch (RuntimeException e) { // Capturamos Excepciones del modelo (ej: RecursosInsuficientes)
            mostrarAlerta("Error en la compra", "No tienes suficientes recursos o no quedan cartas.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

