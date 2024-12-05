package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.ColeccionDAO;
import org.example.tap2024b.models.Conexion;

import java.sql.ResultSet;
import java.sql.Statement;

public class FormColeccion extends Stage {

    private TextField txtNomAl;
    private TextField txtNomCan;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<ColeccionDAO> tbvColeccion;
    private ColeccionDAO objCol;
    private Scene escena;
    private int album;

    public FormColeccion(TableView<ColeccionDAO> tbv, ColeccionDAO objtC, int album){
        this.album = album;
        tbvColeccion = tbv;
        CrearUI();
        if (objtC != null ) {
            this.objCol = objtC;
            txtNomAl.setText(objCol.getNomAl());
            txtNomCan.setText(objCol.getNomCan());
            this.setTitle("Editar Cancion");
        } else {
            this.objCol = new ColeccionDAO();
            this.setTitle("Agregar Cancion");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        String querys = "SELECT nomAl FROM album where idAl = " + album;
        String nombreAlbum = "";
        try {
            Statement stm = Conexion.connection.createStatement();
            ResultSet rs = stm.executeQuery(querys);
            if (rs.next())
                nombreAlbum = rs.getString("nomAl");
        }catch (Exception e ){
            e.printStackTrace();
        }

        txtNomAl = new TextField();
        txtNomAl.setPromptText("Nombre del album");
        txtNomAl.setText(nombreAlbum);
        txtNomAl.setVisible(false);

        txtNomCan = new TextField();
        txtNomCan.setPromptText("Nombre de la cancion");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> comprobar());
        Label lbl = new Label("Nombre de la cancion");
        vBox = new VBox(lbl, txtNomCan, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 150);
    }

    private void comprobar() {
        String queryss = "SELECT * FROM cancion where nomCan = '" + txtNomCan.getText() + "'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryss);
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("La cancion que ingreso no existe");
                alert.showAndWait();
            } else {
                GuardarCancion();
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void GuardarCancion() {
        objCol.setNomAl(txtNomAl.getText());
        objCol.setNomCan(txtNomCan.getText());
        String msj;
        Alert.AlertType type;


        if (objCol.INSERT() > 0) {
            msj = "Registro insertado";
            type = Alert.AlertType.INFORMATION;
        } else {
            msj = "Ocurrio un error al insertar, intente de nuevo";
            type = Alert.AlertType.ERROR;
        }
        Alert alerta = new Alert(type);
        alerta.setTitle("Alerta del sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();


        tbvColeccion.setItems(objCol.SELECTALL(album));
        tbvColeccion.refresh();
    }
}