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
    private String nomAl;
    private String nomArt;

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

    public String getNomAl() {
        return nomAl;
    }

    public void setNomAl(String nomAl) {
        this.nomAl = nomAl;
    }

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public int INSERT() {
        int rowCount;

        String querys = "SELECT idGen FROM genero where nomGen = '" + this.nomGen + "'";
        String idGenero = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idGenero = rs.getString("idGen");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idGen = Integer.parseInt(idGenero);

        String query = "INSERT INTO cancion(nomCan, duracionCan, fechaCan, costoCan, idGen)" +
                " VALUES('" + this.nomCan + "','" + this.duracionCan + "','" + this.fechaCan + "'," + this.costoCan + "," + idGen + ")";
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

        String querys = "SELECT idGen FROM genero where nomGen = '" + this.nomGen + "'";
        String idGenero = "";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(querys);
            if (rs.next())
                idGenero = rs.getString("idGen");
        }catch (Exception e ){
            e.printStackTrace();
        }
        int idGen = Integer.parseInt(idGenero);

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

                String querys = "SELECT nomGen FROM genero where idGen = " + res.getInt(6);
                String nombreGenero = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querys);
                    if (rs.next())
                        nombreGenero = rs.getString("nomGen");
                }catch (Exception e ){
                    e.printStackTrace();
                }

                /*
                --------------------------------------------------------------------------------------------------------
                 */
                String nombreArtista = "";
                String querysss = "SELECT * FROM interpretacion where idCan = " + res.getInt(1);
                int idArtista;
                try {
                    Statement st = Conexion.connection.createStatement();
                    ResultSet r = st.executeQuery(querysss);
                    while (r.next()) {
                        idArtista = r.getInt("idArt");
                        String queryssAl = "SELECT nomArt FROM artista where idArt = " + idArtista;
                        String nomArt;
                        try {
                            Statement stt = Conexion.connection.createStatement();
                            ResultSet rr = stt.executeQuery(queryssAl);
                            if (rr.next()) {
                                nomArt = rr.getString("nomArt");
                                if (!nombreArtista.equals(""))
                                    nombreArtista += ", ";
                                nombreArtista += nomArt;
                            }
                        }catch (Exception e ){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                }

                String nombreAlbum = "";
                String queryssss = "SELECT * FROM coleccion where idCan = " + res.getInt(1);
                int idAlbum;
                try {
                    Statement st = Conexion.connection.createStatement();
                    ResultSet r = st.executeQuery(queryssss);
                    while (r.next()) {
                        idAlbum = r.getInt("idAl");
                        String queryssAl = "SELECT nomAl FROM album where idAl = " + idAlbum;
                        String nomAl;
                        try {
                            Statement stt = Conexion.connection.createStatement();
                            ResultSet rr = stt.executeQuery(queryssAl);
                            if (rr.next()) {
                                nomAl = rr.getString("nomAl");
                                if (!nombreAlbum.equals(""))
                                    nombreAlbum += ", ";
                                nombreAlbum += nomAl;
                            }
                        }catch (Exception e ){
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                }
                /*
                --------------------------------------------------------------------------------------------------------
                 */

                objCan = new CancionDAO();
                objCan.idCan = res.getInt(1);
                objCan.nomCan = res.getString(2);
                objCan.duracionCan = res.getString(3);
                objCan.fechaCan = res.getString(4);
                objCan.costoCan = res.getDouble(5);
                objCan.nomGen = nombreGenero;
                objCan.nomArt = nombreArtista;
                objCan.nomAl = nombreAlbum;
                listaC.add(objCan);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }
}