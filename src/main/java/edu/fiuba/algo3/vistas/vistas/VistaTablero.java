package edu.fiuba.algo3.vistas.vistas;

import edu.fiuba.algo3.Estilos;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VistaTablero extends StackPane {
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;

    public VistaTablero(Stage stage, PantallaPrincipal pantallaPrincipal) {

        HBox layout = topLayuot();
        super.getChildren().addAll(layout);
        super.setBackground(new Background(new BackgroundFill(Color.web("#233850"), null, null)));
    }

    private HBox topLayuot (){
        HBox layout = new HBox();
        HBox mapa = agregarTerrenos();
        VBox jugadores = informacionJugadores();

        layout.getChildren().addAll(mapa, jugadores);

        return layout;
    }


    private HBox agregarTerrenos(){
        HBox mapa = new HBox();
        mapa.setPadding(new Insets(40, 80, 40 ,80));
        Catan catan = new Catan();
        Tablero tablero = catan.crearTablero();
        Map<Integer, Terreno> terrenos = tablero.getTerrenos();

        double centerY =  180.0;
        double centerX =  320.0;

        Group root = new Group();

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

        mapa.getChildren().add(root);

        return mapa;
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
        Image img = new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/imagenes/"+nombreImg+".jpg");
        hexagon.setFill(new ImagePattern(img));
        hexagon.setStroke(Color.BLACK);
        return hexagon;
    }

    private VBox informacionJugadores(){
        List<Jugador> jugadores = new ArrayList<Jugador>();
        jugadores.add(new Jugador("Jugador1", new edu.fiuba.algo3.modelo.Color("#aa7785")));
        jugadores.add(new Jugador("Jugador2", new edu.fiuba.algo3.modelo.Color("#7275bb")));
        jugadores.add(new Jugador("Jugador3", new edu.fiuba.algo3.modelo.Color("78dd74")));

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(40));
        vBox.setPrefWidth(300);
        vBox.setAlignment(Pos.TOP_RIGHT);

        jugadores.forEach(jugador -> {

            HBox infoJugador = agregarJugador(jugador);
            HBox separador = new HBox();
            separador.setPrefHeight(20);
            vBox.getChildren().addAll(infoJugador,separador);

        });


        return vBox;
    }

    private HBox agregarJugador(Jugador modeloJugador){
        HBox jugador = new HBox();
        jugador.setPadding(new Insets(20));
        jugador.setBackground(new Background(new BackgroundFill(Color.web(modeloJugador.getColor().getColor()), null, null)));

        Label nombreJugador = new Label(modeloJugador.getNombre());
        nombreJugador.setPrefWidth(105);
        nombreJugador.setPrefHeight(40);
        nombreJugador.setWrapText(true);
        nombreJugador.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 14));
        nombreJugador.setPadding(new Insets(0, 35, 0, 0));

        VBox puntosVictoria = new VBox();
        Label pvLabel = new Label("Puntos de Victoria");
        pvLabel.setPrefWidth(70);
        pvLabel.setPrefHeight(40);
        pvLabel.setWrapText(true);
        pvLabel.setFont(Font.font(Estilos.FUENTE, FontWeight.BOLD, 14));
        pvLabel.setTextAlignment(TextAlignment.CENTER);
        Label pvValue = new  Label("0");
        pvValue.setPrefWidth(70);
        pvValue.setFont(Font.font(Estilos.FUENTE, FontWeight.MEDIUM, 18));
        pvValue.setTextAlignment(TextAlignment.CENTER);
        pvValue.setPadding(new Insets(0, 0, 0, 35));
        puntosVictoria.getChildren().addAll(pvLabel,pvValue);

//        VBox recursosOcultos = new VBox();
//        VBox imgRecursos = new VBox();
//        imgRecursos.setPrefWidth(40);
//        imgRecursos.setPrefHeight(80);
//        imgRecursos.setBackground(new Background(new BackgroundFill(Color.web("#aa4445"), null, null)));
//        Label cantidadrecursos = new Label("0");
//        recursosOcultos.getChildren().addAll(imgRecursos, cantidadrecursos);
//        VBox cartasDesarrolo = new VBox();
//        VBox imgCarta = new VBox();
//        imgCarta.setPrefWidth(20);
//        imgCarta.setPrefHeight(40);
//        imgCarta.setBackground(new Background(new BackgroundImage(
//                new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/imagenes/cartasDesarrolloOcultas.jpg"),
//                BackgroundRepeat.ROUND,
//                BackgroundRepeat.SPACE,
//                BackgroundPosition.CENTER,
//                new BackgroundSize(20, 40, true, true, true, false)
//        )));
//        Label cantidadCartas = new Label("0");
//        cartasDesarrolo.getChildren().addAll(imgCarta, cantidadCartas);

        jugador.getChildren().addAll(nombreJugador, puntosVictoria);

        return jugador;
    }

}
