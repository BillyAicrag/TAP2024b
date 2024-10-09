package org.example.tap2024b.models;
import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.DriverManager;
import java.sql.Connection;

public class Conexion {
    static private String DB = "spotify";
    static private String USER = "admin";
    static private String PWD = "1234";
    static private String HOST = "localhost";
    static private String PORT = "3306";
    public static Connection connection;

    public static void crearConeccion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB, USER, PWD);
            System.out.println("Conexión establecida a la base de datos");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
