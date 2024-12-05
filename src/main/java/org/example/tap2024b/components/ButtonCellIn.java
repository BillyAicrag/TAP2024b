package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.InterpretacionDAO;
import org.example.tap2024b.vistas.FormInterpretacion;

import java.util.Optional;

public class ButtonCellIn extends TableCell<InterpretacionDAO,String> {
    Button btnCelda;
    int artista;

    public ButtonCellIn(String str, int artista) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
        this.artista = artista;
    }

    private void eventoBoton(String str) {
        InterpretacionDAO objIn = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Eliminar")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objIn.DELETE();
                this.getTableView().setItems(objIn.SELECTALL(artista));
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
