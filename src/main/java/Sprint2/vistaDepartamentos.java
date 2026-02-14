package Sprint2;

import javax.swing.*;
import java.awt.*;

public class vistaDepartamentos extends JFrame {

    private departamentoDAO dao;
    private JTable tablaDepartamentos;

    public vistaDepartamentos() {
        dao = new departamentoDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Supermercado - Departamentos");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- PESTAÑA 1: ALTA DE DEPARTAMENTOS ---
        JPanel panelAlta = new JPanel(new GridLayout(5, 2, 10, 10));
        panelAlta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtClave = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtResponsable = new JTextField();
        JLabel lblResultadoAlta = new JLabel("");
        JButton btnGuardar = new JButton("Dar de Alta");

        panelAlta.add(new JLabel("Clave del Departamento:"));
        panelAlta.add(txtClave);
        panelAlta.add(new JLabel("Nombre del Departamento:"));
        panelAlta.add(txtNombre);
        panelAlta.add(new JLabel("Responsable:"));
        panelAlta.add(txtResponsable);
        panelAlta.add(new JLabel(""));
        panelAlta.add(btnGuardar);
        panelAlta.add(new JLabel(""));
        panelAlta.add(lblResultadoAlta);

        btnGuardar.addActionListener(e -> {
            String clave = txtClave.getText();
            String nombre = txtNombre.getText();
            String responsable = txtResponsable.getText();

            if (!clave.isEmpty() && !nombre.isEmpty() && !responsable.isEmpty()) {

                String resultado = dao.altaDepartemento(clave, nombre, responsable);

                if (resultado != null) {
                    lblResultadoAlta.setText("Departamento guardado correctamente.");
                    lblResultadoAlta.setForeground(new Color(0, 150, 0));

                    txtClave.setText("");
                    txtNombre.setText("");
                    txtResponsable.setText("");

                    actualizarTabla();
                } else {
                    lblResultadoAlta.setText("Error al guardar.");
                    lblResultadoAlta.setForeground(Color.RED);
                }

            } else {
                lblResultadoAlta.setText("Llene todos los campos.");
                lblResultadoAlta.setForeground(Color.RED);
            }
        });

        // --- PESTAÑA 2: CONSULTA DE DEPARTAMENTOS ---
        JPanel panelConsulta = new JPanel(new BorderLayout());
        tablaDepartamentos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaDepartamentos);
        JButton btnRefrescar = new JButton("Actualizar Lista");

        panelConsulta.add(scrollPane, BorderLayout.CENTER);
        panelConsulta.add(btnRefrescar, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> actualizarTabla());

        // --- PESTAÑA 3: BAJA DE DEPARTAMENTOS ---
        JPanel panelBaja = new JPanel(new FlowLayout());
        JTextField txtClaveBaja = new JTextField(10);
        JButton btnEliminar = new JButton("Dar de Baja");

        panelBaja.add(new JLabel("Ingrese Clave del Departamento a eliminar:"));
        panelBaja.add(txtClaveBaja);
        panelBaja.add(btnEliminar);

        btnEliminar.addActionListener(e -> {
            try {
                String clave = txtClaveBaja.getText();
                boolean exito = dao.bajaDepartamento(clave);


                if (exito) {
                    JOptionPane.showMessageDialog(this, "Departamento eliminado correctamente.");
                    txtClaveBaja.setText("");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se encontró un departamento con esa clave.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
            }
        });

        // Agregar pestañas
        tabbedPane.addTab("Alta Departamento", panelAlta);
        tabbedPane.addTab("Consultar Departamentos", panelConsulta);
        tabbedPane.addTab("Baja Departamento", panelBaja);

        add(tabbedPane);

        // Cargar datos iniciales
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaDepartamentos.setModel(dao.consultarDepartamentos());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new vistaDepartamentos().setVisible(true);
        });
    }
}
