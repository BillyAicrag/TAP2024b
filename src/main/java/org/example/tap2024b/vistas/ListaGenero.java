package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellGen;
import org.example.tap2024b.models.GeneroDAO;

public class ListaGenero extends Stage {

    private TableView<GeneroDAO> tbvGenero;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaGenero(){
        CrearUI();
        this.setTitle("Lista de Generos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddGen = new Button();
        btnAddGen.setOnAction(event -> new FormGenero(tbvGenero, null));
        btnAddGen.setGraphic(imv);
        tlbMenu.getItems().add(btnAddGen);

        tbvGenero = new TableView<GeneroDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvGenero);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        GeneroDAO objCte = new GeneroDAO();
        TableColumn<GeneroDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nomGen"));

        TableColumn<GeneroDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGen("Editar");
            }
        });

        TableColumn<GeneroDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGen("Eliminar");
            }
        });

        tbvGenero.getColumns().addAll(tbcNomGen, tbcEditar, tbcEliminar);
        tbvGenero.setItems(objCte.SELECTALL());
    }
}
