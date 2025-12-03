package edu.fiuba.algo3.vistas.vistas;

import com.sun.javafx.geom.Point2D;
import edu.fiuba.algo3.controllers.*;
import edu.fiuba.algo3.modelo.Catan;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Tablero.Dados;
import edu.fiuba.algo3.modelo.Tablero.Factory.Axial;
import edu.fiuba.algo3.modelo.Tablero.Factory.Coordenada;
import edu.fiuba.algo3.modelo.Tablero.Factory.Cubic;
import edu.fiuba.algo3.modelo.Tablero.Factory.Vertice;
import edu.fiuba.algo3.modelo.Tablero.Tablero;
import edu.fiuba.algo3.modelo.Tablero.Terrenos.Terreno;
import edu.fiuba.algo3.vistas.PantallaPrincipal;
import edu.fiuba.algo3.vistas.botones.BotonGenericoAccionUsuario;
import edu.fiuba.algo3.vistas.botones.BotonLanzarDados;
import edu.fiuba.algo3.vistas.botones.BotonTerminarTurno;
import edu.fiuba.algo3.vistas.botones.BotonVertice;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.*;

import static edu.fiuba.algo3.modelo.Tablero.Factory.TableroFactory.Vertice_OFFSETS;

public class VistaTablero2 extends BorderPane { // CAMBIO: Ahora extendemos BorderPane
    private static final double ANCHO_VENTANA = 1280;
    private static final double ALTO_VENTANA = 720;

    // Variable para controlar la selección de cartas
    private String cartaSeleccionada = null;
    private Catan catan;
    private HBox contenedorDadosVisuales;
    private Stage stage;
    private PantallaPrincipal pantallaPrincipal;
    private Map<Cubic, Circle> verticesUI;


    public VistaTablero2(Stage stage, PantallaPrincipal pantallaPrincipal) {

        this.setBackground(new Background(new BackgroundFill(Color.web("#233850"), null, null)));
        this.catan = Catan.getInstance();
        this.stage = stage;
        this.pantallaPrincipal = pantallaPrincipal;
        Group grupoTerrenos = agregarTerrenos();
        Group grupoVertices = agregarVertices(this.catan.getTablero().getTerrenos(), 50);
        StackPane contenedorMapa = new StackPane(grupoTerrenos, grupoVertices);
        contenedorMapa.setAlignment(Pos.CENTER);
        this.setCenter(contenedorMapa);

        // DERECHA: Jugadores + Dados + Fin Turno
        this.setRight(crearPanelDerecho());

        // ABAJO: Inventario + Acciones
        this.setBottom(crearPanelInferior());
    }

    private Group agregarTerrenos() {

        Tablero tablero = this.catan.crearTablero();
        Map<Integer, Terreno> terrenos = tablero.getTerrenos();

        double centerY = 0;
        double centerX = 0;

        Group root = new Group();
        double hexRadius = 50;

        for (Terreno t : terrenos.values()) {

            Axial pos = t.getPosicion();
            double q = pos.q;
            double r = pos.r;

            // axial → pixel
            double x = hexRadius * Math.sqrt(3) * (q + r / 2.0);
            double y = hexRadius * 1.5 * r;

            Polygon hexagon = createHexagon(x, y, hexRadius, t);
            root.getChildren().add(hexagon);
        }

        return root;
    }

