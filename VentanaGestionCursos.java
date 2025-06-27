package com.capacitacion.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaGestionCursos extends JFrame {
    private JComboBox<String> comboCarreras;
    private JComboBox<String> comboCursos;
    private JComboBox<String> comboAlumnos;
    private JTextField campoNota;
    private JButton btnGuardar;

    public VentanaGestionCursos() {
        setTitle("Crear Curso y Asignar Alumno");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        comboCarreras = new JComboBox<>();
        comboCursos = new JComboBox<>();
        comboAlumnos = new JComboBox<>();
        campoNota = new JTextField();
        btnGuardar = new JButton("Guardar Curso");

        panel.add(new JLabel("Carrera:"));
        panel.add(comboCarreras);
        
        panel.add(new JLabel("Cursos:"));
        panel.add(comboCursos);

        panel.add(new JLabel("Alumno:"));
        panel.add(comboAlumnos);

        panel.add(new JLabel("Nota:"));
        panel.add(campoNota);

        panel.add(new JLabel());
        panel.add(btnGuardar);

        add(panel);

        cargarCarreras();
        cargarAlumnos();

        comboCarreras.addActionListener(e -> cargarCursos());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCursoAlumno();
            }
        });
    }

    private void cargarCarreras() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_carrera, nombre FROM carreras")) {

            while (rs.next()) {
                comboCarreras.addItem(rs.getInt("id_carrera") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarCursos() {
        comboCursos.removeAllItems();
        if (comboCarreras.getSelectedItem() == null) return;

        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
             PreparedStatement ps = conn.prepareStatement("SELECT id_curso, nombre_materia, dia_semana, fecha_inicio, hora_inicio FROM cursos WHERE id_carrera = ?")) {

            ps.setInt(1, carreraId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comboCursos.addItem(rs.getInt("id_curso") + " - " + rs.getString("nombre_materia")+ " - " + rs.getString("dia_semana")+ " - " + rs.getDate("fecha_inicio")+ " - " + rs.getTime("hora_inicio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAlumnos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_alumno, nombre, apellido FROM alumnos")) {

            while (rs.next()) {
                comboAlumnos.addItem(rs.getInt("id_alumno") + " - " + rs.getString("nombre")+ " - " + rs.getString("apellido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarCursoAlumno() {
        if (comboCarreras.getSelectedItem() == null ||comboCursos.getSelectedItem() == null || comboAlumnos.getSelectedItem() == null || campoNota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int alumnoId = Integer.parseInt(comboAlumnos.getSelectedItem().toString().split(" - ")[0]);
        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        int cursoId = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);
        float nota;
        try {
            nota = Float.parseFloat(campoNota.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nota inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO alumnos_cursos (id_alumno, id_carrera, id_curso, nota, estado) VALUES (?, ?, ?, ?, 'cursando')")) {
            ps.setInt(1, alumnoId);
            ps.setInt(2, carreraId);
            ps.setInt(3, cursoId);
            ps.setFloat(4, nota);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Curso asignado con éxito");

            mostrarInscripciones();
            dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarInscripciones() {
        JFrame ventanaInscripciones = new JFrame("Inscripciones actuales");
        ventanaInscripciones.setSize(600, 300);
        ventanaInscripciones.setLocationRelativeTo(null);

        String[] columnas = {"ID", "Alumno", "Carrera", "Curso", "Nota", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT ac.id_inscripcion, CONCAT(a.nombre, ' ', a.apellido) AS alumno, " +
                             "ca.nombre AS carrera, " +
                             "CONCAT(c.id_curso, ' ', c.nombre_materia, ' ', c.dia_semana, ' ', c.fecha_inicio, ' ', c.hora_inicio) AS curso, " +
                             "ac.nota, ac.estado " +
                             "FROM alumnos_cursos ac " +
                             "JOIN alumnos a ON ac.id_alumno = a.id_alumno " +
                             "JOIN carreras ca ON ac.id_carrera = ca.id_carrera " +
                             "JOIN cursos c ON ac.id_curso = c.id_curso")) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_inscripcion"),
                        rs.getString("alumno"),
                        rs.getString("carrera"),
                        rs.getString("curso"),
                        rs.getFloat("nota"),
                        rs.getString("estado")
                };
                modeloTabla.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar inscripciones", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ventanaInscripciones.add(new JScrollPane(tabla));
        ventanaInscripciones.setVisible(true);
    }
}
