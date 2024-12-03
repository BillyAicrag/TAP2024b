package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneroDAO {
    private int idGen;
    private String nomGen;

    public int getIdGen() {
        return idGen;
    }

    public void setIdGen(int idGen) {
        this.idGen = idGen;
    }

    public String getNomGen() {
        return nomGen;
    }

    public void setNomGen(String nomGen) {
        this.nomGen = nomGen;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO genero(nomGen)" +
                " VALUES('" + this.nomGen + "')";
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
        String query = "UPDATE genero SET nomGen = '" + this.nomGen + "'" +
                " WHERE idGen = " + this.idGen;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM genero WHERE idGen = " + this.idGen;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<GeneroDAO> SELECTALL(){
        GeneroDAO objGen;
        String query = "SELECT * FROM genero";
        ObservableList<GeneroDAO> listaG = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objGen = new GeneroDAO();
                objGen.idGen = res.getInt(1);
                objGen.nomGen = res.getString(2);
                listaG.add(objGen);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaG;
    }
}