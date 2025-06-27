package com.capacitacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;

    public VentanaLogin() {
        setTitle("Login de Usuario");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel de campos de usuario y contraseña
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelCampos.add(new JLabel("Usuario (Email):"));
        campoUsuario = new JTextField();
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        campoPassword = new JPasswordField();
        panelCampos.add(campoPassword);
        add(panelCampos, BorderLayout.CENTER);

        // Panel del botón
        JPanel panelBoton = new JPanel();
        botonLogin = new JButton("Ingresar");
        panelBoton.add(botonLogin);
        add(panelBoton, BorderLayout.SOUTH);

        // Acción del botón
        botonLogin.addActionListener(e -> validarCredenciales());
    }

    private void validarCredenciales() {
        String usuario = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT email_usuarios FROM usuarios WHERE email_usuarios = ? AND psw_usuarios = ?")) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login exitoso. Bienvenido " + usuario, "Acceso correcto", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cierra la ventana de login
                new VentanaPrincipal().setVisible(true); // Abre la ventana principal
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection conectar() throws SQLException {
        // Asegurate de que el nombre de la base y usuario/clave coincidan con tu entorno
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        });
    }
}

/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VentanaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;

    public VentanaLogin() {
        setTitle("Login de Usuario");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panelCampos.add(new JLabel("Usuario (Email):"));
        campoUsuario = new JTextField();
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        campoPassword = new JPasswordField();
        panelCampos.add(campoPassword);
        add(panelCampos, BorderLayout.CENTER);

        // Panel de botón
        JPanel panelBoton = new JPanel();
        botonLogin = new JButton("Ingresar");
        panelBoton.add(botonLogin);
        add(panelBoton, BorderLayout.SOUTH);

        // Acción del botón
        botonLogin.addActionListener(e -> validarCredenciales());

        setVisible(true);
    }

    private void validarCredenciales() {
        String usuario = campoUsuario.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement("SELECT email_usuarios FROM usuarios WHERE email_usuarios = ? AND psw_usuarios = ?")) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login exitoso. Bienvenido " + usuario);
                dispose(); // cerrar login
                new VentanaPrincipal();
                /*new VentanaGestionCursos(); 
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la conexión", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/EFIP21", "root", "Gracias_mysql21");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin());
    }
}
*/
