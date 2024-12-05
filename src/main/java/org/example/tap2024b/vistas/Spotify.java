package org.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.ClienteDAO;

public class Spotify extends Stage {
    private VBox vbx1, vbx2;
    private VBox vbx11, vbx12, vbx13, vbx21, vbx22, vbx23;
    private HBox hbx;
    private Label lblCte, lblCan, lblArt, lblAl, lblVenta, lblGen;
    private Scene escena;

    public Spotify(){
        CrearUI();
        this.setTitle("Lista de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imv1 = new ImageView(getClass().getResource("/images/customer.png").toString());
        imv1.setFitWidth(100);
        imv1.setFitHeight(100);
        Button btnAddCte = new Button();
        btnAddCte.setOnAction(event -> new ListaClientes());
        btnAddCte.setGraphic(imv1);
        lblCte = new Label("Clientes");

        ImageView imv2 = new ImageView(getClass().getResource("/images/artist.png").toString());
        imv2.setFitWidth(100);
        imv2.setFitHeight(100);
        Button btnAddArt = new Button();
        btnAddArt.setOnAction(event -> new ListaArtista());
        btnAddArt.setGraphic(imv2);
        lblArt = new Label("Artistas");

        ImageView imv3 = new ImageView(getClass().getResource("/images/genre.png").toString());
        imv3.setFitWidth(100);
        imv3.setFitHeight(100);
        Button btnAddGen = new Button();
        btnAddGen.setOnAction(event -> new ListaGenero());
        btnAddGen.setGraphic(imv3);
        lblGen = new Label("Generos");

        ImageView imv4 = new ImageView(getClass().getResource("/images/album.png").toString());
        imv4.setFitWidth(100);
        imv4.setFitHeight(100);
        Button btnAddAl = new Button();
        btnAddAl.setOnAction(event -> new ListaAlbum());
        btnAddAl.setGraphic(imv4);
        lblAl = new Label("Albums");

        ImageView imv5 = new ImageView(getClass().getResource("/images/song.png").toString());
        imv5.setFitWidth(100);
        imv5.setFitHeight(100);
        Button btnAddCan = new Button();
        btnAddCan.setOnAction(event -> new ListaCancion());
        btnAddCan.setGraphic(imv5);
        lblCan = new Label("Canciones");

        ImageView imv6 = new ImageView(getClass().getResource("/images/sell.png").toString());
        imv6.setFitWidth(100);
        imv6.setFitHeight(100);
        Button btnAddVta = new Button();
        btnAddVta.setOnAction(event -> new ListaVenta());
        btnAddVta.setGraphic(imv6);
        lblVenta = new Label("Ventas");

        vbx11 = new VBox(btnAddCte, lblCte);
        vbx11.setAlignment(Pos.CENTER);
        vbx12 = new VBox(btnAddCan, lblCan);
        vbx12.setAlignment(Pos.CENTER);
        vbx13 = new VBox(btnAddAl, lblAl);
        vbx13.setAlignment(Pos.CENTER);

        vbx21 = new VBox(btnAddArt, lblArt);
        vbx21.setAlignment(Pos.CENTER);
        vbx22 = new VBox(btnAddGen, lblGen);
        vbx22.setAlignment(Pos.CENTER);
        vbx23 = new VBox(btnAddVta, lblVenta);
        vbx23.setAlignment(Pos.CENTER);

        vbx1 = new VBox(vbx11, vbx12, vbx13);
        vbx1.setAlignment(Pos.CENTER);
        vbx1.setSpacing(10);
        vbx2 = new VBox(vbx21, vbx22, vbx23);
        vbx2.setAlignment(Pos.CENTER);
        vbx2.setSpacing(10);
        hbx = new HBox(vbx1, vbx2);
        hbx.setAlignment(Pos.CENTER);
        hbx.setSpacing(10);
        escena = new Scene(hbx, 500, 500);
    }
}
