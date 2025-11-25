package edu.fiuba.algo3.vistas.vistas;


import edu.fiuba.algo3.vistas.PantallaPrincipal;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VistaInicial extends StackPane {

    private Stage stage;

    public VistaInicial(Stage stagePrincipal, PantallaPrincipal contenedorPrincipal) {
        this.stage = stagePrincipal;

        Image imagen = new Image("file:" + System.getProperty("user.dir") + "/src/main/java/edu/fiuba/algo3/resources/catan-game-pantalla.png");
        BackgroundImage fondoImagen = new BackgroundImage(imagen,
                BackgroundRepeat.ROUND,
                BackgroundRepeat.SPACE,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false));

        Background fondo = new Background(fondoImagen);
        super.setBackground(fondo);

    }
}
