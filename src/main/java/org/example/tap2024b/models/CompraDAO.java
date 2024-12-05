package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompraDAO {
    private String nomCan;
    private double costoCan;
    private String nomGen;
    private String nomArt;
    private String nomAl;

    public String getNomCan() {
        return nomCan;
    }

    public void setNomCan(String nomCan) {
        this.nomCan = nomCan;
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

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getNomAl() {
        return nomAl;
    }

    public void setNomAl(String nomAl) {
        this.nomAl = nomAl;
    }

    public ObservableList<CompraDAO> SELECTALL(){
        CompraDAO objCom;
        String query = "SELECT * FROM cancion";
        ObservableList<CompraDAO> listaC = FXCollections.observableArrayList();
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

                String queryss = "SELECT * FROM interpretacion where idCan = " + res.getInt(1);
                int idArtista;
                String nombreArtista = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(queryss);
                    if (rs.next()) {
                        idArtista = rs.getInt("idArt");
                        String queryssAl = "SELECT nomArt FROM artista where idArt = " + idArtista;
                        String nomArt;
                        try {
                            Statement st = Conexion.connection.createStatement();
                            ResultSet r = st.executeQuery(queryssAl);
                            if (r.next()) {
                                nomArt = r.getString("nomArt");
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

                String querysss = "SELECT * FROM coleccion where idCan = " + res.getInt(1);
                int idAlbum;
                String nombreAlbum = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querysss);
                    if (rs.next()) {
                        idAlbum = rs.getInt("idAl");
                        String queryssAl = "SELECT nomAl FROM album where idAl = " + idAlbum;
                        String nomAl;
                        try {
                            Statement st = Conexion.connection.createStatement();
                            ResultSet r = st.executeQuery(queryssAl);
                            if (r.next()) {
                                nomAl = r.getString("nomAl");
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

                objCom = new CompraDAO();
                objCom.nomCan = res.getString(2);
                objCom.costoCan = res.getDouble(5);
                objCom.nomGen = nombreGenero;
                objCom.nomArt = nombreArtista;
                objCom.nomAl = nombreAlbum;
                listaC.add(objCom);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }


    /*
    --------------------------------------------------------------------------------------------------------------------
     */
    public ObservableList<CompraDAO> SELECTALL(String artista, String album){
        CompraDAO objCom;
        String query = "SELECT * FROM cancion";
        ObservableList<CompraDAO> listaC = FXCollections.observableArrayList();
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

                String queryss = "SELECT * FROM interpretacion where idCan = " + res.getInt(1);
                int idArtista;
                String nombreArtista = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(queryss);
                    if (rs.next()) {
                        idArtista = rs.getInt("idArt");
                        String queryssAl = "SELECT nomArt FROM artista where idArt = " + idArtista;
                        String nomArt;
                        try {
                            Statement st = Conexion.connection.createStatement();
                            ResultSet r = st.executeQuery(queryssAl);
                            if (r.next()) {
                                nomArt = r.getString("nomArt");
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

                String querysss = "SELECT * FROM coleccion where idCan = " + res.getInt(1);
                int idAlbum;
                String nombreAlbum = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(querysss);
                    if (rs.next()) {
                        idAlbum = rs.getInt("idAl");
                        String queryssAl = "SELECT nomAl FROM album where idAl = " + idAlbum;
                        String nomAl;
                        try {
                            Statement st = Conexion.connection.createStatement();
                            ResultSet r = st.executeQuery(queryssAl);
                            if (r.next()) {
                                nomAl = r.getString("nomAl");
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

                objCom = new CompraDAO();
                objCom.nomCan = res.getString(2);
                objCom.costoCan = res.getDouble(5);
                objCom.nomGen = nombreGenero;
                objCom.nomArt = nombreArtista;
                objCom.nomAl = nombreAlbum;
                if (!album.equals("") && artista.equals("")) {
                    if(nombreAlbum.equals(album))
                        listaC.add(objCom);
                } else if (album.equals("") && !artista.equals("")) {
                    if(nombreArtista.equals(artista))
                        listaC.add(objCom);
                } else {
                    if(nombreAlbum.equals(album) && nombreArtista.equals(artista))
                        listaC.add(objCom);
                }
                    //listaC.add(objCom);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaC;
    }
}

