package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellCan;
import org.example.tap2024b.models.CancionDAO;

public class ListaCancion extends Stage {

    private TableView<CancionDAO> tbvCancion;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaCancion(){
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddCan = new Button();
        btnAddCan.setOnAction(event -> new FormCancion(tbvCancion, null));
        btnAddCan.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCan);

        tbvCancion = new TableView<CancionDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvCancion);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();
        TableColumn<CancionDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<CancionDAO,String> tbcDuracionCan = new TableColumn<>("Duracion");
        tbcDuracionCan.setCellValueFactory(new PropertyValueFactory<>("duracionCan"));

        TableColumn<CancionDAO,String> tbcFechaCan = new TableColumn<>("Fecha");
        tbcFechaCan.setCellValueFactory(new PropertyValueFactory<>("fechaCan"));

        TableColumn<CancionDAO,String> tbcCostoCan = new TableColumn<>("Costo");
        tbcCostoCan.setCellValueFactory(new PropertyValueFactory<>("costoCan"));

        TableColumn<CancionDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nomGen"));

        TableColumn<CancionDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<CancionDAO, String>, TableCell<CancionDAO, String>>() {
            @Override
            public TableCell<CancionDAO, String> call(TableColumn<CancionDAO, String> cancionDAOStringTableColumn) {
                return new ButtonCellCan("Editar");
            }
        });

        TableColumn<CancionDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<CancionDAO, String>, TableCell<CancionDAO, String>>() {
            @Override
            public TableCell<CancionDAO, String> call(TableColumn<CancionDAO, String> cancionDAOStringTableColumn) {
                return new ButtonCellCan("Eliminar");
            }
        });

        tbvCancion.getColumns().addAll(tbcNomCan, tbcDuracionCan, tbcFechaCan, tbcCostoCan, tbcNomGen, tbcEditar, tbcEliminar);
        tbvCancion.setItems(objCan.SELECTALL());
    }
}
