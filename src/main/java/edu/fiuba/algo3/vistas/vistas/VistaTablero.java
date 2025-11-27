package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class VistaTablero extends StackPane {
    private static final double ANCHO_VENTANA = 640;
    private static final double ALTO_VENTANA = 360;

    public VistaTablero(Stage stage, PantallaPrincipal pantallaPrincipal) {

        Catan catan = new Catan();
        Tablero tablero = catan.crearTablero();
        Map<Integer, Terreno> terrenos = tablero.getTerrenos();

        //Creating a Polygon
        double centerY =  180.0;
        double centerX =  320.0;

        Group root = new Group();
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA, Color.web("#4395ab"));

        double hexRadius = 40;

        Polygon center = createHexagon(centerX, centerY, hexRadius, terrenos.get(1));
        root.getChildren().add(center);
        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * i / 6;
            double x = centerX + hexRadius * 1.8 * Math.cos(angle);
            double y = centerY + hexRadius * 1.8 * Math.sin(angle);
            Polygon hexagon = createHexagon(x, y, hexRadius, terrenos.get(i+1));
            root.getChildren().add(hexagon);
        }
        for (int i = 0; i < 12; i++) {
            double angle = 2.0 * Math.PI * i / 12;
            double x = centerX + hexRadius * 3.6 * Math.cos(angle);
            double y = centerY + hexRadius * 3.6 * Math.sin(angle);
            Polygon hexagon = createHexagon(x, y, hexRadius,  terrenos.get(i+7));
            root.getChildren().add(hexagon);
        }
        Translate moverAbajo = new Translate();
        moverAbajo.setY(20);
        Translate moverAbajoDerecha = new Translate();
        moverAbajoDerecha.setY(10);
        moverAbajoDerecha.setX(17);
        Translate moverAbajoIzquierda = new Translate();
        moverAbajoIzquierda.setY(10);
        moverAbajoIzquierda.setX(-17);
        Translate moverArriba = new Translate();
        moverArriba.setY(-20);
        Translate moverArribaDerecha = new Translate();
        moverArribaDerecha.setY(-10);
        moverArribaDerecha.setX(17);
        Translate moverArribaIzquierda = new Translate();
        moverArribaIzquierda.setY(-10);
        moverArribaIzquierda.setX(-17);

        root.getChildren().get(8).getTransforms().add(moverArribaIzquierda);
        root.getChildren().get(10).getTransforms().add(moverArriba);
        root.getChildren().get(12).getTransforms().add(moverArribaDerecha);
        root.getChildren().get(14).getTransforms().add(moverAbajoDerecha);
        root.getChildren().get(16).getTransforms().add(moverAbajo);
        root.getChildren().get(18).getTransforms().add(moverAbajoIzquierda);

        stage.setScene(scene);
        stage.show();
    }

    private Polygon createHexagon(double x, double y, double radius, Terreno terreno) {
        System.out.println("x: "+ x + " y: "+ y);
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = (2.0 * Math.PI * i / 6) + (Math.PI / 6) ;
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPos, yPos);
        }
        String nombreImg = terreno.getTipoTerreno().toLowerCase();
        Image img = new Image("file:" + System.getProperty("user.dir") + "/src/main/java/edu/fiuba/algo3/resources/Imagenes/"+nombreImg+".jpg");
        hexagon.setFill(new ImagePattern(img));
        hexagon.setStroke(Color.BLACK);
        return hexagon;
    }

}
