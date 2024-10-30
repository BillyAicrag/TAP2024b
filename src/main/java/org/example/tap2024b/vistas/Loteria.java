package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loteria extends Stage {
    Todo ob1;
    Tiempo ob2;

    public Loteria(){
        iniciar();
    }

    private void iniciar(){
        ob1 = new Todo();
        ob2 = new Tiempo(ob1);
        ob1.setOb(ob2);
    }
}





class Tiempo extends Thread{
    private int carta = -1,minutosI = 0,minutosD = 0, segundosI = 0, segundosD = 0;
    private List<Integer> listaCartas;
    private String[] arImages;
    private ImageView imvCarta, minI, minD, segI, segD;
    Todo ob;

    public Tiempo(Todo ob){
        this.ob = ob;
    }

    @Override
    public void run() {
        iniciar();
    }

    public void iniciar(){
        carta = ob.getCarta();
        listaCartas = ob.getListaCartas();
        arImages = ob.getArImages();
        imvCarta = ob.getImvCarta();
        minI = ob.getMinI();
        minD = ob.getMinD();
        segI = ob.getSegI();
        segD = ob.getSegD();
        for (int i = 0; i < 41; i++) {
            mostrarCartas();
            esperar();
        }
    }

    private void esperar(){
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cambiarTiempo();
        }
    }

    private void cambiarTiempo(){
        segundosD++;
        if (segundosD == 10){
            segundosD = 0;
            segundosI++;
            if (segundosI == 6){
                segundosI = 0;
                minutosD++;
                if (minutosD == 10){
                    minutosD = 0;
                    minutosI++;
                }
            }
        }
        minD.setImage(new Image(getClass().getResource("/images/num"+ minutosD +".png").toString()));
        minI.setImage(new Image(getClass().getResource("/images/num"+ minutosI +".png").toString()));
        segD.setImage(new Image(getClass().getResource("/images/num"+ segundosD +".png").toString()));
        segI.setImage(new Image(getClass().getResource("/images/num"+ segundosI +".png").toString()));
    }

    public void mostrarCartas(){
        Button btn = new Button();
        carta++;
        if (carta < listaCartas.size()) {
            imvCarta.setImage(new Image(getClass().getResource("/images/lotes/" + arImages[listaCartas.get(carta)]).toString()));
            ob.setIdCarta( "" + listaCartas.get(carta));
        } else {
            imvCarta.setImage(new Image(getClass().getResource("/images/lotes/mystery.png").toString()));
            ob.setIdCarta("-1");
            alerta(ob.getPuntos());
        }
    }

    private void alerta(int opc){
        if (opc < 16) {
            imvCarta.setImage(new Image(getClass().getResource("/images/failure.png").toString()));
        }
    }

    //Sección de getters

    public int getMinutosI() {
        return minutosI;
    }

    public int getMinutosD() {
        return minutosD;
    }

    public int getSegundosI() {
        return segundosI;
    }

    public int getSegundosD() {
        return segundosD;
    }
}

class Todo extends Stage{
    private HBox hbxMain, hbxButtons;
    private VBox vbxIzquierda,vbxTablilla,vbxDerecha,vbxCarta;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer, lblMarcador;
    private GridPane[] gdpTablilla = new GridPane[5];
    private Scene escena;
    private String[] arImages = {"baymax.png","controller.png","creed.png","ds.png","gameboy.png","ghost.png","invader.png",
            "mario.png","pacman.png","pig.png","pikachu.png","pokeball.png","snes.png","sword.png","xbox.png","zelda.png",
            "castle.png","chest.png","coin.png","creeper.png","cruzeta.png","cs.png","ea.png","fortnite.png","half.png",
            "heart.png","link.png","logro.png","n64.png","over.png","play.png","potion.png","psp.png","rocket.png","skull.png",
            "steam.png","sus.png","switch.png","take.png","unreal.png"};
    private int tablero = 0, carta = -1, puntos = 0;
    private Panel pnlPrincipal;
    private boolean removido = false;

    //Nuevos
    private int contador = 0;
    private String idCarta = "";
    private List<Integer> listaCartas;
    private Tiempo ob;
    private ImageView imvAnt, imvSig, imvCarta, imvInicio;

    //Marcar el tiempo
    private HBox hbxTiempo;
    private ImageView minI, minD,medio, segI, segD;

    public Todo(){
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        barajarCartas();
        crearTiempo();
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
            crearTablilla(i);
        }

