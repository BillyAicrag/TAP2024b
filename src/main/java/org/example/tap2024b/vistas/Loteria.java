package org.example.tap2024b.vistas;

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

public class Loteria extends Stage {
    private GridPane[] gdpTabla = new GridPane[5];
    private VBox vbxIzq, vbxDer, vbxMostrar, vbxMazo;
    private VBox[] vbxTablas = new VBox[5];
    private HBox hbxPrincipal, hbxBotones;
    private Button[][] arBtnImagenes = new Button[3][4];
    private Button btnIzquierda, btnDerecha, btnInicio;
    private Scene escena;
    private String[] arImagenes = new String[54];
    private String[] arTotal = new String[54], arPasaron = new String[54];
    private int[][] arUsados = new int[5][16];
    private int[]  arRelleno = {0,0,0,0,0};
    private int actual = 0 ;
    private String carta = "20";
    private Label lblTimer;
    private ImageView imvMazo;


    public Loteria(){
        CrearUI();
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){
        generarCartas();
        for (int i = 0; i < 5; i++) {
            vbxTablas[i] = new VBox(crearTabla(i));
        }
        btnDerecha = new Button("->");
        btnDerecha.setOnAction(event -> cambiarTabla(1));
        btnIzquierda = new Button("<-");
        btnIzquierda.setOnAction(event -> cambiarTabla(2));
        hbxBotones = new HBox(btnIzquierda,btnDerecha);
        vbxMostrar = new VBox(vbxTablas[0]);
        vbxIzq = new VBox(vbxMostrar,hbxBotones);


        btnInicio = new Button("Inicio");
        hbxPrincipal = new HBox(vbxIzq);
        escena = new Scene(hbxPrincipal,500,700);
    }

    private void cambiarTabla(int n) {
        if(n == 1)
            actual++;
        else
            actual--;
        if(actual > 4)
            actual = 0;
        if (actual < 0)
            actual = 4;
        vbxMostrar.getChildren().clear();
        vbxMostrar.getChildren().add(vbxTablas[actual]);
    }

    private GridPane crearTabla(int con){
        gdpTabla[con] = new GridPane();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String str = rellenarButton(con);
                arBtnImagenes[j][i] = new Button(str);
                arBtnImagenes[j][i].setPrefSize(100, 100);
                int finali = i, finalj = j;
                arBtnImagenes[j][i].setOnAction(event -> marcar(str,finalj,finali));
                gdpTabla[con].add(arBtnImagenes[j][i],j,i);
            }
        }
        return gdpTabla[con];
    }

    private String rellenarButton(int con) {
        boolean flag = true;
        int n = ((int) (Math.random() * 54) + 1);
        while (flag) {
            flag = false;
            for (int i = 0; i < arRelleno[con]; i++) {
                if (arUsados[con][i] == n)
                    flag = true;
            }
            if (flag)
                n = ((int) (Math.random() * 54) + 1);
        }
        arUsados[con][arRelleno[con]] = n;
        arRelleno[con]++;
        return ("" + n);
    }

    public void generarCartas(){
        for (int i = 0; i < arTotal.length; i++) {
            arTotal[i] = i + "";
        }
    }

    private void marcar(String str, int column, int row) {
        if (str.equals(carta)) {
            for (Node node : gdpTabla[actual].getChildren()) {
                Integer nodeColumn = GridPane.getColumnIndex(node);
                Integer nodeRow = GridPane.getRowIndex(node);

                // Si no tiene un valor, significa que está en la columna 0 o fila 0
                if (nodeColumn == null) nodeColumn = 0;
                if (nodeRow == null) nodeRow = 0;

                if (nodeColumn == column && nodeRow == row) {
                    gdpTabla[actual].getChildren().remove(node);
                    break; // Es importante romper el bucle para evitar excepciones de concurrencia
                }
            }
        }
    }


    private void CrearMazo() {
        Image imgMazo = new Image(getClass().getResource("/images/lotes/mystery.png").toString());
        lblTimer = new Label("00:00");
        imvMazo = new ImageView(imgMazo);
        imvMazo.setFitHeight(400);
        imvMazo.setFitWidth(400);
        btnInicio = new Button("Iniciar Juego");
        vbxMazo = new VBox(lblTimer,imvMazo,btnInicio);
        vbxMazo.setSpacing(20);
    }

}
