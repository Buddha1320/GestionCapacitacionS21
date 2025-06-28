package com.capacitacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaGestionAlumnos extends JFrame {
    private JComboBox<String> comboCarreras;
    private JComboBox<String> comboAlumnos;
    private JButton btnInscribir;

    public VentanaGestionAlumnos() {
        setTitle("Gestión de Alumnos por Carrera");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboCarreras = new JComboBox<>();
        comboAlumnos = new JComboBox<>();
        btnInscribir = new JButton("Pre Inscribir Alumno");

        panel.add(new JLabel("Seleccione Carrera:"));
        panel.add(comboCarreras);
        panel.add(new JLabel("Seleccione Alumno:"));
        panel.add(comboAlumnos);
        panel.add(new JLabel(""));
        panel.add(btnInscribir);

        add(panel);

        cargarCarreras();
        cargarAlumnos();

        btnInscribir.addActionListener(e -> inscribirAlumnoCarrera());
    }

    private void cargarCarreras() {
        comboCarreras.removeAllItems();
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_carrera, codigo_carrera, descripcion FROM carreras")) {
            while (rs.next()) {
                int id = rs.getInt("id_carrera");
                String codigo = rs.getString("codigo_carrera");
                String descripcion = rs.getString("descripcion");
                comboCarreras.addItem(id + " - " + codigo + " - " + descripcion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAlumnos() {
        comboAlumnos.removeAllItems();
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_alumno, nombre, apellido, dni FROM alumnos")) {
            while (rs.next()) {
                int id = rs.getInt("id_alumno");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                comboAlumnos.addItem(id + " - " + nombre + " " + apellido + " (DNI: " + dni + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inscribirAlumnoCarrera() {
        if (comboCarreras.getSelectedItem() == null || comboAlumnos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar carrera y alumno.");
            return;
        }

        String[] datosCarrera = comboCarreras.getSelectedItem().toString().split(" - ");
        int idCarrera = Integer.parseInt(datosCarrera[0].trim());
        String codigoCarrera = datosCarrera[1].trim();

        String[] datosAlumno = comboAlumnos.getSelectedItem().toString().split(" - ");
        int idAlumno = Integer.parseInt(datosAlumno[0].trim());

        try (Connection conn = conectar()) {
            // Validar si ya existe inscripción
            String sqlValidar = "SELECT COUNT(*) FROM alumnos_carreras WHERE fk_id_carrera = ? AND fk_id_alumno = ?";
            PreparedStatement psValidar = conn.prepareStatement(sqlValidar);
            psValidar.setInt(1, idCarrera);
            psValidar.setInt(2, idAlumno);
            ResultSet rsValidar = psValidar.executeQuery();
            if (rsValidar.next() && rsValidar.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "El alumno ya está inscripto en esta carrera.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Calcular siguiente número de secuencia
            String sqlCount = "SELECT COUNT(*) FROM alumnos_carreras WHERE fk_id_carrera = ?";
            PreparedStatement psCount = conn.prepareStatement(sqlCount);
            psCount.setInt(1, idCarrera);
            ResultSet rsCount = psCount.executeQuery();
            int secuencia = 100;
            if (rsCount.next()) {
                secuencia += rsCount.getInt(1);
            }
            String idAlumnoCarrera = codigoCarrera + secuencia;

            String sqlInsert = "INSERT INTO alumnos_carreras " +
                    "(id_alumnos_carreras, fk_id_carrera, fk_id_alumno, fecha_inscripcion, fk_estado_carrera, fecha_actualizacion) " +
                    "VALUES (?, ?, ?, NOW(), 1, NOW())";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, idAlumnoCarrera);
            psInsert.setInt(2, idCarrera);
            psInsert.setInt(3, idAlumno);

            psInsert.executeUpdate();

            JOptionPane.showMessageDialog(this, "Alumno inscrito correctamente con ID: " + idAlumnoCarrera);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al inscribir alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
    }
}