        btnAnterior = new Button();
        btnAnterior.setOnAction(event -> cambiarTabla(2));
        btnAnterior.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setOnAction(event -> cambiarTabla(1));
        btnSiguiente.setGraphic(imvSig);

        vbxTablilla = new VBox(gdpTablilla[0]);
        // Dar estilo a la VBox
        vbxTablilla.getStyleClass().add("vbox");
        hbxButtons = new HBox(btnAnterior,btnSiguiente);
        hbxButtons.setAlignment(Pos.CENTER);
        lblMarcador = new Label("Puntos: " + puntos);
        vbxIzquierda = new VBox(lblMarcador,vbxTablilla,hbxButtons);
        vbxIzquierda.setAlignment(Pos.CENTER);

        //Boton inicial partida--------------------------------------------------------------------------
        //lblTimer = new Label("00:00");
        btnIniciar = new Button();
        btnIniciar.setGraphic(imvInicio);
        btnIniciar.setOnAction(event -> iniciarPartida(btnIniciar));

        vbxCarta = new VBox(imvCarta);
        // Dar estilo a la VBox
        vbxCarta.getStyleClass().add("vbox");
        //vbxDerecha = new VBox(hbxTiempo,lblTimer,vbxCarta,btnIniciar);
        vbxDerecha = new VBox(hbxTiempo,vbxCarta,btnIniciar);
        vbxDerecha.setAlignment(Pos.CENTER);

        hbxMain = new HBox(vbxIzquierda, vbxDerecha);
        hbxMain.setAlignment(Pos.CENTER);
        hbxMain.setSpacing(20);
        hbxMain.setPadding(new Insets(20));

        pnlPrincipal = new Panel("Loteria Mexicana");
        pnlPrincipal.getStyleClass().add("panel-success");
        pnlPrincipal.setBody(hbxMain);

        escena = new Scene(pnlPrincipal,470,450);

        escena.getStylesheets().add(getClass().getResource("/styles/loteria.css").toExternalForm());
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void iniciarPartida(Button btn) {
        elegirTablilla();
        //ob.iniciar();
        ob.start();
        btn.setDisable(true);
    }

