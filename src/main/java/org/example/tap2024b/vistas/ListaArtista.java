package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellArt;
import org.example.tap2024b.models.ArtistaDAO;

public class ListaArtista extends Stage {

    private TableView<ArtistaDAO> tbvArtista;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaArtista(){
        CrearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddArt = new Button();
        btnAddArt.setOnAction(event -> new FormArtista(tbvArtista, null));
        btnAddArt.setGraphic(imv);
        tlbMenu.getItems().add(btnAddArt);

        tbvArtista = new TableView<ArtistaDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvArtista);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        ArtistaDAO objArt = new ArtistaDAO();
        TableColumn<ArtistaDAO,String> tbcNomArt = new TableColumn<>("Artista");
        tbcNomArt.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<ArtistaDAO,String> tbcNacionalidadArt = new TableColumn<>("Nacionalidad");
        tbcNacionalidadArt.setCellValueFactory(new PropertyValueFactory<>("nacionalidadArt"));

        TableColumn<ArtistaDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> artistaDAOStringTableColumn) {
                return new ButtonCellArt("Editar");
            }
        });

        TableColumn<ArtistaDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> artistaDAOStringTableColumn) {
                return new ButtonCellArt("Eliminar");
            }
        });

        tbvArtista.getColumns().addAll(tbcNomArt, tbcNacionalidadArt, tbcEditar, tbcEliminar);
        tbvArtista.setItems(objArt.SELECTALL());
    }
}
