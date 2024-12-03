package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionDAO {
    private int idCan;
    private String nomCan;
    private String duracionCan;
    private String fechaCan;
    private double costoCan;
    private String nomGen;

    public int getIdCan() {
        return idCan;
    }

    public void setIdCan(int idCan) {
        this.idCan = idCan;
    }

    public String getNomCan() {
        return nomCan;
    }

    public void setNomCan(String nomCan) {
        this.nomCan = nomCan;
    }

    public String getDuracionCan() {
        return duracionCan;
    }

    public void setDuracionCan(String duracionCan) {
        this.duracionCan = duracionCan;
    }

    public String getFechaCan() {
        return fechaCan;
    }

    public void setFechaCan(String fechaCan) {
        this.fechaCan = fechaCan;
    }

    public double getCostoCan() {
        return costoCan;
    }

    public void setCostoCan(double costoCan) {
        this.costoCan = costoCan;
    }

    public String getNomGen() {
        return nomGen;
    }

    public void setNomGen(String nomGen) {
        this.nomGen = nomGen;
    }

    public int INSERT() {
        int rowCount;

        String idGenString = "SELECT idGen FROM genero where nomGen = " + this.nomGen;
        try {
            String query = idGenString;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            idGenString = rs.getString("idGen");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idGen = Integer.parseInt(idGenString);

        String query = "INSERT INTO cancion(nomCan, duracionCan, fechaCan, costoCan, idGen)" +
                " VALUES('" + this.nomCan + "','" + this.duracionCan + "','" + this.fechaCan + "'," + this.costoCan +"," + idGen + ")";
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

        String idGenString = "SELECT idGen FROM genero where nomGen = " + this.nomGen;
        try {
            String query = idGenString;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            idGenString = rs.getString("idGen");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idGen = Integer.parseInt(idGenString);

        String query = "UPDATE cancion SET nomCan = '" + this.nomCan + "', " +
                "duracionCan = '" + this.duracionCan + "', fechaCan = '" + this.fechaCan + "', costoCan = " +
                this.costoCan + ", idGen = " + idGen +
                " WHERE idCan = " + this.idCan;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM cancion WHERE idCan = " + this.idCan;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<CancionDAO> SELECTALL(){
        CancionDAO objCan;
        String query = "SELECT * FROM cancion";
        ObservableList<CancionDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {

                String nomGenString = "SELECT nomGen FROM genero where idGen = " + res.getInt(6);
                try {
                    String querys = nomGenString;
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    nomGenString = rs.getString("nomGen");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                objCan = new CancionDAO();
                objCan.idCan = res.getInt(1);
                objCan.nomCan = res.getString(2);
                objCan.duracionCan = res.getString(3);
                objCan.fechaCan = res.getString(4);
                objCan.costoCan = res.getInt(5);
                objCan.nomGen = nomGenString;
                listaC.add(objCan);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }
}