    private void crearTablilla(int con) {
        gdpTablilla[con] = new GridPane();
        List<Integer> listaNum = new ArrayList<>();
        Button[][] arBtn = new Button[4][4];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                ImageView imv = new ImageView(new Image(getClass().getResource("/images/lotes/" + arImages[contador]).toString()));
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                arBtn[i][j] = new Button();
                arBtn[i][j].setGraphic(imv);
                arBtn[i][j].setId("" + contador);
                final int finali = i, finalj = j;
                arBtn[i][j].setOnAction(event -> comprobarCarta(arBtn[finali][finalj]));
                listaNum.add(contador);
                contador++;
                gdpTablilla[con].add(arBtn[i][j],i,j);
            }
        }
        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int n = comprobar(listaNum);
                ImageView imv = new ImageView(new Image(getClass().getResource("/images/lotes/" + arImages[n]).toString()));
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                arBtn[i][j] = new Button();
                arBtn[i][j].setGraphic(imv);
                arBtn[i][j].setId("" + n);
                final int finali = i, finalj = j;
                arBtn[i][j].setOnAction(event -> comprobarCarta(arBtn[finali][finalj]));
                listaNum.add(n);
                gdpTablilla[con].add(arBtn[i][j],i,j);
            }
        }
    }

    private void comprobarCarta(Button btn) {
        lblMarcador.setText("Puntos: " + puntos);
        if (btn.getId().equals(idCarta)) {
            btn.setId("button");
            btn.setDisable(true);
            comprobarPuntos(1, btn);
        }
    }

    public void comprobarPuntos(int x, Button btn) {
        if (x == 1) {
            puntos++;
            if (puntos >= 16) {
                lblMarcador.setText("¡¡¡LOTERÍA!!!");
                ImageView imv = new ImageView(new Image(getClass().getResource("/images/victoria.png").toString()));
                vbxCarta.getChildren().clear();
                vbxCarta.getChildren().add(imv);
                tiempoFinal();
                alerta(0);
            } else {
                lblMarcador.setText("Puntos: " + puntos);
            }
        } else {
            if (puntos < 16) {
                lblMarcador.setText("¡Perdiste!\nPuntuación total: " + puntos);
                //alerta(1);
            }
        }
    }

    private int comprobar(List<Integer> list) {
        boolean flag = true;
        Random random = new Random();
        int n = random.nextInt(40);
        while (flag) {
            flag = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == n) {
                    flag = true;
                    n = random.nextInt(40);
                }
            }
        }
        return n;
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
        vbxTablilla.getChildren().clear();
        vbxTablilla.getChildren().add(gdpTablilla[tablero]);
    }

    private void barajarCartas() {
        listaCartas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            listaCartas.add(i);
        }
        Collections.shuffle(listaCartas);
    }

    public void mostrarCartas(){
        ImageView imv;
        Button btn = new Button();
        carta++;
        if (carta < listaCartas.size()) {
            imv = new ImageView(new Image(getClass().getResource("/images/lotes/" + arImages[listaCartas.get(carta)]).toString()));
            vbxCarta.getChildren().clear();
            vbxCarta.getChildren().add(imv);
            idCarta = "" + listaCartas.get(carta);
        } else {
            imv = new ImageView(new Image(getClass().getResource("/images/lotes/mystery.png").toString()));
            vbxCarta.getChildren().clear();
            vbxCarta.getChildren().add(imv);
            idCarta = "-1";
            comprobarPuntos(2, btn);
        }
    }

    private void elegirTablilla(){
        if(!removido) {
            vbxIzquierda.getChildren().remove(vbxIzquierda.getChildren().size() - 1);
            removido = true;
        }
    }

    private void alerta(int opc){
        String msj;
        Alert.AlertType type;
        if (opc == 0) {
            msj = "¡¡¡LOTERIA!!!";
            type = Alert.AlertType.INFORMATION;
        } else {
            msj = "¡PERDISTE!";
            type = Alert.AlertType.INFORMATION;
        }
        Alert alerta = new Alert(type);
        alerta.setTitle("Informacion del juego");
        alerta.setContentText(msj);
        alerta.showAndWait();
    }

    private void crearTiempo(){
        minI = new ImageView(new Image(getClass().getResource("/images/num0.png").toString()));
        minI.setFitHeight(14);
        minI.setFitWidth(14);
        minD = new ImageView(new Image(getClass().getResource("/images/num0.png").toString()));
        minD.setFitHeight(14);
        minD.setFitWidth(14);
        medio = new ImageView(new Image(getClass().getResource("/images/numMedio.png").toString()));
        medio.setFitHeight(14);
        medio.setFitWidth(14);
        segI = new ImageView(new Image(getClass().getResource("/images/num0.png").toString()));
        segI.setFitHeight(14);
        segI.setFitWidth(14);
        segD = new ImageView(new Image(getClass().getResource("/images/num0.png").toString()));
        segD.setFitHeight(14);
        segD.setFitWidth(14);
        hbxTiempo = new HBox(minI,minD,medio,segI,segD);
        hbxTiempo.setAlignment(Pos.CENTER);
        hbxTiempo.getStyleClass().add("hbox");
    }

    private void tiempoFinal(){
        ImageView minI = new ImageView(new Image(getClass().getResource("/images/num" + ob.getMinutosI() + ".png").toString()));
        minI.setFitHeight(14);
        minI.setFitWidth(14);
        ImageView minD = new ImageView(new Image(getClass().getResource("/images/num" + ob.getMinutosD() + ".png").toString()));
        minD.setFitHeight(14);
        minD.setFitWidth(14);
        ImageView segI = new ImageView(new Image(getClass().getResource("/images/num" + ob.getSegundosI() + ".png").toString()));
        segI.setFitHeight(14);
        segI.setFitWidth(14);
        ImageView segD = new ImageView(new Image(getClass().getResource("/images/num" + ob.getSegundosD() + ".png").toString()));
        segD.setFitHeight(14);
        segD.setFitWidth(14);
        hbxTiempo.getChildren().clear();
        hbxTiempo.getChildren().addAll(minI,minD,medio,segI,segD);
    }

    //Sección de Getters

    public void setOb(Tiempo ob) {
        this.ob = ob;
    }

    public int getCarta() {
        return carta;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setIdCarta(String idCarta) {
        this.idCarta = idCarta;
    }

    public String[] getArImages() {
        return arImages;
    }

    public List<Integer> getListaCartas() {
        return listaCartas;
    }

    public ImageView getImvCarta() {
        return imvCarta;
    }

    public ImageView getMinI() {
        return minI;
    }

    public ImageView getMinD() {
        return minD;
    }

    public ImageView getSegI() {
        return segI;
    }

    public ImageView getSegD() {
        return segD;
    }
}