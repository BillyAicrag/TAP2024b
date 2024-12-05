package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellAl;
import org.example.tap2024b.models.AlbumDAO;

public class ListaAlbum extends Stage {

    private TableView<AlbumDAO> tbvAlbum;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaAlbum(){
        CrearUI();
        this.setTitle("Lista de Album");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddAl = new Button();
        btnAddAl.setOnAction(event -> new FormAlbum(tbvAlbum, null));
        btnAddAl.setGraphic(imv);
        tlbMenu.getItems().add(btnAddAl);

        tbvAlbum = new TableView<AlbumDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvAlbum);
        escena = new Scene(vbx, 500, 400);
    }

    private void CrearTable() {
        AlbumDAO objAl = new AlbumDAO();
        TableColumn<AlbumDAO,String> tbcNomAl = new TableColumn<>("Album");
        tbcNomAl.setCellValueFactory(new PropertyValueFactory<>("nomAl"));

        TableColumn<AlbumDAO,String> tbcFechaAl = new TableColumn<>("Fecha del album");
        tbcFechaAl.setCellValueFactory(new PropertyValueFactory<>("fechaAl"));

        TableColumn<AlbumDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAl("Editar");
            }
        });

        TableColumn<AlbumDAO,String> tbcLista = new TableColumn<>("");
        tbcLista.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAl("Canciones");
            }
        });

        TableColumn<AlbumDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAl("Eliminar");
            }
        });

        tbvAlbum.getColumns().addAll(tbcNomAl, tbcFechaAl, tbcLista, tbcEditar, tbcEliminar);
        tbvAlbum.setItems(objAl.SELECTALL());
    }
}
