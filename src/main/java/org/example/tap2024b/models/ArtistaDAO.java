package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistaDAO {
    private int idArt;
    private String nomArt;
    private String nacionalidadArt;

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getNacionalidadArt() {
        return nacionalidadArt;
    }

    public void setNacionalidadArt(String nacionalidadArt) {
        this.nacionalidadArt = nacionalidadArt;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO artista(nomArt, nacionalidadArt)" +
                " VALUES('" + this.nomArt + "','" + this.nacionalidadArt + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        }catch (Exception e ){
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public void UPDATE(){
        String query = "UPDATE artista SET nomArt = '" + this.nomArt + "', " +
                "nacionalidadArt = '" + this.nacionalidadArt + "'" +
                " WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM artista WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<ArtistaDAO> SELECTALL(){
        ArtistaDAO objArt;
        String query = "SELECT * FROM artista";
        ObservableList<ArtistaDAO> listaA = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objArt = new ArtistaDAO();
                objArt.idArt = res.getInt(1);
                objArt.nomArt = res.getString(2);
                objArt.nacionalidadArt = res.getString(3);
                listaA.add(objArt);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaA;
    }
}