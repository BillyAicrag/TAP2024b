package org.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DetalleDAO {

    private int idVta;
    private String nomCan;
    private double costoCan;
    private String nomGen;
    private String nomArt;
    private String nomAl;

    public int getIdVta() {
        return idVta;
    }

    public void setIdVta(int idVta) {
        this.idVta = idVta;
    }

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

    public int INSERT() {
        int rowCount;

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

        String query = "INSERT INTO detalle_venta(idVta, idCan)" +
                " VALUES(" + this.idVta + "," + idCan + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        }catch (Exception e ){
            rowCount = 0;
            e.printStackTrace();
        }
        return rowCount;
    }

    /*
    public void DELETE(){

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

        String query = "DELETE FROM detalle_venta WHERE idVta = " + this.idVta + " AND idCan = " + idCan;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
     */

    public ObservableList<DetalleDAO> SELECTALL(int idVta){
        DetalleDAO objDet;
        String query = "SELECT * FROM detalle_venta where idVta = " + idVta;
        ObservableList<DetalleDAO> listaD = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while(res.next()) {

                String queryss = "SELECT * FROM cancion where idCan = " + res.getInt(2);
                String nombreCancion = "";
                double costoCancion = 0;
                String nombreGenero = "";
                String nombreArtista = "";
                String nombreAlbum = "";
                try {
                    Statement stm = Conexion.connection.createStatement();
                    ResultSet rs = stm.executeQuery(queryss);
                    if (rs.next())
                        nombreCancion = rs.getString("nomCan");
                        costoCancion = Double.parseDouble(rs.getString("costoCan"));
                    /*
                    ----------------------------------------------------------------------------------------------------
                     */
                    String querys = "SELECT nomGen FROM genero where idGen = " + rs.getInt(6);
                    try {
                        Statement st = Conexion.connection.createStatement();
                        ResultSet r = st.executeQuery(querys);
                        if (r.next())
                            nombreGenero = r.getString("nomGen");
                    }catch (Exception e ){
                        e.printStackTrace();
                    }

                    String querysss = "SELECT * FROM interpretacion where idCan = " + rs.getInt(1);
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

                    String queryssss = "SELECT * FROM coleccion where idCan = " + rs.getInt(1);
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
                    ----------------------------------------------------------------------------------------------------
                     */



                }catch (Exception e ){
                    e.printStackTrace();
                }

                objDet = new DetalleDAO();
                objDet.idVta = idVta;
                objDet.nomCan = nombreCancion;
                objDet.costoCan = costoCancion;
                objDet.nomGen = nombreGenero;
                objDet.nomArt = nombreArtista;
                objDet.nomAl = nombreAlbum;
                listaD.add(objDet);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return listaD;
    }
}