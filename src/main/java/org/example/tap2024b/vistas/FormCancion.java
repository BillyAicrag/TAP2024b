package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.CancionDAO;

public class FormCancion extends Stage {

    private TextField txtNomCan;
    private TextField txtDuracionCan;
    private TextField txtFechaCan;
    private TextField txtCostoCan;
    private TextField txtNomGen;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<CancionDAO> tbvCancion;
    private CancionDAO objCan;
    private Scene escena;

    public FormCancion(TableView<CancionDAO> tbv, CancionDAO objtC){
        tbvCancion = tbv;
        CrearUI();
        if (objtC != null ) {
            this.objCan = objtC;
            txtNomCan.setText(objCan.getNomCan());
            txtDuracionCan.setText(objCan.getDuracionCan());
            txtFechaCan.setText(objCan.getFechaCan());
            txtCostoCan.setText(objCan.getCostoCan() + "");
            txtNomGen.setText(objCan.getNomGen());
            this.setTitle("Editar Cancion");
        } else {
            this.objCan = new CancionDAO();
            this.setTitle("Agregar Cancion");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNomCan = new TextField();
        txtNomCan.setPromptText("Nombre de la cancion");
        txtDuracionCan = new TextField();
        txtDuracionCan.setPromptText("Duracion de la cancion");
        txtFechaCan = new TextField();
        txtFechaCan.setPromptText("Fecha de la cancion");
        txtCostoCan = new TextField();
        txtCostoCan.setPromptText("Costo de la cancion");
        txtNomGen = new TextField();
        txtNomGen.setPromptText("Genero de la cancion");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCancion());
        vBox = new VBox(txtNomCan,txtDuracionCan,txtFechaCan,txtCostoCan,txtNomGen,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarCancion() {
        objCan.setNomCan(txtNomCan.getText());
        objCan.setDuracionCan(txtDuracionCan.getText());
        objCan.setFechaCan(txtFechaCan.getText());
        objCan.setCostoCan(Double.parseDouble(txtCostoCan.getText()));
        objCan.setNomGen(txtNomGen.getText());
        String msj;
        Alert.AlertType type;

        if(objCan.getIdCan() > 0) {
            objCan.UPDATE();
        } else {
            if (objCan.INSERT() > 0) {
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

        tbvCancion.setItems(objCan.SELECTALL());
        tbvCancion.refresh();
    }
}