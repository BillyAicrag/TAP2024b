package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[] arBtns;
    private TextField txtPantalla;
    private GridPane paneTeclado;
    private VBox vBox;
    private Scene escena;

    private void CrearUI(){
        arBtns = new Button[16];
        txtPantalla = new TextField("0");
        paneTeclado = new GridPane();
        vBox = new VBox(txtPantalla,paneTeclado);
        vBox.setSpacing(10);
        escena = new Scene(vBox);
    }

    public Calculadora(){
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }
}
