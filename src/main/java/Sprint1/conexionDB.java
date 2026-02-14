package Sprint1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionDB {
    // Ajusta tu usuario y contraseña de postgres aquí
    private static final String URL = "jdbc:postgresql://localhost:5432/supermercado_db";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "momo55";

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }
}
