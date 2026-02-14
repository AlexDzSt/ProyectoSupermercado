package Sprint2;

import Sprint1.conexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class departamentoDAO {

    // ===============================
    // ALTA DE DEPARTAMENTO
    // ===============================
    public String altaDepartemento(String clave, String nombre, String responsable) {

    String sql = "INSERT INTO departamentos (clave_dpto, nombre, responsable) "
               + "VALUES (?, ?, ?) RETURNING clave_dpto";

    String claveGenerada = null;

    try (Connection conn = conexionDB.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, clave);
        pstmt.setString(2, nombre);
        pstmt.setString(3, responsable);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            claveGenerada = rs.getString("clave_dpto");
        }

    } catch (SQLException e) {
        System.out.println("Error al insertar departamento: " + e.getMessage());
    }

    return claveGenerada;
}



    // ===============================
    // CONSULTAR DEPARTAMENTOS
    // ===============================
    public DefaultTableModel consultarDepartamentos() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Clave Departamento");
        modelo.addColumn("Nombre");
        modelo.addColumn("Responsable");

        String sql = "SELECT * FROM departamentos ORDER BY clave_dpto";

        try (Connection conn = conexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Object[] fila = new Object[3];

                fila[0] = rs.getString("clave_dpto");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("responsable");

                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar departamentos: " + e.getMessage());
        }

        return modelo;
    }


    // ===============================
    // BAJA DE DEPARTAMENTO
    // ===============================
    public boolean bajaDepartamento(String claveDpto) {

    String sql = "DELETE FROM departamentos WHERE clave_dpto = ?";

    boolean eliminado = false;

    try (Connection conn = conexionDB.conectar();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, claveDpto);

        int filas = pstmt.executeUpdate();

        if (filas > 0) {
            eliminado = true;
        }

    } catch (SQLException e) {
        System.out.println("Error al eliminar departamento: " + e.getMessage());
    }

    return eliminado;
}


}