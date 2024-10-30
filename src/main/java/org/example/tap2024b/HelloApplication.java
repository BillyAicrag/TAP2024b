package org.example.tap2024b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.tap2024b.components.CorredorThread;
import org.example.tap2024b.models.Conexion;
import org.example.tap2024b.vistas.*;

import java.io.IOException;

public class HelloApplication extends Application {

    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1,menCompetencia2,menSalir;
    private MenuItem mitCalculadora, mitLoteria, mitLotery, mitSpotify, mitPista, mitBuscaminas;

    public void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());
        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());
        mitLotery = new MenuItem("Lotery");
        mitLotery.setOnAction(event -> new Lotery());
        mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(event -> new ListaClientes());
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria, mitLotery, mitSpotify);

        mitBuscaminas = new MenuItem("Buscaminas");
        mitBuscaminas.setOnAction(event -> new Buscaminas());
        mitPista = new MenuItem("Pista");
        mitPista.setOnAction(event -> new Pista());
        menCompetencia2 = new Menu("Competencia 2");
        menCompetencia2.getItems().addAll(mitPista, mitBuscaminas);
        mnbPrincipal = new MenuBar(menCompetencia1, menCompetencia2);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 320, 240);
        //scene.getStylesheets().add(getClass().getResource("/styles/main.css").toString());
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        Conexion.crearConeccion();
    }

    public static void main(String[] args) {
        launch();
    }
}