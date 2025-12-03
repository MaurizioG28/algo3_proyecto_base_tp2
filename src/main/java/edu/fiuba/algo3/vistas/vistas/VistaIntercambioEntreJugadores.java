package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.controllers.ControladorIntercambioEntreJugadores;
import edu.fiuba.algo3.controllers.ControladorProponerTrato;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class VistaIntercambioEntreJugadores extends Stage {
    private static final String IMAGEN = "/imagenes/intercambioj.png";

    public VistaIntercambioEntreJugadores(Stage owner) {
        Catan catan = Catan.getInstance();
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
        this.initStyle(StageStyle.UNDECORATED);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
                "-fx-border-color: #dabf11;" +
                        "-fx-border-width: 6;" +
                        "-fx-border-radius: 0;"
        );

        configurarFondo(layout);


        Label titulo = new Label("INTERCAMBIO CON JUGADOR");
        titulo.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 18px; -fx-font-weight: bold;");

        // ELEGIR JUGADOR ---
        ComboBox<String> cmbJugadores = new ComboBox<>();
        cmbJugadores.setPromptText("Seleccionar Jugador...");

        // Llenar combo excluyendo al jugador actual
        Jugador actual = catan.getManagerTurno().getJugadorActual();
        for (Jugador j : catan.getJugadores()) {
            if (!j.equals(actual)) {
                cmbJugadores.getItems().add(j.getNombre());
            }
        }

        // LO QUE YO DOY (Recurso + Cantidad) ---
        HBox panelDoy = new HBox(10);
        panelDoy.setAlignment(Pos.CENTER);

        ComboBox<String> cmbRecursoDoy = new ComboBox<>();
        cmbRecursoDoy.getItems().addAll("Madera", "Ladrillo", "Lana", "Grano", "Mineral");
        cmbRecursoDoy.setPromptText("Doy...");

        TextField txtCantDoy = new TextField("1"); // Valor por defecto 1
        txtCantDoy.setPrefWidth(50); // Pequeño para números

        panelDoy.getChildren().addAll(new Label("Doy:"), txtCantDoy, cmbRecursoDoy);
        // Estilo de labels
        panelDoy.getChildren().forEach(n -> { if(n instanceof Label) n.setStyle("-fx-text-fill: white;"); });


        // LO QUE YO PIDO (Recurso + Cantidad) ---
        HBox panelPido = new HBox(10);
        panelPido.setAlignment(Pos.CENTER);

        ComboBox<String> cmbRecursoPido = new ComboBox<>();
        cmbRecursoPido.getItems().addAll("Madera", "Ladrillo", "Lana", "Grano", "Mineral");
        cmbRecursoPido.setPromptText("Pido...");

        TextField txtCantPido = new TextField("1");
        txtCantPido.setPrefWidth(50);

        panelPido.getChildren().addAll(new Label("Por:"), txtCantPido, cmbRecursoPido);
        panelPido.getChildren().forEach(n -> { if(n instanceof Label) n.setStyle("-fx-text-fill: white;"); });


        HBox panelBotones = new HBox(15);
        panelBotones.setAlignment(Pos.CENTER);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnCancelar.setOnAction(e -> this.close());

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // CONECTAMOS EL CONTROLADOR ESPECÍFICO
        ControladorProponerTrato controlador = new ControladorProponerTrato(this,
                cmbJugadores,
                cmbRecursoDoy, txtCantDoy,
                cmbRecursoPido, txtCantPido
        );
        btnConfirmar.setOnAction(controlador);

        panelBotones.getChildren().addAll(btnCancelar, btnConfirmar);

        layout.getChildren().addAll(titulo, cmbJugadores, panelDoy, panelPido, panelBotones);

        Scene scene = new Scene(layout, 400, 350);
        this.setScene(scene);
    }

    private void configurarFondo(Region region) {
        try {
            java.net.URL url = getClass().getResource(IMAGEN);

            if (url == null) {
                System.out.println("BUSCADO EN: " + IMAGEN);
                System.out.println("--------------------------------------------------");

                region.setStyle(
                        "-fx-background-color: #2B3A55;" +
                                "-fx-border-color: #dabf11;" +
                                "-fx-border-width: 6;"
                );
                return;
            }

            Image imagen = new Image(url.toExternalForm());

            BackgroundImage fondoImagen = new BackgroundImage(
                    imagen,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );

            region.setBackground(new Background(fondoImagen));

        } catch (Exception e) {
            System.out.println("Excepción cargando imagen: " + e.getMessage());
            region.setStyle(
                    "-fx-background-color: #2B3A55;" +
                            "-fx-border-color: #dabf11;" +
                            "-fx-border-width: 6;"
            );
        }
    }

}