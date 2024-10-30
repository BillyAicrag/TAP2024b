package org.example.tap2024b.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tap2024b.models.ClienteDAO;
import org.example.tap2024b.vistas.FormCliente;

import java.util.Optional;

public class ButtonCell extends TableCell<ClienteDAO,String> {
    Button btnCelda;

    public ButtonCell(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        ClienteDAO objCte = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormCliente(this.getTableView(), objCte);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                objCte.DELETE();
                this.getTableView().setItems(objCte.SELECTALL());
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
