package org.example.tap2024b.components;

import javafx.scene.control.*;
import org.example.tap2024b.models.CompraDAO;

import java.util.Optional;

public class ButtonCellDet extends TableCell<CompraDAO,String> {
    Button btnCelda;
    boolean flag;
    private TableView<CompraDAO> tbvCarrito;

    public ButtonCellDet(String str, boolean flag, TableView<CompraDAO> tbv) {
        this.tbvCarrito = tbv;
        this.flag = flag;
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> eventoBoton(str));
    }

    private void eventoBoton(String str) {
        CompraDAO objDet = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Artista")){
            if(flag)
                this.getTableView().setItems(objDet.SELECTALL(objDet.getNomArt(), objDet.getNomAl()));
            else
                this.getTableView().setItems(objDet.SELECTALL(objDet.getNomArt(), ""));
            this.getTableView().refresh();
        } else if (str.equals("Album")){
            if(flag)
                this.getTableView().setItems(objDet.SELECTALL(objDet.getNomArt(), objDet.getNomAl()));
            else
                this.getTableView().setItems(objDet.SELECTALL("", objDet.getNomAl()));
            this.getTableView().refresh();
        } else if (str.equals("Add")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas agregar esta cancion al carrito de compras?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                tbvCarrito.getItems().add(this.getTableView().getItems().get(this.getIndex()));
                tbvCarrito.refresh();
            }
            this.getTableView().refresh();
            //tbvCarrito.getItems().add(this.getTableView().getItems().get(this.getIndex()));
            //tbvCarrito.refresh();
        } else if (str.equals("Eliminar")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar esta cancion del carrito de compras?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                this.getTableView().getItems().remove(this.getIndex());
                this.getTableView().refresh();
            }
            tbvCarrito.refresh();
            //this.getTableView().getItems().remove(this.getIndex());
            //this.getTableView().refresh();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del sistema");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                //objDet.DELETE();
                this.getTableView().setItems(objDet.SELECTALL());
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
