package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.Color;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ManagerTurno;
import edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.vistas.VistaPedirCantidadJugadores;
import edu.fiuba.algo3.vistas.vistas.VistaTablero;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ControladorCrearPartida implements EventHandler<ActionEvent> {
    private final Stage stage   ;
    private final PantallaPrincipal pantallaPrincipal   ;

    public ControladorCrearPartida(Stage stage, PantallaPrincipal pantallaPrincipal, ArrayList<TextField> nombresIngresados, ArrayList<ColorPicker> coloresElegidos) {
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;
        List<Jugador> jugadores = new ArrayList<>();
        int i = 0;
        nombresIngresados.forEach(textField -> {
            String nombreJugador = textField.getText();
            System.out.println("nombreJugador: " + nombreJugador);

            String colorJUgador = coloresElegidos.get(i).getValue().toString();
            System.out.println("colorJUgador: " + colorJUgador);
            jugadores.add(new Jugador(nombreJugador, new Color(colorJUgador)));
        });


    }

    @Override
    public void handle(ActionEvent actionEvent) {
        pantallaPrincipal.setCentro(new VistaTablero(stage,pantallaPrincipal));
    }
}
