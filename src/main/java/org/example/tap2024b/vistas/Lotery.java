package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lotery extends Stage {

    private HBox hbxMain, hbxButtons;
    private VBox vbxTablilla,vbxMostrar,vbxCartas,vbxRevelar;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer, lblMarcador;
    private GridPane[] gdpTablilla = new GridPane[5];
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"baymax.png","controller.png","creed.png","ds.png","gameboy.png","ghost.png","invader.png",
            "mario.png","pacman.png","pig.png","pikachu.png","pokeball.png","snes.png","sword.png","xbox.png","zelda.png",
            "castle.png","chest.png","coin.png","creeper.png","cruzeta.png","cs.png","ea.png","fortnite.png","half.png",
            "heart.png","link.png","logro.png","n64.png","over.png","play.png","potion.png","psp.png","rocket.png","skull.png",
            "steam.png","sus.png","switch.png","take.png","unreal.png"};
    private int[] arTotal = new int[40];
    private Button[][] arBtnTab;
    private int[][] arUsados = new int[5][16];
    private int[]  arRelleno = {0,0,0,0,0};
    private int rellenado = 0, tablero = 0, carta = -1, puntos = 0;
    private Panel pnlPrincipal;
    private boolean removido= false;
    private int minutos = 0, segundos = 0;



    public Lotery(){
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        barajar();
        ImageView imvAnt, imvSig, imvCarta, imvInicio;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/Back.png").toString()));
        imvAnt.setFitWidth(60);
        imvAnt.setFitHeight(60);
        imvSig = new ImageView(new Image(getClass().getResource("/images/Forward.png").toString()));
        imvSig.setFitWidth(60);
        imvSig.setFitHeight(60);

        imvInicio = new ImageView(new Image(getClass().getResource("/images/lotes/start.png").toString()));
        imvInicio.setFitWidth(60);
        imvInicio.setFitHeight(60);

        imvCarta = new ImageView(new Image(getClass().getResource("/images/lotes/mystery.png").toString()));

        for (int i = 0; i < 5; i++) {
            CrearTablilla(i);
        }

        btnAnterior = new Button();
        btnAnterior.setOnAction(event -> cambiarTabla(2));
        btnAnterior.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setOnAction(event -> cambiarTabla(1));
        btnSiguiente.setGraphic(imvSig);

        vbxMostrar = new VBox(gdpTablilla[0]);
        hbxButtons = new HBox(btnAnterior,btnSiguiente);
        hbxButtons.setAlignment(Pos.CENTER);
        lblMarcador = new Label("Puntos: " + puntos);
        vbxTablilla = new VBox(lblMarcador,vbxMostrar,hbxButtons);
        vbxTablilla.setAlignment(Pos.CENTER);

        // Dar estilo a la VBox
        vbxMostrar.getStyleClass().add("vbox");

        //Boton inicial partida--------------------------------------------------------------------------
        lblTimer = new Label("00:00");
        btnIniciar = new Button();
        //btnIniciar.setOnAction(event -> mostrar());
        btnIniciar.setOnAction(event -> iniciarPartida());
        //Boton inicial partida--------------------------------------------------------------------------

        btnIniciar.getStyleClass().setAll("btn","btn-danger");
        btnIniciar.setGraphic(imvInicio);

        vbxRevelar = new VBox(imvCarta);
        vbxCartas = new VBox(lblTimer,vbxRevelar,btnIniciar);
        vbxCartas.setAlignment(Pos.CENTER);

        // Dar estilo a la VBox
        vbxRevelar.getStyleClass().add("vbox");

        hbxMain = new HBox(vbxTablilla,vbxCartas);
        hbxMain.setAlignment(Pos.CENTER);
        hbxMain.setSpacing(20);
        hbxMain.setPadding(new Insets(20));

        pnlPrincipal = new Panel("Loteria Mexicana");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setBody(hbxMain);

        escena = new Scene(pnlPrincipal,470,450);

        escena.getStylesheets().add(getClass().getResource("/styles/lotery.css").toExternalForm());
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTablilla(int con) {
        gdpTablilla[con] = new GridPane();
        int sig = 0, espacio;
        arBtnTab  = new Button[4][4];
        Image img;
        ImageView imv;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (sig < 8){
                    sig++;
                    espacio = rellenado;
                    sumarUsos(con);
                    rellenado++;
                } else {
                    espacio = rellenarButton(con);
                }
                img = new Image(getClass().getResource("/images/lotes/" + arImages[espacio]).toString());
                imv = new ImageView(img);
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                arBtnTab[j][i] = new Button();
                arBtnTab[j][i].setGraphic(imv);
                arBtnTab[j][i].setId("" + espacio);
                int finali = i, finalj = j;
                String str = "" + espacio;
                arBtnTab[j][i].setOnAction(event -> apretarBotton(str, finalj, finali));

                gdpTablilla[con].add(arBtnTab[j][i],j,i);
            }
        }
    }

    private int rellenarButton(int con) {
        boolean flag = true;
        int n = ((int) (Math.random() * 40));
        while (flag) {
            flag = false;
            for (int i = 0; i < arRelleno[con]; i++) {
                if (arUsados[con][i] == n) {
                    flag = true;
                    n = ((int) (Math.random() * 40));
                }
            }
        }
        arUsados[con][arRelleno[con]] = n;
        arRelleno[con]++;
        return n;
    }

    private void sumarUsos(int con){
        arUsados[con][arRelleno[con]] = rellenado;
        arRelleno[con]++;
    }

    private void cambiarTabla(int n) {
        if(n == 1)
            tablero++;
        else
            tablero--;
        if(tablero > 4)
            tablero = 0;
        if (tablero < 0)
            tablero = 4;
        vbxMostrar.getChildren().clear();
        vbxMostrar.getChildren().add(gdpTablilla[tablero]);
    }

    private void barajar() {
        List<Integer> listaNumeros = new ArrayList<>();
        for (int i = 0; i < arTotal.length; i++) {
            listaNumeros.add(i);
        }
        Collections.shuffle(listaNumeros);

        for (int i = 0; i < arTotal.length; i++) {
            arTotal[i] = listaNumeros.get(i);
        }
    }

    private void mostrar(){
        bloquearTablilla();
        ImageView imv;
        carta++;
        if (carta < arImages.length) {
            imv = new ImageView(new Image(getClass().getResource("/images/lotes/" + arImages[arTotal[carta]]).toString()));
            vbxRevelar.getChildren().clear();
            vbxRevelar.getChildren().add(imv);
        } else {
            imv = new ImageView(new Image(getClass().getResource("/images/lotes/mystery.png").toString()));
            vbxRevelar.getChildren().clear();
            vbxRevelar.getChildren().add(imv);
            puntuacion(2);
        }
    }

    private void iniciarPartida() {
        for (int i = 0; i < 3; i++) {
            mostrar();
            esperar();
        }
    }

    private void esperar(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1 * 1000);
                //rootplatform thread
                cambiarTiempo();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarTiempo(){
        String min, seg;
        segundos++;
        if (segundos == 60){
            segundos = 0;
            minutos++;
        }
        min = "" + minutos;
        seg = "" + segundos;
        if (minutos < 10)
            min = "0" + minutos;

        if (segundos < 10)
            seg = "0" + segundos;
        lblTimer.setText(min + ":" + seg);
    }

    private void apretarBotton(String str, int j, int i) {
        if (carta >= 0) {
            if (str.equals("" + arTotal[carta])) {
                for (var node : gdpTablilla[tablero].getChildren()) {
                    // Verificar si el nodo está en la columna 4 y fila 3
                    Integer columnIndex = GridPane.getColumnIndex(node);
                    Integer rowIndex = GridPane.getRowIndex(node);

                    if (columnIndex != null && rowIndex != null && columnIndex == j && rowIndex == i) {
                        // Cambiar el ID del nodo
                        node.setId("button");
                        // Aumentar la puntuacion
                        puntuacion(1);
                        break;
                    }
                }
            }
        }
    }

    private void puntuacion(int x) {
        if (x == 1) {
            puntos++;
            if (puntos >= 16) {
                lblMarcador.setText("¡¡LOTERÍA!!");
            } else {
                lblMarcador.setText("Puntos: " + puntos);
            }
        } else {
            if (puntos < 16) {
                lblMarcador.setText("¡¡Perdiste!!\nPuntuación total: " + puntos);
            }
        }
    }

    private void bloquearTablilla(){
        if(!removido) {
            vbxTablilla.getChildren().remove(vbxTablilla.getChildren().size() - 1);
            removido = true;
        }
    }

}
