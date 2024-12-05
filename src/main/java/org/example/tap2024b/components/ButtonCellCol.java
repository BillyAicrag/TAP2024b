package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.ColeccionDAO;
import org.example.tap2024b.vistas.FormColeccion;

import java.util.Optional;

public class ButtonCellCol extends TableCell<ColeccionDAO,String> {
    Button btnCelda;
    int album;

    public ButtonCellCol(String str, int album) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
        this.album = album;
    }

    private void eventoBoton(String str) {
        ColeccionDAO objCol = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Eliminar")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objCol.DELETE();
                this.getTableView().setItems(objCol.SELECTALL(album));
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
