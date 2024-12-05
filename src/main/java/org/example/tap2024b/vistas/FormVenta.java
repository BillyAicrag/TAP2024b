package org.example.tap2024b.vistas;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.Conexion;
import org.example.tap2024b.models.VentaDAO;

import java.sql.ResultSet;
import java.sql.Statement;

public class FormVenta extends Stage {

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
        txtNomCte = new TextField();
        txtNomCte.setPromptText("Nombre del cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> hacerCompra());
        Label lbl = new Label("Nombre del usuario");
        vBox = new VBox(lbl, txtNomCte, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void hacerCompra(){
        String query = "SELECT * FROM cliente where nomCte = '" + txtNomCte.getText() + "'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("El usuario que ingreso no existe");
                alert.showAndWait();
            } else {
                new ListaCompra(txtNomCte.getText(), tbvVenta);
                this.close();
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        //new ListaCompra(txtNomCte.getText(), tbvVenta);
        //this.close();
    }


}