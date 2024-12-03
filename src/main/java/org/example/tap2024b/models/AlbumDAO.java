package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AlbumDAO {
    private int idAl;
    private String nomAl;
    private String fechaAl;

    public int getIdAl() {
        return idAl;
    }

    public void setIdAl(int idAl) {
        this.idAl = idAl;
    }

    public String getNomAl() {
        return nomAl;
    }

    public void setNomAl(String nomAl) {
        this.nomAl = nomAl;
    }

    public String getFechaAl() {
        return fechaAl;
    }

    public void setFechaAl(String fechaAl) {
        this.fechaAl = fechaAl;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO album(nomAl, fechaAl)" +
                " VALUES('" + this.nomAl + "','" + this.fechaAl + "')";
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
        String query = "UPDATE album SET nomAl = '" + this.nomAl + "', " +
                "fechaAl = '" + this.fechaAl + "'" +
                " WHERE idAl = " + this.idAl;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM album WHERE idAl = " + this.idAl;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<AlbumDAO> SELECTALL(){
        AlbumDAO objAl;
        String query = "SELECT * FROM album";
        ObservableList<AlbumDAO> listaA = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objAl = new AlbumDAO();
                objAl.idAl = res.getInt(1);
                objAl.nomAl = res.getString(2);
                objAl.fechaAl = res.getString(3);
                listaA.add(objAl);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaA;
    }
}