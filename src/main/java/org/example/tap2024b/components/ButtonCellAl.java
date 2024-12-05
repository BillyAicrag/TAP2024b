package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.AlbumDAO;
import org.example.tap2024b.vistas.FormAlbum;
import org.example.tap2024b.vistas.ListaColeccion;

import java.util.Optional;

public class ButtonCellAl extends TableCell<AlbumDAO,String> {
    Button btnCelda;

    public ButtonCellAl(String str) {
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        AlbumDAO objAl = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormAlbum(this.getTableView(), objAl);
        } else if (str.equals("Canciones")){
            new ListaColeccion(objAl.getIdAl());
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objAl.DELETE();
                this.getTableView().setItems(objAl.SELECTALL());
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
