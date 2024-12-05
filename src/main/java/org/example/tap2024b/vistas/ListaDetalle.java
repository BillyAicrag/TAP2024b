package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.CompraDAO;
import org.example.tap2024b.models.DetalleDAO;

public class ListaDetalle extends Stage {

    private TableView<DetalleDAO> tbvDetalle;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;
    private int venta;

    public ListaDetalle(int venta){
        this.venta = venta;
        CrearUI();
        this.setTitle("Lista de Canciones de la Venta");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvDetalle = new TableView<DetalleDAO>();
        CrearTable();

        vbx = new VBox(tbvDetalle);
        escena = new Scene(vbx, 600, 500);
    }

    private void CrearTable() {
        DetalleDAO objDet = new DetalleDAO();
        TableColumn<DetalleDAO,String> tbcIdVta = new TableColumn<>("No. Venta");
        tbcIdVta.setCellValueFactory(new PropertyValueFactory<>("idVta"));

        TableColumn<DetalleDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<DetalleDAO,String> tbcCostoCan = new TableColumn<>("Costo");
        tbcCostoCan.setCellValueFactory(new PropertyValueFactory<>("costoCan"));

        TableColumn<DetalleDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nomGen"));

        TableColumn<DetalleDAO,String> tbcNomArt = new TableColumn<>("Artista/Banda");
        tbcNomArt.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<DetalleDAO,String> tbcNomAl = new TableColumn<>("Album");
        tbcNomAl.setCellValueFactory(new PropertyValueFactory<>("nomAl"));

        tbvDetalle.getColumns().addAll(tbcIdVta, tbcNomCan, tbcNomAl, tbcNomArt, tbcNomGen, tbcCostoCan);
        tbvDetalle.setItems(objDet.SELECTALL(venta));
    }
}