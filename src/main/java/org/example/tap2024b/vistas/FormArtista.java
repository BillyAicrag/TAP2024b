package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.ArtistaDAO;
import org.example.tap2024b.models.ClienteDAO;

public class FormArtista extends Stage {

    private TextField txtNomArt;
    private TextField txtNacionalidadArt;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<ArtistaDAO> tbvArtista;
    private ArtistaDAO objArt;
    private Scene escena;

    public FormArtista(TableView<ArtistaDAO> tbv, ArtistaDAO objtA){
        tbvArtista = tbv;
        CrearUI();
        if (objtA != null ) {
            this.objArt = objtA;
            txtNomArt.setText(objArt.getNomArt());
            txtNacionalidadArt.setText(objArt.getNacionalidadArt());
            this.setTitle("Editar Artista");
        } else {
            this.objArt = new ArtistaDAO();
            this.setTitle("Agregar Artista");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomArt = new TextField();
        txtNomArt.setPromptText("Nombre del artista");
        txtNacionalidadArt = new TextField();
        txtNacionalidadArt.setPromptText("Nacionalidad del artista");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarArtista());
        vBox = new VBox(txtNomArt,txtNacionalidadArt,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarArtista() {
        objArt.setNomArt(txtNomArt.getText());
        objArt.setNacionalidadArt(txtNacionalidadArt.getText());
        String msj;
        Alert.AlertType type;

        if(objArt.getIdArt() > 0) {
            objArt.UPDATE();
        } else {
            if (objArt.INSERT() > 0) {
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

        tbvArtista.setItems(objArt.SELECTALL());
        tbvArtista.refresh();
    }
}