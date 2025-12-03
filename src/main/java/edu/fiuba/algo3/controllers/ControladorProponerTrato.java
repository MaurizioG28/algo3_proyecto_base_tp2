package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recursos.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ControladorProponerTrato implements EventHandler<ActionEvent> {
    private final Stage ventana;

    // Referencias a los inputs de la vista
    private final ComboBox<String> cmbJugadorDestino;
    private final ComboBox<String> cmbRecursoDoy;
    private final TextField txtCantDoy;
    private final ComboBox<String> cmbRecursoPido;
    private final TextField txtCantPido;

    public ControladorProponerTrato(Stage ventana,
                                    ComboBox<String> cmbJugador,
                                    ComboBox<String> cmbDoy, TextField txtDoy,
                                    ComboBox<String> cmbPido, TextField txtPido) {
        this.ventana = ventana;
        this.cmbJugadorDestino = cmbJugador;
        this.cmbRecursoDoy = cmbDoy;
        this.txtCantDoy = txtDoy;
        this.cmbRecursoPido = cmbPido;
        this.txtCantPido = txtPido;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            //Obtener Datos
            String nombreDestino = cmbJugadorDestino.getValue();
            String recursoDoyStr = cmbRecursoDoy.getValue();
            String recursoPidoStr = cmbRecursoPido.getValue();

            // Parsear Cantidades
            int cantDoy = Integer.parseInt(txtCantDoy.getText());
            int cantPido = Integer.parseInt(txtCantPido.getText());

            //Validaciones Básicas
            if (nombreDestino == null || recursoDoyStr == null || recursoPidoStr == null) {
                System.out.println("Por favor completa todos los campos.");
                return;
            }
            if (cantDoy <= 0 || cantPido <= 0) {
                System.out.println("Las cantidades deben ser mayores a 0.");
                return;
            }


            Jugador jugadorActual = Catan.getInstance().getManagerTurno().getJugadorActual();
            Jugador jugadorDestino = Catan.getInstance().getManagerTurno().buscarJugador(nombreDestino); // Implementar búsqueda

            TipoDeRecurso recursoDoy = crearRecurso(recursoDoyStr, cantDoy); // Con cantidad real
            TipoDeRecurso recursoPido = crearRecurso(recursoPidoStr, cantPido);


            jugadorActual.intercambiar( recursoDoy,jugadorDestino, recursoPido);


//            mostrarAlerta("Éxito", "Intercambio realizado.");
            ventana.close();

            System.out.println("PROPUESTA: Doy " + cantDoy + " " + recursoDoyStr +
                    " a " + nombreDestino +
                    " por " + cantPido + " " + recursoPidoStr);

            // Llamar al Manager
            // catan.getManagerTurno().intercambiar(jugadorDestino, tipoDoy, cantDoy, tipoPido, cantPido);

            ventana.close();

        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa números válidos en las cantidades.");
        } catch (Exception e) {
            System.out.println("Error en el intercambio: " + e.getMessage());
        } catch (RecursosIsuficientesException e) {
            throw new RuntimeException(e);
        }
    }


    private TipoDeRecurso crearRecurso(String nombre,int cantidad) {
        switch (nombre.toLowerCase()) {
            case "madera": return new Madera(cantidad);
            case "ladrillo": return new Ladrillo(cantidad);
            case "lana": return new Lana(cantidad);
            case "grano": return new Grano(cantidad);
            case "mineral": return new Mineral(cantidad);
            default: throw new IllegalArgumentException("Recurso desconocido");
        }
    }
}
