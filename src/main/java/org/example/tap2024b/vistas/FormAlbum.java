package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.AlbumDAO;

public class FormAlbum extends Stage {

    private TextField txtNomAl;
    private TextField txtFechaAl;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<AlbumDAO> tbvCliente;
    private AlbumDAO objAl;
    private Scene escena;

    public FormAlbum(TableView<AlbumDAO> tbv, AlbumDAO objtA){
        tbvCliente = tbv;
        CrearUI();
        if (objtA != null ) {
            this.objAl = objtA;
            txtNomAl.setText(objAl.getNomAl());
            txtFechaAl.setText(objAl.getFechaAl());
            this.setTitle("Editar Album");
        } else {
            this.objAl = new AlbumDAO();
            this.setTitle("Agregar Album");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomAl = new TextField();
        txtNomAl.setPromptText("Nombre del album");
        txtFechaAl = new TextField();
        txtFechaAl.setPromptText("Fecha del album");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarAlbum());
        vBox = new VBox(txtNomAl,txtFechaAl,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarAlbum() {
        objAl.setNomAl(txtNomAl.getText());
        objAl.setFechaAl(txtFechaAl.getText());
        String msj;
        Alert.AlertType type;

        if(objAl.getIdAl() > 0) {
            objAl.UPDATE();
        } else {
            if (objAl.INSERT() > 0) {
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

        tbvCliente.setItems(objAl.SELECTALL());
        tbvCliente.refresh();
    }
}