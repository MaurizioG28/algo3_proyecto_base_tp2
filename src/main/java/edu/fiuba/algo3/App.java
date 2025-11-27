package edu.fiuba.algo3;

import edu.fiuba.algo3.controllers.ControladorTeclaPresionada;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.vistas.VistaInicial;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(stage);

        VistaInicial vistaInicial = new VistaInicial(stage, pantallaPrincipal);
        pantallaPrincipal.setCentro(vistaInicial);
        Scene sceneInicio = new Scene(pantallaPrincipal, 1280, 720);
        sceneInicio.setOnKeyPressed(new ControladorTeclaPresionada(stage, pantallaPrincipal.getBarraDeMenu()));
        Image icono = new Image("file:" + System.getProperty("user.dir") + "edu/fiuba/algo3/resources/pantalla-inicial-fondo.png");
        stage.getIcons().add(icono);
        stage.setScene(sceneInicio);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}