package Sprint1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vistaProductos extends JFrame {

    private productoDAO dao;
    private JTable tablaProductos;

    public vistaProductos() {
        dao = new productoDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Supermercado - Sprint 1");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- PESTAÑA 1: ALTA DE PRODUCTOS [cite: 35, 48] ---
        JPanel panelAlta = new JPanel(new GridLayout(4, 2, 10, 10));
        panelAlta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNombre = new JTextField();
        JTextField txtProveedor = new JTextField();
        JButton btnGuardar = new JButton("Dar de Alta");
        JLabel lblResultadoAlta = new JLabel("");

        panelAlta.add(new JLabel("Nombre del Producto:"));
        panelAlta.add(txtNombre); // [cite: 150]
        panelAlta.add(new JLabel("Proveedor:"));
        panelAlta.add(txtProveedor); // [cite: 152]
        panelAlta.add(new JLabel("")); // Espacio vacío
        panelAlta.add(btnGuardar);
        panelAlta.add(new JLabel("Estado:"));
        panelAlta.add(lblResultadoAlta);

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String prov = txtProveedor.getText();
            if (!nombre.isEmpty() && !prov.isEmpty()) {
                int clave = dao.altaProducto(nombre, prov);
                if (clave != -1) {
                    lblResultadoAlta.setText("Éxito. Clave asignada: " + clave); // [cite: 51, 163]
                    lblResultadoAlta.setForeground(new Color(0, 150, 0));
                    txtNombre.setText("");
                    txtProveedor.setText("");
                    actualizarTabla(); // Refrescar la tabla de consultas automáticamente
                } else {
                    lblResultadoAlta.setText("Error al guardar.");
                }
            } else {
                lblResultadoAlta.setText("Llene todos los campos.");
                lblResultadoAlta.setForeground(Color.RED);
            }
        });

        // --- PESTAÑA 2: CONSULTA DE PRODUCTOS [cite: 30, 282] ---
        JPanel panelConsulta = new JPanel(new BorderLayout());
        tablaProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        JButton btnRefrescar = new JButton("Actualizar Lista");

        panelConsulta.add(scrollPane, BorderLayout.CENTER);
        panelConsulta.add(btnRefrescar, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> actualizarTabla());

        // --- PESTAÑA 3: BAJA DE PRODUCTOS [cite: 32, 324] ---
        JPanel panelBaja = new JPanel(new FlowLayout());
        JTextField txtClaveBaja = new JTextField(10);
        JButton btnEliminar = new JButton("Dar de Baja");
        
        panelBaja.add(new JLabel("Ingrese Clave del Producto a eliminar:"));
        panelBaja.add(txtClaveBaja); // [cite: 328]
        panelBaja.add(btnEliminar);

        btnEliminar.addActionListener(e -> {
            try {
                int clave = Integer.parseInt(txtClaveBaja.getText());
                boolean exito = dao.bajaProducto(clave);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "El producto fue dado de baja exitosamente."); // [cite: 330, 361]
                    txtClaveBaja.setText("");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un producto con esa clave.", "Error", JOptionPane.ERROR_MESSAGE); // [cite: 329, 364]
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido.");
            }
        });

        // Agregar pestañas
        tabbedPane.addTab("Alta Producto", panelAlta);
        tabbedPane.addTab("Consultar Productos", panelConsulta);
        tabbedPane.addTab("Baja Producto", panelBaja);

        add(tabbedPane);
        
        // Cargar datos iniciales
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaProductos.setModel(dao.consultarProductos());
    }

    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new vistaProductos().setVisible(true);
        });
    }
}
