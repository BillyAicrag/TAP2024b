package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.CancionDAO;
import org.example.tap2024b.vistas.FormCancion;

import java.util.Optional;

public class ButtonCellCan extends TableCell<CancionDAO,String> {
    Button btnCelda;

    public ButtonCellCan(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        CancionDAO objCan = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormCancion(this.getTableView(), objCan);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objCan.DELETE();
                this.getTableView().setItems(objCan.SELECTALL());
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
