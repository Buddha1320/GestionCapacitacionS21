package com.capacitacion.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class VentanaGestionCursos extends JFrame {
    private JComboBox<String> comboCarreras;
    private JComboBox<String> comboCursos;
    private JComboBox<String> comboAlumnos;
    private JComboBox<String> comboNotas;
    private JButton btnInscribir, btnAsignarNota, btnEstadoCarrera, btnGuardarNota;
    private JTable tablaInscripciones;
    private DefaultTableModel modeloTabla;

    public VentanaGestionCursos() {
        setTitle("Gestión de Cursos");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // BLOQUE SUPERIOR: Carrera y Curso en 2 filas
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 5, 5));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Carrera y Curso"));
        comboCarreras = new JComboBox<>();
        comboCursos = new JComboBox<>();
        panelSuperior.add(new JLabel("Carrera:"));
        panelSuperior.add(comboCarreras);
        panelSuperior.add(new JLabel("Curso:"));
        panelSuperior.add(comboCursos);
        add(panelSuperior, BorderLayout.NORTH);

        // BLOQUE CENTRAL: Alumnos y botones en 2 filas
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fila 1 - Alumno + Combo + Botón Inscribir
        JPanel panelFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        comboAlumnos = new JComboBox<>();
        btnInscribir = crearBotonPequeño("Inscribir Alumno");
        btnInscribir.setEnabled(false); // se habilita sólo si alumno seleccionado
        panelFila1.add(new JLabel("Alumno:"));
        panelFila1.add(comboAlumnos);
        panelFila1.add(btnInscribir);

        // Fila 2 - Botones asignar nota, guardar nota, estado carrera alineados a la derecha
        JPanel panelFila2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        btnAsignarNota = crearBotonPequeño("Asignar Nota");
        btnGuardarNota = crearBotonPequeño("Guardar Nota");
        btnGuardarNota.setEnabled(false);
        comboNotas = new JComboBox<>();
        comboNotas.setEnabled(false);
        comboNotas.setPreferredSize(new Dimension(80, 25));
        btnEstadoCarrera = crearBotonPequeño("Estado Carrera");
        panelFila2.add(btnAsignarNota);
        panelFila2.add(comboNotas);
        panelFila2.add(btnGuardarNota);
        panelFila2.add(btnEstadoCarrera);

        panelCentral.add(panelFila1);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(panelFila2);

        add(panelCentral, BorderLayout.CENTER);

        // BLOQUE INFERIOR: Tabla con checkbox para seleccionar alumnos
        String[] columnas = {"Seleccionar", "Legajo", "Apellido", "Nombre", "Inscripción", "Nota", "Estado"};
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // solo la primera columna editable para checkbox
            }
        };
        tablaInscripciones = new JTable(modeloTabla);
        tablaInscripciones.setRowHeight(25);
        tablaInscripciones.setPreferredScrollableViewportSize(new Dimension(820, 25 * 10)); // altura para 10 filas visibles
        JScrollPane scroll = new JScrollPane(tablaInscripciones);
        scroll.setBorder(BorderFactory.createTitledBorder("Inscripciones"));
        add(scroll, BorderLayout.SOUTH);

        // Listeners y lógica para activar botón inscribir si hay alumno seleccionado en combo
        comboAlumnos.addActionListener(e -> {
            boolean habilitar = comboAlumnos.getSelectedIndex() > 0;
            btnInscribir.setEnabled(habilitar);
        });

        // Acciones botones
        btnInscribir.addActionListener(e -> inscribirAlumno());
        btnAsignarNota.addActionListener(e -> asignarNotaSeleccionado());
        btnGuardarNota.addActionListener(e -> guardarNota());
        btnEstadoCarrera.addActionListener(e -> estadoCarreraSeleccionado());

        // Carga inicial
        cargarCarreras();
        comboCarreras.addActionListener(e -> {
            cargarCursos();
            cargarAlumnosPorCarrera();
            actualizarTablaInscripciones();
        });
        comboCursos.addActionListener(e -> actualizarTablaInscripciones());
    }

    private JButton crearBotonPequeño(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(140, 30));
        return btn;
    }
    
    // Obtiene id alumno seleccionado en la tabla por checkbox
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
    
    private Integer obtenerAlumnoSeleccionadoEnTabla() {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                String legajo = (String) modeloTabla.getValueAt(i, 1); // Ej: "RRHH003"
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
    
    // Métodos para cargar combobox y actualizar tabla (igual que antes) --
    private void cargarCarreras() {
        comboCarreras.removeAllItems();
        try (Connection conn = conectar();
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
        int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT id_curso, nombre_materia FROM cursos WHERE id_carrera = ?")) {
            ps.setInt(1, idCarrera);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comboCursos.addItem(rs.getInt("id_curso") + " - " + rs.getString("nombre_materia"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAlumnosPorCarrera() {
        comboAlumnos.removeAllItems();
        comboAlumnos.addItem("0 - Todos");
        if (comboCarreras.getSelectedItem() == null) return;
        int idCarrera = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT a.id_alumno, a.nombre, a.apellido " +
                             "FROM alumnos a JOIN alumnos_carreras ac ON a.id_alumno = ac.fk_id_alumno " +
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
            JOptionPane.showMessageDialog(this, "Seleccione carrera, curso y alumno.");
            return;
        }
        int alumnoId = Integer.parseInt(comboAlumnos.getSelectedItem().toString().split(" - ")[0]);
        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        int cursoId = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);
        try (Connection conn = conectar()) {
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM alumnos_cursos WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ?");
            check.setInt(1, alumnoId);
            check.setInt(2, carreraId);
            check.setInt(3, cursoId);
            ResultSet rsCheck = check.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "El alumno ya está inscripto en ese curso.");
                return;
            }
            PreparedStatement psEstado = conn.prepareStatement("SELECT descripcion_estado_materia FROM estado_materia WHERE id_estado_materia = 2");
            ResultSet rs = psEstado.executeQuery();
            String estado = "cursando";
            if (rs.next()) estado = rs.getString("descripcion_estado_materia");
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO alumnos_cursos (id_alumno, id_carrera, id_curso, fk_estado_id, estado, fecha_inscripcion, fecha_update) VALUES (?, ?, ?, 2, ?, NOW(), NOW())");
            ps.setInt(1, alumnoId);
            ps.setInt(2, carreraId);
            ps.setInt(3, cursoId);
            ps.setString(4, estado);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Alumno inscrito correctamente.");
            actualizarTablaInscripciones();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al inscribir.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaInscripciones() {
        modeloTabla.setRowCount(0);
        if (comboCarreras.getSelectedItem() == null || comboCursos.getSelectedItem() == null) return;
        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        int cursoId = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT ca.id_alumnos_carreras AS Legajo, a.apellido, a.nombre, ac.fecha_inscripcion, ac.nota, em.descripcion_estado_materia " +
                             "FROM alumnos_cursos ac " +
                             "JOIN alumnos a ON ac.id_alumno = a.id_alumno " +
                             "JOIN estado_materia em ON ac.fk_estado_id = em.id_estado_materia " +
                             "JOIN alumnos_carreras ca ON a.id_alumno = ca.fk_id_alumno AND ac.id_carrera = ca.fk_id_carrera " +
                             "WHERE ac.id_carrera = ? AND ac.id_curso = ?")) {
            ps.setInt(1, carreraId);
            ps.setInt(2, cursoId);
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

    // Nuevo método para usar checkbox seleccionado al asignar nota
    private void asignarNotaSeleccionado() {
        Integer alumnoId = obtenerAlumnoSeleccionadoEnTabla();
        if (alumnoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno en la tabla para asignar nota.");
            return;
        }
        // Aquí sigue lógica similar a habilitarAsignarNota anterior pero con alumnoId seleccionado
        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        int cursoId = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT fk_estado_id FROM alumnos_cursos WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ?")) {
            ps.setInt(1, alumnoId);
            ps.setInt(2, carreraId);
            ps.setInt(3, cursoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int estado = rs.getInt("fk_estado_id");
                if (estado == 2 || estado == 3) {
                    comboNotas.removeAllItems();
                    comboNotas.setEnabled(true);
                    btnGuardarNota.setEnabled(true);
                    try (Statement stmt = conn.createStatement();
                         ResultSet rsNotas = stmt.executeQuery("SELECT notas FROM notas")) {
                        while (rsNotas.next()) {
                            comboNotas.addItem(rsNotas.getString("notas"));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Solo se puede asignar nota si el alumno está en estado 'cursando' o 'regular'.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró inscripción para ese alumno en el curso seleccionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarNota() {
        if (comboNotas.getSelectedItem() == null) return;
        Integer alumnoId = obtenerAlumnoSeleccionadoEnTabla();
        if (alumnoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno en la tabla para guardar la nota.");
            return;
        }
        int carreraId = Integer.parseInt(comboCarreras.getSelectedItem().toString().split(" - ")[0]);
        int cursoId = Integer.parseInt(comboCursos.getSelectedItem().toString().split(" - ")[0]);
        int nota = Integer.parseInt(comboNotas.getSelectedItem().toString());
        int estado = (nota >= 7) ? 4 : 5;
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE alumnos_cursos SET nota = ?, fk_estado_id = ?, fecha_update = NOW() WHERE id_alumno = ? AND id_carrera = ? AND id_curso = ? AND fk_estado_id = 2")) {
            ps.setInt(1, nota);
            ps.setInt(2, estado);
            ps.setInt(3, alumnoId);
            ps.setInt(4, carreraId);
            ps.setInt(5, cursoId);
            ps.executeUpdate();
            actualizarTablaInscripciones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Estado carrera usando alumno seleccionado en tabla
    private void estadoCarreraSeleccionado() {
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
                        rs.getString("nombre_materia"),
                        rs.getObject("nota"),
                        rs.getString("descripcion_estado_materia")
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
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
    }
}
