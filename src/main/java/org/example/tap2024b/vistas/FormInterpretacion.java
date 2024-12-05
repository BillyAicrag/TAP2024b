package org.example.tap2024b.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tap2024b.models.InterpretacionDAO;
import org.example.tap2024b.models.Conexion;

import java.sql.ResultSet;
import java.sql.Statement;

public class FormInterpretacion extends Stage {

    private TextField txtNomArt;
    private TextField txtNomCan;
    private Button btnGuardar;
    private VBox vBox;
    private TableView<InterpretacionDAO> tbvInterpretacion;
    private InterpretacionDAO objIn;
    private Scene escena;
    private int artista;

    public FormInterpretacion(TableView<InterpretacionDAO> tbv, InterpretacionDAO objtC, int artista){
        this.artista = artista;
        tbvInterpretacion = tbv;
        CrearUI();
        if (objtC != null ) {
            this.objIn = objtC;
            txtNomArt.setText(objIn.getNomArt());
            txtNomCan.setText(objIn.getNomCan());
            this.setTitle("Editar Cancion");
        } else {
            this.objIn = new InterpretacionDAO();
            this.setTitle("Agregar Cancion");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        String querys = "SELECT nomArt FROM artista where idArt = " + artista;
        String nombreArtista = "";
        try {
            Statement stm = Conexion.connection.createStatement();
            ResultSet rs = stm.executeQuery(querys);
            if (rs.next())
                nombreArtista = rs.getString("nomArt");
        }catch (Exception e ){
            e.printStackTrace();
        }

        txtNomArt = new TextField();
        txtNomArt.setPromptText("Nombre del artista");
        txtNomArt.setText(nombreArtista);
        txtNomArt.setVisible(false);

        txtNomCan = new TextField();
        txtNomCan.setPromptText("Nombre de la cancion");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> comprobar());
        Label lbl = new Label("Nombre de la cancion");
        vBox = new VBox(lbl,txtNomCan,btnGuardar);
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
        objIn.setNomArt(txtNomArt.getText());
        objIn.setNomCan(txtNomCan.getText());
        String msj;
        Alert.AlertType type;


        if (objIn.INSERT() > 0) {
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


        tbvInterpretacion.setItems(objIn.SELECTALL(artista));
        tbvInterpretacion.refresh();
    }
}