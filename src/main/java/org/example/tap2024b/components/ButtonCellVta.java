package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.VentaDAO;
import org.example.tap2024b.vistas.FormVenta;
import org.example.tap2024b.vistas.ListaDetalle;

import java.util.Optional;

public class ButtonCellVta extends TableCell<VentaDAO,String> {
    Button btnCelda;

    public ButtonCellVta(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        VentaDAO objVta = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Revisar")){
            //new FormVenta(this.getTableView(), objVta);
            int venta = objVta.getIdVta();
            new ListaDetalle(venta);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                //objVta.DELETE();
                this.getTableView().setItems(objVta.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if ( !b ) {
            this.setGraphic(btnCelda);
        }
    }
}
