package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lotery extends Stage {

    private HBox hbxMain, hbxButtons;
    private VBox vbxTablilla;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"baymax.png","controller.png","creed.png","ds.png","gameboy.png","ghost.png","invader.png","mario.png","pacman.png","pig.png","pikachu.png","pokeball.png","snes.png","sword.png","xbox.png","zelda.png"};
    private Button[][] arBtnTab;

    public Lotery(){
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/Back.png").toString()));
        imvSig = new ImageView(new Image(getClass().getResource("/images/Forward.png").toString()));

        gdpTablilla = new GridPane();
        CrearTablilla();

        btnAnterior = new Button();
        btnAnterior.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        hbxButtons = new HBox(btnAnterior,btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla,hbxButtons);

        hbxMain = new HBox(vbxTablilla);
        escena = new Scene(hbxMain,400,400);
    }

    private void CrearTablilla() {
        arBtnTab  = new Button[4][4];
        Image img;
        ImageView imv = new ImageView();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                img =new Image(getClass().getResource("/images/lotes/" + arImages[4 * i + j]).toString());
                imv = new ImageView(img);
                imv.setFitWidth(50);
                imv.setFitHeight(50);
                arBtnTab[j][i] = new Button();
                arBtnTab[j][i].setGraphic(imv);
                gdpTablilla.add(arBtnTab[j][i],j,i);
            }
        }
    }

}
