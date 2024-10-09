package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.ClienteDAO;

public class FormCliente extends Stage {

    private TextField txtNomCte;
    private TextField txtTelCte;
    private TextField txtEmailCte;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;

    ClienteDAO objCte;
    public FormCliente(){
        objCte = new ClienteDAO();
        CrearUI();
        this.setTitle("Agregar Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre del cliente");
        txtTelCte = new TextField();
        txtTelCte.setPromptText("Telefono del cliente");
        txtEmailCte = new TextField();
        txtEmailCte.setPromptText("Email del cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCliente());
        vBox = new VBox(txtNomCte,txtTelCte,txtEmailCte,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarCliente() {
        objCte.setNomCte(txtNomCte.getText());
        objCte.setTelCte(txtTelCte.getText());
        objCte.setEmailCte(txtEmailCte.getText());
        String msj;
        Alert.AlertType type;
        if (objCte.INSERT() > 0) {
            msj = "Registro insertado";
            type = Alert.AlertType.INFORMATION;
        } else {
            msj = "Ocurrio un error al insertar, intente de nuevo";
            type = Alert.AlertType.ERROR;
        }
        Alert alerta = new Alert(type);
        alerta.setTitle("Alerta del sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();
    }

}
