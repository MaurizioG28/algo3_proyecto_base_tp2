package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Recursos.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VistaIntercambioConLaBanca extends Stage {
    private static final String IMAGEN = "/imagenes/banca.png";

    public VistaIntercambioConLaBanca(Stage owner, Catan catan) {
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL); // Pausa la ventana principal
        this.initStyle(StageStyle.UNDECORATED); // Sin bordes de ventana

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
                "-fx-border-color: #dabf11;" +  // Color del borde
                        "-fx-border-width: 6;" +        // Grosor (6px se nota bien)
                        "-fx-border-radius: 0;"        // 0 para cuadrado, 10 para redondeado

        );


        configurarFondo(layout);

        Label titulo = new Label("COMERCIO CON BANCA");
        titulo.setStyle("-fx-text-fill: #dabf11; -fx-font-size: 18px; -fx-font-weight: bold;");

        // --- SECCIÓN: YO DOY ---
        Label lblDoy = new Label("Yo entrego:");
        lblDoy.setStyle("-fx-text-fill: #dabf11;");
        ComboBox<String> cmbDoy = new ComboBox<>();
        cmbDoy.getItems().addAll("Madera", "Ladrillo", "Lana", "Grano", "Mineral");
        cmbDoy.setPromptText("Recurso a dar");

        // --- SECCIÓN: YO RECIBO ---
        Label lblPido = new Label("A cambio de:");
        lblPido.setStyle("-fx-text-fill: #dabf11;");
        ComboBox<String> cmbPido = new ComboBox<>();
        cmbPido.getItems().addAll("Madera", "Ladrillo", "Lana", "Grano", "Mineral");
        cmbPido.setPromptText("Recurso a recibir");

        // --- BOTONES ---
        Button btnConfirmar = new Button("Intercambiar");
        btnConfirmar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold;");

        // ACCIONES
        btnCancelar.setOnAction(e -> this.close());

        btnConfirmar.setOnAction(e -> {
            String recursoDoy = cmbDoy.getValue();
            String recursoPido = cmbPido.getValue();

            if (recursoDoy != null && recursoPido != null) {
                try {
                    TipoDeRecurso recursoQueDoy = crearRecursoPorNombre(recursoDoy);
                    TipoDeRecurso recursoQuePido = crearRecursoPorNombre(recursoPido);

                    Catan.getInstance().getManagerTurno().intercambiarConBanca(recursoQueDoy, recursoQuePido);
                    System.out.println("Intercambio Banca: Doy " + recursoDoy + " -> Recibo " + recursoPido);
                    this.close();
                } catch (Exception ex) {
                    // Mostrar alerta de error si no tiene recursos suficientes
                    System.out.println("Error en intercambio: " + ex.getMessage());
                }
            }
        });

        layout.getChildren().addAll(titulo, lblDoy, cmbDoy, lblPido, cmbPido, btnConfirmar, btnCancelar);

        Scene scene = new Scene(layout, 300, 350);
        this.setScene(scene);
    }

    private void configurarFondo(Region region) {
        try {
            java.net.URL url = getClass().getResource(IMAGEN);

            if (url == null) {
                System.out.println("--------------------------------------------------");
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
                    new BackgroundSize(150, 150, true, true, false, true)
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
    private TipoDeRecurso crearRecursoPorNombre(String nombre) {
        switch (nombre.toLowerCase()) {
            case "madera": return new Madera(0);
            case "ladrillo": return new Ladrillo(0);
            case "lana": return new Lana(0);
            case "grano": return new Grano(0);
            case "mineral": return new Mineral(0);
            default: throw new IllegalArgumentException("Recurso desconocido");
        }
    }

}
