package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellIn;
import org.example.tap2024b.models.InterpretacionDAO;

public class ListaInterpretacion extends Stage {

    private TableView<InterpretacionDAO> tbvInterpretacion;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;
    private int artista;

    public ListaInterpretacion(int artista){
        this.artista = artista;
        CrearUI();
        this.setTitle("Lista de Canciones del Artista");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddIn = new Button();
        btnAddIn.setOnAction(event -> new FormInterpretacion(tbvInterpretacion, null, artista));
        btnAddIn.setGraphic(imv);
        tlbMenu.getItems().add(btnAddIn);

        tbvInterpretacion = new TableView<InterpretacionDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvInterpretacion);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        InterpretacionDAO objIn = new InterpretacionDAO();
        TableColumn<InterpretacionDAO,String> tbcNomArt = new TableColumn<>("Artista/Banda");
        tbcNomArt.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<InterpretacionDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<InterpretacionDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<InterpretacionDAO, String>, TableCell<InterpretacionDAO, String>>() {
            @Override
            public TableCell<InterpretacionDAO, String> call(TableColumn<InterpretacionDAO, String> interpretacionDAOStringTableColumn) {
                return new ButtonCellIn("Eliminar", artista);
            }
        });

        tbvInterpretacion.getColumns().addAll(tbcNomArt, tbcNomCan, tbcEliminar);
        tbvInterpretacion.setItems(objIn.SELECTALL(artista));
    }
}