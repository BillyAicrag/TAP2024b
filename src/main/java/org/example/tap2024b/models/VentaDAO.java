package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VentaDAO {
    private int idVta;
    private String fechaVta;
    private double totalVta;
    private String nomCte;

    public int getIdVta() {
        return idVta;
    }

    public void setIdVta(int idVta) {
        this.idVta = idVta;
    }

    public String getFechaVta() {
        return fechaVta;
    }

    public void setFechaVta(String fechaVta) {
        this.fechaVta = fechaVta;
    }

    public double getTotalVta() {
        return totalVta;
    }

    public void setTotalVta(double totalVta) {
        this.totalVta = totalVta;
    }

    public String getNomCte() {
        return nomCte;
    }

    public void setNomCte(String nomCte) {
        this.nomCte = nomCte;
    }

    public int INSERT() {
        int rowCount;

        String idCteString = "SELECT idCte FROM cliente where nomCte = " + this.nomCte;
        try {
            String query = idCteString;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            idCteString = rs.getString("idCte");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idCte = Integer.parseInt(idCteString);

        String query = "INSERT INTO venta(fechaVta, totalVta, idCte)" +
                " VALUES('" + this.fechaVta + "'," + this.totalVta + ",'" + idCte + "')";
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

        String idCteString = "SELECT idCte FROM cliente where nomCte = " + this.nomCte;
        try {
            String query = idCteString;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            idCteString = rs.getString("idCte");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idCte = Integer.parseInt(idCteString);

        String query = "UPDATE venta SET fechaVta = '" + this.fechaVta + "', " +
                "totalVta = " + this.totalVta + ", idCte = " + idCte +
                " WHERE idVta = " + this.idVta;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM venta WHERE idVta = " + this.idVta;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<VentaDAO> SELECTALL(){
        VentaDAO objVta;
        String query = "SELECT * FROM venta";
        ObservableList<VentaDAO> listaV = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                String nomCteString = "SELECT nomCte FROM cliente where idCte = " + res.getInt(4);
                try {
                    String querys = nomCteString;
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    nomCteString = rs.getString("nomCte");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                objVta = new VentaDAO();
                objVta.idVta = res.getInt(1);
                objVta.fechaVta = res.getString(2);
                objVta.totalVta = res.getDouble(3);
                objVta.nomCte = nomCteString;
                listaV.add(objVta);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaV;
    }
}