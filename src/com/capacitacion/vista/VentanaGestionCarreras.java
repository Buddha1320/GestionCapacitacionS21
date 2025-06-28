package com.capacitacion.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaGestionCarreras extends JFrame {
    private JTable tablaCarreras;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnModificar, btnEliminar;
    
    public VentanaGestionCarreras() {
        setTitle("Gestión de Carreras");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Descripción", "Semanas", "Código"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID no editable
            }
        };
        tablaCarreras = new JTable(modeloTabla);
        tablaCarreras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaCarreras);

        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarCarreras();

        btnAgregar.addActionListener(e -> agregarCarrera());
        btnModificar.addActionListener(e -> modificarCarrera());
        btnEliminar.addActionListener(e -> eliminarCarrera());
    }

    private void cargarCarreras() {
        modeloTabla.setRowCount(0);
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM carreras")) {
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id_carrera"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("duracion_semanas"),
                        rs.getString("codigo_carrera")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar carreras.");
        }
    }

    private void agregarCarrera() {
        JTextField nombreField = new JTextField();
        JTextArea descripcionField = new JTextArea(3, 20);
        JTextField semanasField = new JTextField();
        JTextField codigoField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Descripción:")); panel.add(new JScrollPane(descripcionField));
        panel.add(new JLabel("Duración semanas:")); panel.add(semanasField);
        panel.add(new JLabel("Código carrera:")); panel.add(codigoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar Carrera",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = conectar();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO carreras (nombre, descripcion, duracion_semanas, codigo_carrera) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, nombreField.getText());
                ps.setString(2, descripcionField.getText());
                ps.setInt(3, Integer.parseInt(semanasField.getText()));
                ps.setString(4, codigoField.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Carrera agregada.");
                cargarCarreras();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar carrera.");
            }
        }
    }

    private void modificarCarrera() {
        int fila = tablaCarreras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera para modificar.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        String descripcion = (String) modeloTabla.getValueAt(fila, 2);
        int semanas = Integer.parseInt(modeloTabla.getValueAt(fila, 3).toString());
        String codigo = (String) modeloTabla.getValueAt(fila, 4);

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE carreras SET nombre=?, descripcion=?, duracion_semanas=?, codigo_carrera=? WHERE id_carrera=?")) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setInt(3, semanas);
            ps.setString(4, codigo);
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Carrera modificada.");
            cargarCarreras();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar carrera.");
        }
    }

    private void eliminarCarrera() {
        int fila = tablaCarreras.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera para eliminar.");
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta carrera?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = conectar();
                 PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM carreras WHERE id_carrera = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Carrera eliminada.");
                cargarCarreras();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar carrera.");
            }
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
    }
}