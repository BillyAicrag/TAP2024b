package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Buscaminas extends Stage {
    private GridPane gdpPrincipal;
    private Label lblPregunta;
    private TextField txtMinas;
    private Button btnIniciar;
    private Button[][] arBtn;
    private VBox vbxPrincipal;
    private int lado = 10, minas = 30, vacios = 70, puntos = 0;
    private List<Integer> listaMinas;
    private Scene escena;


    public Buscaminas(){
        CrearUI();
        this.setTitle("Buscaminas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblPregunta = new Label("¿Cuántas bombas habrá en la cuadrícula?");
        txtMinas = new TextField();
        btnIniciar = new Button("Crear campo");
        btnIniciar.setOnAction(event -> crearCampo());
        gdpPrincipal = new GridPane();
        listaMinas = new ArrayList<>();
        vbxPrincipal = new VBox(lblPregunta, txtMinas, btnIniciar,gdpPrincipal);
        escena = new Scene(vbxPrincipal,500,500);
        escena.getStylesheets().add(getClass().getResource("/styles/buscaminas.css").toExternalForm());
    }

    private void crearCampo() {
        lado = 10;
        listaMinas.clear();
        gdpPrincipal.getChildren().clear();
        gdpPrincipal.setDisable(false);
        puntos = 0;
        if (!txtMinas.getText().equals("")){
            try {
                int num = Integer.parseInt(txtMinas.getText());
                //System.out.println("Correcto: " + num);
                minas = Integer.parseInt(txtMinas.getText());
                calcularCuadricula();
                colocarMinas();
                arBtn = new Button[lado][lado];
                for (int i = 0; i < lado; i++) {
                    for (int j = 0; j < lado; j++) {
                        arBtn[i][j] = new Button();
                        arBtn[i][j].setMinHeight(30);
                        arBtn[i][j].setMinWidth(30);
                        arBtn[i][j].setId("" + listaMinas.get((lado * i) + j));
                        final int finali = i, finalj = j;
                        if(arBtn[i][j].getId().equals("0")){
                            //arBtn[i][j].setOnAction(event -> botonVacio(arBtn[finali][finalj], finali, finalj));
                            arBtn[i][j].setOnMouseClicked(event -> {
                                // Si el clic es izquierdo
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    botonVacio(arBtn[finali][finalj], finali, finalj);
                                }
                                // Si el clic es derecho
                                else if (event.getButton() == MouseButton.SECONDARY) {
                                    clickDerechoVacio(arBtn[finali][finalj]);
                                }
                            });
                        } else {
                            arBtn[i][j].setOnAction(event -> botonBomba(arBtn[finali][finalj]));
                            arBtn[i][j].setOnMouseClicked(event -> {
                                // Si el clic es izquierdo
                                if (event.getButton() == MouseButton.PRIMARY) {
                                    botonBomba(arBtn[finali][finalj]);
                                }
                                // Si el clic es derecho
                                else if (event.getButton() == MouseButton.SECONDARY) {
                                    clickDerechoMina(arBtn[finali][finalj]);
                                }
                            });

                        }
                        gdpPrincipal.add(arBtn[i][j],i,j);
                    }
                }

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Informacion del juego");
                alerta.setContentText("No ingreso un número");
                alerta.showAndWait();
            }

        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Informacion del juego");
            alerta.setContentText("Ingrese un numero");
            alerta.showAndWait();
        }
    }

    private void botonVacio(Button btn , int i, int j) {
        ImageView imv = new ImageView(contarMinas(i,j));
        imv.setFitHeight(14);
        imv.setFitWidth(14);
        if (btn.getId().equals("0")) {
            btn.setGraphic(imv);
            puntos++;
            sumatoria();
            btn.setDisable(true);
        }
    }

    private void clickDerechoVacio(Button btn){
        Image img = new Image(getClass().getResource("/images/checkmark.png").toString());
        ImageView imv = new ImageView(img);
        imv.setFitHeight(14);
        imv.setFitWidth(14);
        if (btn.getId().equals("0")) {
            btn.setGraphic(imv);
            btn.setId("-1");
        } else {
            btn.setGraphic(null);
            btn.setId("0");
        }
    }

    private void botonBomba(Button btn) {
        Image img = new Image(getClass().getResource("/images/bomb.png").toString());
        ImageView imv = new ImageView(img);
        imv.setFitHeight(14);
        imv.setFitWidth(14);
        if (btn.getId().equals("1")) {
            btn.setGraphic(imv);
            btn.setId("button");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            revelarMinas();
            gdpPrincipal.setDisable(true);
            alerta(1);
        }
    }

    private void clickDerechoMina(Button btn){
        Image img = new Image(getClass().getResource("/images/checkmark.png").toString());
        ImageView imv = new ImageView(img);
        imv.setFitHeight(14);
        imv.setFitWidth(14);
        if (btn.getId().equals("1")) {
            btn.setGraphic(imv);
            btn.setId("2");
        } else {
            btn.setGraphic(null);
            btn.setId("1");
        }
    }

    private void calcularCuadricula() {
        if(minas > ((lado * lado) / 3)){
            lado++;
            calcularCuadricula();
        }
    }

    private void colocarMinas() {
        vacios = (lado * lado) - minas;
        for (int i = 0; i < vacios; i++) {
            listaMinas.add(0);
        }
        for (int i = 0; i < minas; i++) {
            listaMinas.add(1);
        }
        Collections.shuffle(listaMinas);
    }

    private Image contarMinas(int finali, int finalj) {
        int num = 0, compari = finali - 1, comparj = finalj - 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((compari >= 0) && (compari < lado)){
                    if ((comparj >= 0) && (comparj < lado)) {
                        if (arBtn[compari][comparj].getId().equals("1") || arBtn[compari][comparj].getId().equals("2"))
                            num++;
                    }
                }
                compari++;
            }
            compari = finali - 1;
            comparj++;
        }
        Image img = new Image(getClass().getResource("/images/num" + num + ".png").toString());
        return img;
    }

    private void sumatoria() {
        System.out.println("Vacios: " + vacios + " Puntos: " + puntos);
        if (puntos == vacios) {
            gdpPrincipal.setDisable(true);
            alerta(0);
            revelarMinas();
        }
    }

    private void revelarMinas(){
        for (int i = 0; i < lado; i++) {
            for (int j = 0; j < lado; j++) {
                if (arBtn[i][j].getId().equals("1")) {
                    arBtn[i][j].setGraphic(imagenBomba());
                }
                if (arBtn[i][j].getId().equals("2")){
                    arBtn[i][j].setGraphic(imagenBomba());
                    arBtn[i][j].setId("buttons");
                }
            }
        }
    }

    private ImageView imagenBomba() {
        Image img = new Image(getClass().getResource("/images/bomb.png").toString());
        ImageView imv = new ImageView(img);
        imv.setFitHeight(14);
        imv.setFitWidth(14);
        return imv;
    }

    private void alerta(int opc){
        String msj;
        Alert.AlertType type;
        if (opc == 0) {
            msj = "¡¡¡FELICIDADES!!!";
            type = Alert.AlertType.INFORMATION;
        } else {
            msj = "Fin de la partida";
            type = Alert.AlertType.INFORMATION;
        }
        Alert alerta = new Alert(type);
        alerta.setTitle("Informacion del juego");
        alerta.setContentText(msj);
        alerta.showAndWait();
    }
}
