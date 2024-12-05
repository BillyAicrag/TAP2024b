package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ColeccionDAO {

    private String nomAl;
    private String nomCan;

    public String getNomAl() {
        return nomAl;
    }

    public void setNomAl(String nomAl) {
        this.nomAl = nomAl;
    }

    public String getNomCan() {
        return nomCan;
    }

    public void setNomCan(String nomCan) {
        this.nomCan = nomCan;
    }

    public int INSERT() {
        int rowCount;

        String querys = "SELECT idAl FROM album where nomAl = '" + this.nomAl + "'";
        String idAlbum = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idAlbum = rs.getString("idAl");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idAl = Integer.parseInt(idAlbum);

        String queryss = "SELECT idCan FROM cancion where nomCan = '" + this.nomCan + "'";
        String idCancion = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryss);
            if (rs.next())
                idCancion = rs.getString("idCan");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idCan = Integer.parseInt(idCancion);

        String query = "INSERT INTO coleccion(idAl, idCan)" +
                " VALUES(" + idAl + "," + idCan + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        }catch (Exception e ){
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    public void DELETE(){

        String querys = "SELECT idAl FROM album where nomAl = '" + this.nomAl + "'";
        String idAlbum = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idAlbum = rs.getString("idAl");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idAl = Integer.parseInt(idAlbum);

        String queryss = "SELECT idCan FROM cancion where nomCan = '" + this.nomCan + "'";
        String idCancion = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryss);
            if (rs.next())
                idCancion = rs.getString("idCan");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idCan = Integer.parseInt(idCancion);

        String query = "DELETE FROM coleccion WHERE idAl = " + idAl + " AND idCan = " + idCan;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<ColeccionDAO> SELECTALL(int idAl){
        ColeccionDAO objCol;
        String query = "SELECT * FROM coleccion where idAl = " + idAl;
        ObservableList<ColeccionDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {

                String querys = "SELECT nomAl FROM album where idAl = " + idAl;
                String nombreAlbum = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    if (rs.next())
                        nombreAlbum = rs.getString("nomAl");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                String queryss = "SELECT nomCan FROM cancion where idCan = " + res.getInt(2);
                String nombreCancion = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(queryss);
                    if (rs.next())
                        nombreCancion = rs.getString("nomCan");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                objCol = new ColeccionDAO();
                objCol.nomAl = nombreAlbum;
                objCol.nomCan = nombreCancion;
                listaC.add(objCol);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }
}