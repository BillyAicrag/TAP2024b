package org.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tap2024b.components.ButtonCellDet;
import org.example.tap2024b.models.CancionDAO;
import org.example.tap2024b.models.CompraDAO;
import org.example.tap2024b.models.DetalleDAO;
import org.example.tap2024b.models.VentaDAO;

import java.util.Optional;

public class ListaCompra extends Stage {

    private TableView<CompraDAO> tbvCompra;
    private TableView<CompraDAO> tbvCarrito;
    private ToolBar tlbMenu;
    private VBox vbx;
    private HBox hbx;
    private HBox hbxInferior;
    private Button btnPay;
    private Scene escena;
    private double total = 0;
    private boolean artista = false;
    private boolean album = false;
    private String nomCte;
    private Label lblTotal;
    private TableView<VentaDAO> tbvVenta;

    public ListaCompra(String nomCte, TableView<VentaDAO> tbv){
        this.tbvVenta = tbv;
        this.nomCte = nomCte;
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
        Button btnDel = new Button("Quitar Busqueda");
        btnDel.setOnAction(event -> quitar());
        tlbMenu.getItems().addAll(btnDel);

        tbvCompra = new TableView<CompraDAO>();
        CrearTable();

        tbvCarrito = new TableView<CompraDAO>();
        CrearTable2();

        btnPay = new Button("Pagar");
        btnPay.setOnAction(event -> pagar());
        btnPay.setPrefWidth(50);
        lblTotal = new Label("$ " + total);
        lblTotal.setPrefWidth(50);
        activarBoton();
        hbxInferior = new HBox(btnPay, lblTotal);
        hbxInferior.setAlignment(Pos.CENTER);
        hbxInferior.setSpacing(10);
        tbvCompra.setPrefWidth(760);
        hbx = new HBox(tbvCompra, tbvCarrito);
        hbx.setAlignment(Pos.CENTER);
        vbx = new VBox(tlbMenu, hbx, hbxInferior);
        vbx.setSpacing(10);
        escena = new Scene(vbx, 1300, 500);
    }

    private void actualizarVentas() {
        VentaDAO objVta = new VentaDAO();
        tbvVenta.setItems(objVta.SELECTALL());
        tbvVenta.refresh();
    }

    private void actualizarLabel() {
        lblTotal.setText("$ " + total);
    }

    private void pagar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setContentText("Confirmar la compra");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            VentaDAO obVta = new VentaDAO();
            obVta.setNomCte(nomCte);
            obVta.setTotalVta(total);
            obVta.INSERT();

