package vallegrande.edu.pe.sgec.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3307/db_eventos?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a MySQL!");
            return conexion;

        } catch (SQLException e) {
            System.out.println("Error al conectar con MySQL:");
            System.out.println(e.getMessage());
            return null;
        }
    }
}