    private Polygon createHexagon(double x, double y, double radius, Terreno terreno) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = (2.0 * Math.PI * i / 6) + (Math.PI / 6);
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPos, yPos);
        }

        Tooltip tooltip = new Tooltip("hex("+terreno.getPosicion().q + "," + terreno.getPosicion().r +")");
        String nombreImg = terreno.getTipoTerreno().toLowerCase();
        try {

            Image img = new Image("file:" + System.getProperty("user.dir") + "/src/main/resources/imagenes/" + nombreImg + ".jpg");
            hexagon.setFill(new ImagePattern(img));
        } catch (Exception e) {
            hexagon.setFill(Color.BROWN); // Color fallback si falla la imagen
        }
        hexagon.setStroke(Color.BLACK);
        Tooltip.install(hexagon, tooltip);
        return hexagon;
    }

    private Group agregarVertices(Map<Integer, Terreno> terrenos, double hexRadius) {

        Group root = new Group();
        //Set<Cubic> visitados = new HashSet<>();
        //Map<Cubic, Circle> verticesUI = new HashMap<>();
        //Map<Coordenada, Vertice> verticesModelo = this.catan.getTablero().getVertices();

        for (Terreno terreno : terrenos.values()) {

            Axial axial = terreno.getPosicion();

            // Centro del hexágono en píxeles
            double cx = hexRadius * Math.sqrt(3) * (axial.q + axial.r / 2.0);
            double cy = hexRadius * 1.5 * axial.r;

            // Obtener su centro cúbico
            Cubic centroCubic = axial.toCubic();

            for (int i = 0; i < 6; i++) {

                Cubic vCubic = centroCubic.add(Vertice_OFFSETS[i]);


//

                // --- COORDENADAS DE LA PUNTA DEL HEXÁGONO ---
                double angle = (Math.PI / 2) + i * (Math.PI / 3) + Math.PI;

                double px = cx + hexRadius * Math.cos(angle);
                double py = cy + hexRadius * Math.sin(angle);

                Tooltip tooltip = new Tooltip("vertice ("+terreno.getId()+","+i+")");

                BotonVertice btn = new BotonVertice(px, py, vCubic, terreno.getId(), i);
                Tooltip.install(btn, tooltip);
                new ControladorVertice(btn, catan.getManagerTurno());

                root.getChildren().add(btn);
            }
        }
        //this.verticesUI=verticesUI;
        return root;
    }





    private VBox crearPanelDerecho() {
        VBox panel = new VBox();
        panel.setPadding(new Insets(20));
        panel.setPrefWidth(300);
        panel.setAlignment(Pos.TOP_CENTER);

        // LISTA DE JUGADORES
        List<Jugador> jugadores = this.catan.getJugadores();
        for (Jugador j : jugadores) {
            HBox infoJugador = agregarJugador(j);
            HBox separador = new HBox(); separador.setPrefHeight(15);
            panel.getChildren().addAll(infoJugador, separador);
        }

        //ESPACIADOR
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        panel.getChildren().add(spacer);


        // Los Dados (Visuales)
        // Guardamos la referencia en la variable de clase para actualizarla luego
        this.contenedorDadosVisuales = new HBox(15);
        this.contenedorDadosVisuales.setAlignment(Pos.CENTER);
        actualizarDadosVisuales(1, 1);
        Dados dados = new Dados();

        ControladorLanzarDados controladorLanzar = new ControladorLanzarDados(dados, this);
        BotonLanzarDados btnLanzar = new BotonLanzarDados(controladorLanzar);


        ControladorTerminarTurno controladorTerminarTurno = new ControladorTerminarTurno(btnLanzar );
        BotonTerminarTurno btnTerminar = new BotonTerminarTurno(controladorTerminarTurno);

        btnTerminar.setDisable(true);

        controladorLanzar.setBoton(btnLanzar);
        controladorLanzar.setBotonTerminar(btnTerminar); // <--- Para habilitarlo

        // El controlador de terminar necesita conocer a su propio botón
        controladorTerminarTurno.setBotonTerminar(btnTerminar); //  Para deshabilitarlo

        // Contenedor de Botones
        HBox contenedorBotones = new HBox(15);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorBotones.getChildren().addAll(btnLanzar, btnTerminar);

        //Armar Zona Final
        VBox zonaControl = new VBox(20);
        zonaControl.setAlignment(Pos.CENTER);
        zonaControl.getChildren().addAll(this.contenedorDadosVisuales, contenedorBotones);

        panel.getChildren().add(zonaControl);

        return panel;
    }

    private HBox agregarJugador(Jugador jugador) {
        HBox jugadorBox = new HBox();
        jugadorBox.setPadding(new Insets(10));


        Color colorFondoJavaFX = Color.web(jugador.getColor().getColor());

        //  Aplicamos el fondo con esquinas redondeadas
        jugadorBox.setBackground(new Background(new BackgroundFill(colorFondoJavaFX, new CornerRadii(8), null)));

        //  borde sutil
        jugadorBox.setStyle("-fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 8; -fx-border-width: 1;");

        // Calculamos qué color de texto (blanco o negro) se ve mejor sobre ese fondo
        Color colorTexto = obtenerColorTextoContraste(colorFondoJavaFX);


        //Etiqueta del Nombre
        Label nombreJugador = new Label(jugador.getNombre());
        nombreJugador.setPrefWidth(110);
        nombreJugador.setWrapText(true);
        nombreJugador.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        // Aplicamos el color de contraste calculado
        nombreJugador.setTextFill(colorTexto);


        //Sección de Puntos de Victoria
        VBox puntosVictoria = new VBox(2); // Pequeño espacio vertical
        puntosVictoria.setAlignment(Pos.CENTER);

        Label pvLabel = new Label("Puntos de\nVictoria");
        pvLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
        pvLabel.setTextAlignment(TextAlignment.CENTER);
        pvLabel.setTextFill(colorTexto);

        String puntosReales = String.valueOf(jugador.totalPuntos());
        Label pvValue = new Label(puntosReales);
        pvValue.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        pvValue.setAlignment(Pos.CENTER);
        pvValue.setTextFill(colorTexto);

        puntosVictoria.getChildren().addAll(pvLabel, pvValue);
        jugadorBox.getChildren().addAll(nombreJugador, puntosVictoria);

        return jugadorBox;
    }

    private HBox crearPanelInferior() {
        HBox panel = new HBox(20);
        panel.setPadding(new Insets(15, 20, 15, 20));
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.setPrefHeight(180);

        //  CÁPSULA NEGRA DEL INVENTARIO
        HBox inventario = new HBox(15);
        inventario.setPrefWidth(600);
        inventario.setPadding(new Insets(10));
        inventario.setAlignment(Pos.CENTER_LEFT);
        // Estilo negro redondeado del boceto
        inventario.setStyle("-fx-background-color: #222; -fx-background-radius: 20; -fx-border-color: black; -fx-border-width: 4; -fx-border-radius: 20;");

        // Recursos
        VBox areaRecursos = new VBox(5);
        Label lblTituloRec = new Label("RECURSOS"); lblTituloRec.setTextFill(Color.WHITE);
        Label lblDatosRec = new Label("Madera: 0 | Ladrillo: 0 | Lana: 0| Mineral: 0 | Grano: 0"); // Aquí enlazarías con el modelo
        lblDatosRec.setTextFill(Color.LIGHTGREEN); lblDatosRec.setStyle("-fx-font-weight: bold;");

        areaRecursos.getChildren().addAll(lblTituloRec, lblDatosRec);
        areaRecursos.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-padding: 10; -fx-min-width: 200;");
        areaRecursos.setAlignment(Pos.CENTER);

        // Cartas Interactivas
        HBox areaCartas = new HBox(10);
        areaCartas.setAlignment(Pos.CENTER_LEFT);
        areaCartas.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-padding: 10;");

        // Agregamos cartas de ejemplo
        areaCartas.getChildren().add(crearCartaInteractiva("Caballero", Color.LIGHTGRAY));
        areaCartas.getChildren().add(crearCartaInteractiva("Monopolio", Color.LIGHTGREEN));
        areaCartas.getChildren().add(crearCartaInteractiva("Punto Vic.", Color.GOLD));

        inventario.getChildren().addAll(areaRecursos, areaCartas);

        //GRID DE BOTONES DE ACCIÓN (Rojos)
        GridPane acciones = new GridPane();
        acciones.setHgap(10);
        acciones.setVgap(10);
        acciones.setAlignment(Pos.CENTER_RIGHT);

        acciones.add(crearBotonAccion("Construir\nPoblado",new ControladorConstruirPoblado(this.catan)), 0, 0);
        acciones.add(crearBotonAccion("Construir\nCamino",new ControladorConstruirCamino(this.catan)), 1, 0);
        acciones.add(crearBotonAccion("Construir\nCiudad",new ControladorConstruirCiudad(this.catan)), 2, 0);

        acciones.add(crearBotonAccion("Banca",new ControladorBanca(this.catan,this)), 0, 1);
        acciones.add(crearBotonAccion("Intercambio",new ControladorIntercambioEntreJugadores(this.catan,this)), 1, 1);
        acciones.add(crearBotonAccion("JUGAR\nCARTA",new ControladorJugarCarta(this.catan,this)),2,1);


        // Espaciador para separar inventario de acciones
        Region espacioCentral = new Region();
        HBox.setHgrow(espacioCentral, Priority.ALWAYS);

        panel.getChildren().addAll(inventario, espacioCentral, acciones);
        return panel;
    }

    private VBox crearCartaInteractiva(String nombre, Color color) {
        VBox carta = new VBox();
        carta.setPrefSize(60, 80);
        carta.setStyle("-fx-background-color: " + toHex(color) + "; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 5;");
        carta.setAlignment(Pos.CENTER);

        Label lbl = new Label(nombre.substring(0, 3)); // Solo 3 letras
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        carta.getChildren().add(lbl);

        carta.setOnMouseClicked(e -> {
            this.cartaSeleccionada = nombre;
            // Limpiar selección de hermanos
            HBox padre = (HBox) carta.getParent();
            padre.getChildren().forEach(n -> n.setStyle(n.getStyle().replace("-fx-border-color: yellow;", "-fx-border-color: white;")));
            // Seleccionar actual
            carta.setStyle(carta.getStyle().replace("-fx-border-color: white;", "-fx-border-color: yellow;"));
        });
        return carta;
    }

    private BotonGenericoAccionUsuario crearBotonAccion(String texto, EventHandler<ActionEvent> event) {
        BotonGenericoAccionUsuario btn = new BotonGenericoAccionUsuario (event,texto);
        return btn;
    }

    private String toHex(Color c) {
        return String.format("#%02X%02X%02X", (int)(c.getRed()*255), (int)(c.getGreen()*255), (int)(c.getBlue()*255));
    }
    private Color obtenerColorTextoContraste(Color colorFondo) {

        double luminancia = 0.2126 * colorFondo.getRed() +
                0.7152 * colorFondo.getGreen() +
                0.0722 * colorFondo.getBlue();

        //  Si la luminancia es mayor a 0.5, el color es "claro" -> Texto NEGRO
        // Si es menor o igual, el color es "oscuro" -> Texto BLANCO
        // Usamos 0.55 punto medio
        return (luminancia > 0.55) ? Color.BLACK : Color.WHITE;
    }

    private StackPane crearDadoVisual(int valor) {
        //EL FONDO DEL DADO
        StackPane dado = new StackPane();
        dado.setPrefSize(60, 60); // Tamaño del dado

        javafx.scene.shape.Rectangle fondo = new javafx.scene.shape.Rectangle(60, 60);
        fondo.setArcWidth(15); // Redondez de esquinas
        fondo.setArcHeight(15);
        fondo.setFill(Color.web("#d9534f")); // Color ROJO (Igual a tus botones)
        fondo.setStroke(Color.WHITE); // Borde blanco
        fondo.setStrokeWidth(2);

        dado.getChildren().add(fondo);

        GridPane puntosGrid = new GridPane();
        puntosGrid.setAlignment(Pos.CENTER);
        puntosGrid.setHgap(5); // Espacio horizontal entre puntos
        puntosGrid.setVgap(5); // Espacio vertical entre puntos

        //posiciones lógicas de los puntos en una matriz 3x3
        // (columna, fila)
        // 0,0  1,0  2,0
        // 0,1  1,1  2,1
        // 0,2  1,2  2,2

        // PUNTOS SEGÚN EL NÚMERO
        // Centro (para 1, 3, 5)
        if (valor % 2 != 0) {
            puntosGrid.add(crearPunto(), 1, 1);
        }

        // Esquinas Superior-Izquierda e Inferior-Derecha (para 2, 3, 4, 5, 6)
        if (valor > 1) {
            puntosGrid.add(crearPunto(), 0, 0);
            puntosGrid.add(crearPunto(), 2, 2);
        }

        // Esquinas Superior-Derecha e Inferior-Izquierda (para 4, 5, 6)
        if (valor > 3) {
            puntosGrid.add(crearPunto(), 2, 0);
            puntosGrid.add(crearPunto(), 0, 2);
        }

        // Medio-Izquierda y Medio-Derecha (solo para 6)
        if (valor == 6) {
            puntosGrid.add(crearPunto(), 0, 1);
            puntosGrid.add(crearPunto(), 2, 1);
        }

        dado.getChildren().add(puntosGrid);
        return dado;
    }

    private javafx.scene.shape.Circle crearPunto() {
        javafx.scene.shape.Circle punto = new javafx.scene.shape.Circle(5); // Radio 5
        punto.setFill(Color.WHITE);
        return punto;
    }
    public void actualizarDadosVisuales(int valor1, int valor2) {
        this.contenedorDadosVisuales.getChildren().clear();

        StackPane d1 = crearDadoVisual(valor1);
        StackPane d2 = crearDadoVisual(valor2);

        this.contenedorDadosVisuales.getChildren().addAll(d1, d2);
    }

}