            DetalleDAO obDet = new DetalleDAO();
            int venta = obVta.SELECTVENTAS();
            for (CompraDAO com : tbvCarrito.getItems()) {
                String nomCan = com.getNomCan();
                obDet.setIdVta(venta);
                obDet.setNomCan(nomCan);
                obDet.INSERT();
            }
            tbvCarrito.getItems().clear();
            actualizarVentas();
        }
        /*
        VentaDAO obVta = new VentaDAO();
        obVta.setNomCte(nomCte);
        obVta.setTotalVta(total);
        obVta.INSERT();

        DetalleDAO obDet = new DetalleDAO();
        int venta = obVta.SELECTVENTAS();
        for (CompraDAO com : tbvCarrito.getItems()) {
            String nomCan = com.getNomCan();
            obDet.setIdVta(venta);
            obDet.setNomCan(nomCan);
            obDet.INSERT();
        }
        tbvCarrito.getItems().clear();
         */
    }

    private void activarBoton() {
        if (total > 0)
            btnPay.setDisable(false);
        else
            btnPay.setDisable(true);
    }

    private void quitar(){
        CompraDAO objCom = new CompraDAO();
        this.artista = false;
        this.album = false;
        tbvCompra.setItems(objCom.SELECTALL());
        tbvCompra.refresh();
    }

    private void CrearTable() {
        CompraDAO objCom = new CompraDAO();

        TableColumn<CompraDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<CompraDAO,String> tbcCostoCan = new TableColumn<>("Costo");
        tbcCostoCan.setCellValueFactory(new PropertyValueFactory<>("costoCan"));

        TableColumn<CompraDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nomGen"));

        TableColumn<CompraDAO,String> tbcNomArt = new TableColumn<>("Artista/Banda");
        tbcNomArt.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<CompraDAO,String> tbcNomAl = new TableColumn<>("Album");
        tbcNomAl.setCellValueFactory(new PropertyValueFactory<>("nomAl"));

        TableColumn<CompraDAO,String> tbcAdd = new TableColumn<>("");
        tbcAdd.setCellFactory(new Callback<TableColumn<CompraDAO, String>, TableCell<CompraDAO, String>>() {
            @Override
            public TableCell<CompraDAO, String> call(TableColumn<CompraDAO, String> compraDAOStringTableColumn) {
                total = tbvCarrito.getItems().stream().mapToDouble(CompraDAO::getCostoCan).sum();
                activarBoton();
                actualizarLabel();
                return new ButtonCellDet("Add", false, tbvCarrito);
            }
        });

        TableColumn<CompraDAO,String> tbcArtista = new TableColumn<>("Buscar:");
        tbcArtista.setCellFactory(new Callback<TableColumn<CompraDAO, String>, TableCell<CompraDAO, String>>() {
            @Override
            public TableCell<CompraDAO, String> call(TableColumn<CompraDAO, String> compraDAOStringTableColumn) {
                artista = true;
                return new ButtonCellDet("Artista", album, tbvCarrito);
            }
        });

        TableColumn<CompraDAO,String> tbcAlbum = new TableColumn<>("Buscar:");
        tbcAlbum.setCellFactory(new Callback<TableColumn<CompraDAO, String>, TableCell<CompraDAO, String>>() {
            @Override
            public TableCell<CompraDAO, String> call(TableColumn<CompraDAO, String> compraDAOStringTableColumn) {
                album = true;
                return new ButtonCellDet("Album", artista, tbvCarrito);
            }
        });

        tbvCompra.getColumns().addAll(tbcNomCan, tbcNomAl, tbcNomArt, tbcNomGen, tbcCostoCan, tbcArtista, tbcAlbum, tbcAdd);
        tbvCompra.setItems(objCom.SELECTALL());
    }

    /*
    --------------------------------------------------------------------------------------------------------------------
    */
    private void CrearTable2() {

        TableColumn<CompraDAO,String> tbcNomCan = new TableColumn<>("Cancion");
        tbcNomCan.setCellValueFactory(new PropertyValueFactory<>("nomCan"));

        TableColumn<CompraDAO,String> tbcCostoCan = new TableColumn<>("Costo");
        tbcCostoCan.setCellValueFactory(new PropertyValueFactory<>("costoCan"));

        TableColumn<CompraDAO,String> tbcNomGen = new TableColumn<>("Genero");
        tbcNomGen.setCellValueFactory(new PropertyValueFactory<>("nomGen"));

        TableColumn<CompraDAO,String> tbcNomArt = new TableColumn<>("Artista/Banda");
        tbcNomArt.setCellValueFactory(new PropertyValueFactory<>("nomArt"));

        TableColumn<CompraDAO,String> tbcNomAl = new TableColumn<>("Album");
        tbcNomAl.setCellValueFactory(new PropertyValueFactory<>("nomAl"));

        TableColumn<CompraDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<CompraDAO, String>, TableCell<CompraDAO, String>>() {
            @Override
            public TableCell<CompraDAO, String> call(TableColumn<CompraDAO, String> compraDAOStringTableColumn) {
                album = true;
                activarBoton();
                actualizarLabel();
                return new ButtonCellDet("Eliminar", false, tbvCompra);
            }
        });

        tbvCarrito.getColumns().addAll(tbcNomCan, tbcNomAl, tbcNomArt, tbcNomGen, tbcCostoCan, tbcEliminar);
        //tbvCarrito.setItems();
    }
}