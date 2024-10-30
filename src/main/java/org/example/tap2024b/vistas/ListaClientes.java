package org.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCell;
import org.example.tap2024b.models.ClienteDAO;

public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tbvClientes;
    private ToolBar tlbMenu;
    private VBox vbx;
    private Scene escena;

    public ListaClientes(){
        CrearUI();
        this.setTitle("Lista de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/Forward.png").toString());
        imv.setFitWidth(15);
        imv.setFitHeight(15);
        Button btnAddCte = new Button();
        btnAddCte.setOnAction(event -> new FormCliente(tbvClientes, null));
        btnAddCte.setGraphic(imv);
        tlbMenu.getItems().add(btnAddCte);

        tbvClientes = new TableView<ClienteDAO>();
        CrearTable();

        vbx = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vbx, 500, 500);
    }

    private void CrearTable() {
        ClienteDAO objCte = new ClienteDAO();
        TableColumn<ClienteDAO,String> tbcNomCte = new TableColumn<>("Cliente");
        tbcNomCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));

        TableColumn<ClienteDAO,String> tbcTelCte = new TableColumn<>("Telefono");
        tbcTelCte.setCellValueFactory(new PropertyValueFactory<>("telCte"));

        TableColumn<ClienteDAO,String> tbcEmailCte = new TableColumn<>("Email");
        tbcEmailCte.setCellValueFactory(new PropertyValueFactory<>("emailCte"));

        TableColumn<ClienteDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Editar");
            }
        });

        TableColumn<ClienteDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNomCte, tbcTelCte, tbcEmailCte, tbcEditar, tbcEliminar);
        tbvClientes.setItems(objCte.SELECTALL());
    }
}
