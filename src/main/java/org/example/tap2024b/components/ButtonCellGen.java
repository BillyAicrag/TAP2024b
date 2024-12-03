package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.GeneroDAO;
import org.example.tap2024b.vistas.FormGenero;

import java.util.Optional;

public class ButtonCellGen extends TableCell<GeneroDAO,String> {
    Button btnCelda;

    public ButtonCellGen(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        GeneroDAO objGen = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormGenero(this.getTableView(), objGen);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objGen.DELETE();
                this.getTableView().setItems(objGen.SELECTALL());
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
