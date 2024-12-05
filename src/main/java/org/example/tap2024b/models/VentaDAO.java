package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VentaDAO {
    private int idVta;
    private String fechaVta;
    private double totalVta;
    private String nomCte;
    private int idCliente;

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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int INSERT() {
        int rowCount;

        String querys = "SELECT idCte FROM cliente where nomCte = '" + this.nomCte + "'";
        String idCliente = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idCliente = rs.getString("idCte");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idCte = Integer.parseInt(idCliente);

        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha al formato "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formatter);

        String query = "INSERT INTO venta(fechaVta, totalVta, idCte)" +
                " VALUES('" + fechaFormateada + "'," + this.totalVta + ",'" + idCte + "')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        }catch (Exception e ){
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public ObservableList<VentaDAO> SELECTALL(){
        VentaDAO objVta;
        String query = "SELECT * FROM venta";
        ObservableList<VentaDAO> listaV = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {

                String querys = "SELECT nomCte FROM cliente where idCte = " + res.getInt(4);
                String nombreCliente = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    if (rs.next())
                        nombreCliente = rs.getString("nomCte");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                objVta = new VentaDAO();
                objVta.idVta = res.getInt(1);
                objVta.fechaVta = res.getString(2);
                objVta.totalVta = res.getDouble(3);
                objVta.nomCte = nombreCliente;
                objVta.idCliente = res.getInt(4);
                listaV.add(objVta);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaV;
    }

    public int SELECTVENTAS(){
        VentaDAO objVta;
        String query = "SELECT * FROM venta";
        int venta = 0;
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {
                objVta = new VentaDAO();
                objVta.idVta = res.getInt(1);
                venta = objVta.idVta;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return venta;
    }
}