package Sprint1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class productoDAO {

    // Tarea #1.1.- Alta de productos
    public int altaProducto(String nombre, String proveedor) {
        String sql = "INSERT INTO productos (nombre, proveedor) VALUES (?, ?) RETURNING clave_prod";
        int claveGenerada = -1;

        try (Connection conn = conexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, proveedor);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                claveGenerada = rs.getInt(1); // Recuperamos la clave autogenerada
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return claveGenerada;
    }

    // Tarea #1.2.- Consulta de productos s
    // Retorna un modelo para usar directamente en la tabla de la GUI
    public DefaultTableModel consultarProductos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Clave");
        modelo.addColumn("Nombre");
        modelo.addColumn("Proveedor");

        String sql = "SELECT * FROM productos ORDER BY clave_prod ASC";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("clave_prod");
                fila[1] = rs.getString("nombre"); // [cite: 288]
                fila[2] = rs.getString("proveedor"); // [cite: 289]
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return modelo;
    }

    // Tarea #1.3.- Baja de productos [cite: 19, 31]
    public boolean bajaProducto(int claveProd) {
        String sql = "DELETE FROM productos WHERE clave_prod = ?";
        boolean eliminado = false;

        try (Connection conn = conexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, claveProd);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                eliminado = true; // [cite: 330]
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return eliminado;
    }
}
