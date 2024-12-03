package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.VentaDAO;

public class FormVenta extends Stage {

    private TextField txtFechaVta;
    private TextField txtTotalVta;
    private TextField txtNomCte;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<VentaDAO> tbvVenta;
    private VentaDAO objVta;
    private Scene escena;

    public FormVenta(TableView<VentaDAO> tbv, VentaDAO objtV){
        tbvVenta = tbv;
        CrearUI();
        if (objtV != null ) {
            this.objVta = objtV;
            txtFechaVta.setText(objVta.getFechaVta());
            txtTotalVta.setText(objVta.getTotalVta() + "");
            txtNomCte.setText(objVta.getNomCte());
            this.setTitle("Editar Venta");
        } else {
            this.objVta = new VentaDAO();
            this.setTitle("Hacer Venta");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtFechaVta = new TextField();
        txtFechaVta.setPromptText("Fecha de venta");
        txtTotalVta = new TextField();
        txtTotalVta.setPromptText("Total");
        txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre del cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarVenta());
        vBox = new VBox(txtFechaVta,txtTotalVta,txtNomCte,btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void GuardarVenta() {
        objVta.setFechaVta(txtFechaVta.getText());
        objVta.setTotalVta(Double.parseDouble(txtTotalVta.getText()));
        objVta.setNomCte(txtNomCte.getText());
        String msj;
        Alert.AlertType type;

        if(objVta.getIdVta() > 0) {
            objVta.UPDATE();
        } else {
            if (objVta.INSERT() > 0) {
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

        tbvVenta.setItems(objVta.SELECTALL());
        tbvVenta.refresh();
    }
}