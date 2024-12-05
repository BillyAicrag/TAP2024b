package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellCol;
import org.example.tap2024b.models.ColeccionDAO;

public class ListaColeccion extends Stage {

    private TableView<ColeccionDAO> tbvColeccion;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;
    private int album;

    public ListaColeccion(int album){
        this.album = album;
        CrearUI();
        this.setTitle("Lista de Canciones del Album");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddCol = new Button();
        btnAddCol.setOnAction(event -> new FormColeccion(tbvColeccion, null, album));
        btnAddCol.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCol);

        tbvColeccion = new TableView<ColeccionDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvColeccion);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        ColeccionDAO objCol = new ColeccionDAO();
        TableColumn<ColeccionDAO,String> tbcNomAl = new TableColumn<>("Album");
        tbcNomAl.setCellValueFactory(new PropertyValueFactory<>("nomAl"));

        TableColumn<ColeccionDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<ColeccionDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ColeccionDAO, String>, TableCell<ColeccionDAO, String>>() {
            @Override
            public TableCell<ColeccionDAO, String> call(TableColumn<ColeccionDAO, String> coleccionDAOStringTableColumn) {
                return new ButtonCellCol("Eliminar", album);
            }
        });

        tbvColeccion.getColumns().addAll(tbcNomAl, tbcNomCan, tbcEliminar);
        tbvColeccion.setItems(objCol.SELECTALL(album));
    }
}