package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.GeneroDAO;

public class FormGenero extends Stage {

    private TextField txtNomGen;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<GeneroDAO> tbvGenero;
    private GeneroDAO objGen;
    private Scene escena;

    public FormGenero(TableView<GeneroDAO> tbv, GeneroDAO objtG){
        tbvGenero = tbv;
        CrearUI();
        if (objtG != null ) {
            this.objGen = objtG;
            txtNomGen.setText(objGen.getNomGen());
            this.setTitle("Editar Genero");
        } else {
            this.objGen = new GeneroDAO();
            this.setTitle("Agregar Genero");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomGen = new TextField();
        txtNomGen.setPromptText("Nombre del Genero");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarGenero());
        vBox = new VBox(txtNomGen,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarGenero() {
        objGen.setNomGen(txtNomGen.getText());
        String msj;
        Alert.AlertType type;

        if(objGen.getIdGen() > 0) {
            objGen.UPDATE();
        } else {
            if (objGen.INSERT() > 0) {
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

        tbvGenero.setItems(objGen.SELECTALL());
        tbvGenero.refresh();
    }
}
