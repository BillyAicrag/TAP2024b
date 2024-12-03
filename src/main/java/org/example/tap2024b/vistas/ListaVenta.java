package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellVta;
import org.example.tap2024b.models.VentaDAO;

public class ListaVenta extends Stage {

    private TableView<VentaDAO> tbvVenta;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaVenta(){
        CrearUI();
        this.setTitle("Lista de Ventas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddVta = new Button();
        btnAddVta.setOnAction(event -> new FormVenta(tbvVenta, null));
        btnAddVta.setGraphic(imv);
        tlbMenu.getItems().add(btnAddVta);

        tbvVenta = new TableView<VentaDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvVenta);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        VentaDAO objVta = new VentaDAO();
        TableColumn<VentaDAO,String> tbcFechaVta = new TableColumn<>("Venta");
        tbcFechaVta.setCellValueFactory(new PropertyValueFactory<>("fechaVta"));

        TableColumn<VentaDAO,String> tbcTotalVta = new TableColumn<>("Total");
        tbcTotalVta.setCellValueFactory(new PropertyValueFactory<>("totalVta"));

        TableColumn<VentaDAO,String> tbcNomCte = new TableColumn<>("Cliente");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<VentaDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<VentaDAO, String>, TableCell<VentaDAO, String>>() {
            @Override
            public TableCell<VentaDAO, String> call(TableColumn<VentaDAO, String> ventaDAOStringTableColumn) {
                return new ButtonCellVta("Editar");
            }
        });

        TableColumn<VentaDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<VentaDAO, String>, TableCell<VentaDAO, String>>() {
            @Override
            public TableCell<VentaDAO, String> call(TableColumn<VentaDAO, String> ventaDAOStringTableColumn) {
                return new ButtonCellVta("Eliminar");
            }
        });

        tbvVenta.getColumns().addAll(tbcFechaVta, tbcTotalVta, tbcNomCte, tbcEditar, tbcEliminar);
        tbvVenta.setItems(objVta.SELECTALL());
    }
}
