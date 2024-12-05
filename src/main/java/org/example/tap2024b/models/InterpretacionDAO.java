package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InterpretacionDAO {

    private String nomArt;
    private String nomCan;

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getNomCan() {
        return nomCan;
    }

    public void setNomCan(String nomCan) {
        this.nomCan = nomCan;
    }

    public int INSERT() {
        int rowCount;

        String querys = "SELECT idArt FROM artista where nomArt = '" + this.nomArt + "'";
        String idArtista = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idArtista = rs.getString("idArt");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idArt = Integer.parseInt(idArtista);

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

        String query = "INSERT INTO interpretacion(idArt, idCan)" +
                " VALUES(" + idArt + "," + idCan + ")";
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

        String querys = "SELECT idArt FROM artista where nomArt = '" + this.nomArt + "'";
        String idArtista = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idArtista = rs.getString("idArt");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idArt = Integer.parseInt(idArtista);

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

        String query = "DELETE FROM interpretacion WHERE idArt = " + idArt + " AND idCan = " + idCan;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public ObservableList<InterpretacionDAO> SELECTALL(int idArt){
        InterpretacionDAO objIn;
        String query = "SELECT * FROM interpretacion where idArt = " + idArt;
        ObservableList<InterpretacionDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {

                String querys = "SELECT nomArt FROM artista where idArt = " + idArt;
                String nombreArtista = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    if (rs.next())
                        nombreArtista = rs.getString("nomArt");
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

                objIn = new InterpretacionDAO();
                objIn.nomArt = nombreArtista;
                objIn.nomCan = nombreCancion;
                listaC.add(objIn);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }
}