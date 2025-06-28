package com.capacitacion.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

//S21 - Clase para gestionar cursos y asignación de notas.
//Nota: Aún podría refactorizarse en varias clases... pero por ahora queda así.

public class VentanaGestionCursos extends JFrame {

 private JComboBox<String> comboCarreras;
 private JComboBox<String> comboCursos;
 private JComboBox<String> comboAlumnos;
 private JComboBox<String> comboNotas;
 private JButton botonInscribir, botonAsignarNota, botonEstadoCarrera, botonGuardarNota;
 private JTable tablaInscripciones;
 private DefaultTableModel modeloTabla;

 public VentanaGestionCursos() {
     setTitle("Gestión de Cursos");
     setSize(850, 600);
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     setLayout(new BorderLayout(10, 10));

     // S21 Panel superior: combos de carrera y curso
     JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 5, 5));
     panelSuperior.setBorder(BorderFactory.createTitledBorder("Carrera y Curso"));

     comboCarreras = new JComboBox<>();
     comboCursos = new JComboBox<>();

     panelSuperior.add(new JLabel("Carrera:"));
     panelSuperior.add(comboCarreras);
     panelSuperior.add(new JLabel("Curso:"));
     panelSuperior.add(comboCursos);

     add(panelSuperior, BorderLayout.NORTH);

     // Panel central: combos de alumnos y botones
     JPanel panelCentral = new JPanel();
     panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
     panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

     JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
     comboAlumnos = new JComboBox<>();
     botonInscribir = crearBotonPequeno("Inscribir Alumno");
     botonInscribir.setEnabled(false);  // Por ahora deshabilitado
     fila1.add(new JLabel("Alumnos Pre Inscriptos:"));
     fila1.add(comboAlumnos);
     fila1.add(botonInscribir);

     JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
     botonAsignarNota = crearBotonPequeno("Asignar Nota");
     botonGuardarNota = crearBotonPequeno("Guardar Nota");
     botonGuardarNota.setEnabled(false);
     comboNotas = new JComboBox<>();
     comboNotas.setEnabled(false);
     comboNotas.setPreferredSize(new Dimension(80, 25));
     botonEstadoCarrera = crearBotonPequeno("Estado Carrera");
     fila2.add(botonAsignarNota);
     fila2.add(comboNotas);
     fila2.add(botonGuardarNota);
     fila2.add(botonEstadoCarrera);

     panelCentral.add(fila1);
     panelCentral.add(Box.createVerticalStrut(10));
     panelCentral.add(fila2);

     add(panelCentral, BorderLayout.CENTER);

     // Tabla de inscripciones
     String[] columnas = {"Seleccionar", "Legajo", "Apellido", "Nombre", "Inscripción", "Nota", "Estado"};
     modeloTabla = new DefaultTableModel(null, columnas) {
         @Override
         public Class<?> getColumnClass(int col) {
             return col == 0 ? Boolean.class : String.class;
         }

         @Override
         public boolean isCellEditable(int row, int col) {
             return col == 0;  // solo el checkbox editable
         }
     };
     tablaInscripciones = new JTable(modeloTabla);
     tablaInscripciones.setRowHeight(25);
     tablaInscripciones.setPreferredScrollableViewportSize(new Dimension(820, 250));
     JScrollPane scrollTabla = new JScrollPane(tablaInscripciones);
     scrollTabla.setBorder(BorderFactory.createTitledBorder("Inscripciones"));
     add(scrollTabla, BorderLayout.SOUTH);

     // Listeners básicos
     comboAlumnos.addActionListener(e -> {
         boolean activar = comboAlumnos.getSelectedIndex() > 0;
         botonInscribir.setEnabled(activar);
     });

     botonInscribir.addActionListener(e -> inscribirAlumno());
     botonAsignarNota.addActionListener(e -> asignarNotaSeleccionado());
     botonGuardarNota.addActionListener(e -> guardarNota());
     botonEstadoCarrera.addActionListener(e -> mostrarEstadoCarreraSeleccionado());

     // Carga inicial
     cargarCarreras();
     comboCarreras.addActionListener(e -> {
         cargarCursos();
         cargarAlumnosPorCarrera();
         actualizarTabla();
     });
     comboCursos.addActionListener(e -> actualizarTabla());
 }

 private JButton crearBotonPequeno(String texto) {
     JButton btn = new JButton(texto);
     btn.setPreferredSize(new Dimension(140, 30));
     return btn;
 }

 private void cargarCarreras() {
     comboCarreras.removeAllItems();
     try (Connection conn = conectar();
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT id_carrera, descripcion FROM carreras")) {

         while (rs.next()) {
             comboCarreras.addItem(rs.getInt("id_carrera") + " - " + rs.getString("descripcion"));
         }
     } catch (SQLException e) {
         e.printStackTrace();  // TODO: Cambiar a logger
     }
 }

 private void cargarCursos() {
     comboCursos.removeAllItems();
     if (comboCarreras.getSelectedItem() == null) return;

     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT id_curso, nombre_materia, CONCAT(dia_semana, ' - ', fecha_inicio, ' - ', hora_inicio) AS info " +
                          "FROM cursos WHERE id_carrera = ?")) {
         ps.setInt(1, idCarrera);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             comboCursos.addItem(rs.getInt("id_curso") + " - " + rs.getString("nombre_materia") + " - " + rs.getString("info"));
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 private void cargarAlumnosPorCarrera() {
     comboAlumnos.removeAllItems();
     comboAlumnos.addItem("0 - Todos");  // Default
     if (comboCarreras.getSelectedItem() == null) return;

     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT a.id_alumno, a.nombre, a.apellido " +
                          "FROM alumnos a " +
                          "JOIN alumnos_carreras ac ON a.id_alumno = ac.fk_id_alumno " +
                          "WHERE ac.fk_id_carrera = ? AND ac.fk_estado_carrera IN (1,2,3,4,5)")) {
         ps.setInt(1, idCarrera);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             comboAlumnos.addItem(rs.getInt("id_alumno") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 private void inscribirAlumno() {
     if (comboAlumnos.getSelectedIndex() <= 0 || comboCarreras.getSelectedItem() == null || comboCursos.getSelectedItem() == null) {
         JOptionPane.showMessageDialog(this, "Debe seleccionar carrera, curso y alumno.");
         return;
     }

     int idAlumno = Integer.parseInt(comboAlumnos.getSelectedItem().toString().split(" - ")[0]);
     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     int idCurso = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);

     try (Connection conn = conectar()) {
         // Chequear si ya existe inscripción
         PreparedStatement psCheck = conn.prepareStatement(
                 "SELECT COUNT(*) FROM alumnos_cursos WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ?");
         psCheck.setInt(1, idAlumno);
         psCheck.setInt(2, idCarrera);
         psCheck.setInt(3, idCurso);
         ResultSet rsCheck = psCheck.executeQuery();

         if (rsCheck.next() && rsCheck.getInt(1) > 0) {
             JOptionPane.showMessageDialog(this, "El alumno ya está inscripto en este curso.");
             return;
         }

         // Obtener descripción estado
         String estado = "cursando";  // Default por si falla consulta ya q pongo un solo estado
         PreparedStatement psEstado = conn.prepareStatement(
                 "SELECT descripcion_estado_materia FROM estado_materia WHERE id_estado_materia = 2");
         ResultSet rsEstado = psEstado.executeQuery();
         if (rsEstado.next()) {
             estado = rsEstado.getString("descripcion_estado_materia");
         }

         // Insertar inscripción
         PreparedStatement psInsert = conn.prepareStatement(
                 "INSERT INTO alumnos_cursos (id_alumno, id_carrera, id_curso, fk_estado_id, estado, fecha_inscripcion, fecha_update) " +
                         "VALUES (?, ?, ?, 2, ?, NOW(), NOW())");
         psInsert.setInt(1, idAlumno);
         psInsert.setInt(2, idCarrera);
         psInsert.setInt(3, idCurso);
         psInsert.setString(4, estado);
         psInsert.executeUpdate();

         JOptionPane.showMessageDialog(this, "Alumno inscrito correctamente.");
         actualizarTabla();

     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 private void actualizarTabla() {
     modeloTabla.setRowCount(0);
     if (comboCarreras.getSelectedItem() == null || comboCursos.getSelectedItem() == null) return;

     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     int idCurso = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);

     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT ca.id_alumnos_carreras AS Legajo, a.apellido, a.nombre, ac.fecha_inscripcion, ac.nota, em.descripcion_estado_materia " +
                          "FROM alumnos_cursos ac " +
                          "JOIN alumnos a ON ac.id_alumno = a.id_alumno " +
                          "JOIN estado_materia em ON ac.fk_estado_id = em.id_estado_materia " +
                          "JOIN alumnos_carreras ca ON a.id_alumno = ca.fk_id_alumno AND ac.id_carrera = ca.fk_id_carrera " +
                          "WHERE ac.id_carrera = ? AND ac.id_curso = ?")) {
         ps.setInt(1, idCarrera);
         ps.setInt(2, idCurso);
         ResultSet rs = ps.executeQuery();

         while (rs.next()) {
             modeloTabla.addRow(new Object[]{
                     false,
                     rs.getString("Legajo"),
                     rs.getString("apellido"),
                     rs.getString("nombre"),
                     rs.getString("fecha_inscripcion"),
                     rs.getObject("nota"),
                     rs.getString("descripcion_estado_materia")
             });
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 private void asignarNotaSeleccionado() {
     // Este método se puede separar en otra clase de servicio
     Integer idAlumno = obtenerAlumnoSeleccionadoEnTabla();
     if (idAlumno == null) {
         JOptionPane.showMessageDialog(this, "Seleccione un alumno.");
         return;
     }

     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     int idCurso = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);

     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT fk_estado_id FROM alumnos_cursos WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ?")) {
         ps.setInt(1, idAlumno);
         ps.setInt(2, idCarrera);
         ps.setInt(3, idCurso);
         ResultSet rs = ps.executeQuery();

         if (rs.next()) {
             int estado = rs.getInt("fk_estado_id");
             if (estado == 2 || estado == 3) {
                 comboNotas.removeAllItems();
                 comboNotas.setEnabled(true);
                 botonGuardarNota.setEnabled(true);

                 try (Statement stmt = conn.createStatement();
                      ResultSet rsNotas = stmt.executeQuery("SELECT notas FROM notas")) {
                     while (rsNotas.next()) {
                         comboNotas.addItem(rsNotas.getString("notas"));
                     }
                 }
             } else {
                 JOptionPane.showMessageDialog(this, "No se puede asignar nota en este estado.");
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

  // Traigo id alumno seleccionado en la tabla por checkbox
 private Integer obtenerAlumnoSeleccionadoEnTabla() {
     // se puede optimizar usando indices ??...
     for (int i = 0; i < modeloTabla.getRowCount(); i++) {
         Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);
         if (Boolean.TRUE.equals(seleccionado)) {
             String legajo = (String) modeloTabla.getValueAt(i, 1);
             try (Connection conn = conectar();
                  PreparedStatement ps = conn.prepareStatement(
                          "SELECT fk_id_alumno FROM alumnos_carreras WHERE id_alumnos_carreras = ?")) {
                 ps.setString(1, legajo);
                 ResultSet rs = ps.executeQuery();
                 if (rs.next()) {
                     return rs.getInt("fk_id_alumno");
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
     return null;
 }
 
 // Traigo id alumno seleccionado en la tabla por checkbox
/*  private Integer obtenerAlumnoSeleccionadoEnTabla() {
     for (int i = 0; i < modeloTabla.getRowCount(); i++) {
         Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);
         if (seleccionado != null && seleccionado) {
             String legajoStr = (String) modeloTabla.getValueAt(i, 1);
             try {
                 return Integer.parseInt(legajoStr);
             } catch (NumberFormatException e) {
                 e.printStackTrace();
             }
         }
     }
     return null;
 }
*/


 private void guardarNota() {
     if (comboNotas.getSelectedItem() == null) return;

     Integer idAlumno = obtenerAlumnoSeleccionadoEnTabla();
     if (idAlumno == null) {
         JOptionPane.showMessageDialog(this, "Seleccione un alumno para guardar la nota.");
         return;
     }

     int nota = Integer.parseInt(comboNotas.getSelectedItem().toString());
     int estado = (nota >= 7) ? 4 : 5;

     int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     int idCurso = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);

     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "UPDATE alumnos_cursos SET nota = ?, fk_estado_id = ?, fecha_update = NOW() " +
                          "WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ? AND fk_estado_id = 2")) {
         ps.setInt(1, nota);
         ps.setInt(2, estado);
         ps.setInt(3, idAlumno);
         ps.setInt(4, idCarrera);
         ps.setInt(5, idCurso);
         ps.executeUpdate();
         actualizarTabla();
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 // Estado carrera usando alumno seleccionado en tabla
 private void mostrarEstadoCarreraSeleccionado() {
     Integer alumnoId = obtenerAlumnoSeleccionadoEnTabla();
     if (alumnoId == null) {
         JOptionPane.showMessageDialog(this, "Seleccione un alumno en la tabla para validar estado de carrera.");
         return;
     }
     int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
     String nombreCarrera = comboCarreras.getSelectedItem().toString().split(" - ")[1];
     // resto del método igual que el anterior calcularEstadoCarrera, adaptado para usar alumnoId obtenido
     // Calcular estado final de la carrera
     ArrayList<Integer> estados = new ArrayList<>();
     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement("SELECT fk_estado_id FROM alumnos_cursos WHERE id_alumno = ? AND id_carrera = ?")) {
         ps.setInt(1, alumnoId);
         ps.setInt(2, carreraId);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             estados.add(rs.getInt("fk_estado_id"));
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     String estadoCarrera = "Regular";
     int estadoFinal = 2;
     long desaprobados = estados.stream().filter(e -> e == 5).count();
     boolean todosAprobados = estados.stream().allMatch(e -> e == 4);
     if (todosAprobados) {
         estadoCarrera = "Egresado";
         estadoFinal = 3;
     } else if (desaprobados >= 4) {
         estadoCarrera = "Libre";
         estadoFinal = 4;
     }
     // Actualizar estado en BD
     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "UPDATE alumnos_carreras SET fk_estado_carrera = ?, fecha_actualizacion = NOW() WHERE fk_id_alumno = ? AND fk_id_carrera = ?")) {
         ps.setInt(1, estadoFinal);
         ps.setInt(2, alumnoId);
         ps.setInt(3, carreraId);
         ps.executeUpdate();
     } catch (SQLException e) {
         e.printStackTrace();
     }
     // Crear ventana personalizada
     JDialog dialog = new JDialog(this, "Estado de Carrera", true);
     dialog.setSize(600, 400);
     dialog.setLocationRelativeTo(this);
     dialog.setLayout(new BorderLayout(10, 10));
     JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
     panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
     panelSuperior.add(new JLabel("Carrera: " + nombreCarrera));
     panelSuperior.add(new JLabel("Estado de la Carrera: " + estadoCarrera));
     dialog.add(panelSuperior, BorderLayout.NORTH);
     DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Materia", "Nota", "Estado"}, 0);
     try (Connection conn = conectar();
          PreparedStatement ps = conn.prepareStatement(
                  "SELECT c.nombre_materia, ac.nota, em.descripcion_estado_materia " +
                          "FROM alumnos_cursos ac " +
                          "JOIN cursos c ON ac.id_curso = c.id_curso " +
                          "JOIN estado_materia em ON ac.fk_estado_id = em.id_estado_materia " +
                          "WHERE ac.id_alumno = ? AND ac.id_carrera = ?")) {
         ps.setInt(1, alumnoId);
         ps.setInt(2, carreraId);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
             modelo.addRow(new Object[]{
                     rs.getString("Nombre_materia"),
                     rs.getObject("Nota"),
                     rs.getString("Descripcion_estado_materia")
             });
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     JTable tabla = new JTable(modelo);
     tabla.setFillsViewportHeight(true);
     JScrollPane scroll = new JScrollPane(tabla);
     scroll.setPreferredSize(new Dimension(550, 160));
     dialog.add(scroll, BorderLayout.CENTER);
     JPanel panelBoton = new JPanel();
     JButton btnCerrar = new JButton("OK");
     btnCerrar.addActionListener(e -> dialog.dispose());
     panelBoton.add(btnCerrar);
     dialog.add(panelBoton, BorderLayout.SOUTH);
     dialog.setVisible(true);
 }

 private Connection conectar() throws SQLException {
     // Nota: Mejor usar pool de conexiones
     return DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
 }
}

