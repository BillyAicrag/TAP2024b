package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.ArtistaDAO;
import org.example.tap2024b.vistas.FormArtista;

import java.util.Optional;

public class ButtonCellArt extends TableCell<ArtistaDAO,String> {
    Button btnCelda;

    public ButtonCellArt(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        ArtistaDAO objArt = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormArtista(this.getTableView(), objArt);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objArt.DELETE();
                this.getTableView().setItems(objArt.SELECTALL());
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
