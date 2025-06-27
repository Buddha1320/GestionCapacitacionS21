package com.capacitacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    // Atributos de los componentes de la interfaz
    private JPanel panelPrincipal;
    private JLabel etiquetaTitulo;
    private JButton btnGestionCarreras;
    private JButton btnGestionAlumnos;
    private JButton btnGestionCursos;
    private JButton btnSalir;

    public VentanaPrincipal() {
        super("Sistema de Gestión de Capacitación Corporativa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Centra la ventana

        // Crear el panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255));

        // Título superior
        etiquetaTitulo = new JLabel("Bienvenido al Sistema de Capacitación", SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        etiquetaTitulo.setForeground(new Color(25, 25, 112));
        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(240, 248, 255));

        btnGestionCarreras = new JButton("Gestión de Carreras");
        btnGestionCarreras.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnGestionCarreras.setBackground(new Color(100, 149, 237));
        btnGestionCarreras.setForeground(Color.WHITE);
        btnGestionCarreras.setFocusPainted(false);

        btnGestionAlumnos = new JButton("Gestión de Alumnos");
        btnGestionAlumnos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnGestionAlumnos.setBackground(new Color(100, 149, 237));
        btnGestionAlumnos.setForeground(Color.WHITE);
        btnGestionAlumnos.setFocusPainted(false);

        btnGestionCursos = new JButton("Gestión de Cursos");
        btnGestionCursos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnGestionCursos.setBackground(new Color(100, 149, 237));
        btnGestionCursos.setForeground(Color.WHITE);
        btnGestionCursos.setFocusPainted(false);

        btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnSalir.setBackground(new Color(255, 99, 71));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);

        // Agregar los botones al panel
        panelBotones.add(btnGestionCarreras);
        panelBotones.add(btnGestionAlumnos);
        panelBotones.add(btnGestionCursos);
        panelBotones.add(btnSalir);

        // Agregar panel de botones al centro del panel principal
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Agregar el panel principal a la ventana
        getContentPane().add(panelPrincipal);

        // Acciones de los botones
        btnSalir.addActionListener(e -> {
            System.out.println("Saliendo de la aplicación...");
            System.exit(0);
        });

        btnGestionCarreras.addActionListener(e -> {
            System.out.println("Botón 'Gestión de Carreras' clicado.");
            // new VentanaGestionCarreras().setVisible(true);
        });

        btnGestionAlumnos.addActionListener(e -> {
            System.out.println("Botón 'Gestión de Alumnos' clicado.");
        });

        btnGestionCursos.addActionListener(e -> {
            new VentanaGestionCursos().setVisible(true);
            System.out.println("Botón 'Gestión de Cursos' clicado.");
        });
    }

    // Método principal solo si querés probar esta ventana sola
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}



/*
import javax.swing.JFrame;       // Para la ventana principal
import javax.swing.JPanel;       // Para un panel que contendrá otros componentes
import javax.swing.JLabel;       // Para etiquetas de texto
import javax.swing.JButton;      // Para botones
import javax.swing.SwingConstants; // Para alinear texto en etiquetas
import java.awt.BorderLayout;    // Para organizar componentes en la ventana
import java.awt.Font;            // Para cambiar la fuente del texto
import java.awt.Color;           // Para definir colores
import java.awt.event.ActionEvent; // Para manejar eventos de botón
import java.awt.event.ActionListener;
import javax.swing.JTree; // Interfaz para manejar eventos

public class VentanaPrincipal extends JFrame { // La clase extiende JFrame para ser una ventana
 // Atributos de los componentes de la interfaz
 private JPanel panelPrincipal;
 private JLabel etiquetaTitulo;
 private JButton btnGestionCarreras;
 private JButton btnGestionAlumnos;
 private JButton btnGestionCursos;
 private JButton btnSalir;
  public VentanaPrincipal() {
     // 1. Configuración de la Ventana Principal (JFrame)
     super("Sistema de Gestión de Capacitación Corporativa"); // Título de la ventana
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Qué hacer al cerrar la ventana (salir de la aplicación)
     setSize(1000, 700); // Tamaño inicial de la ventana (ancho, alto)
     setLocationRelativeTo(null); // Centra la ventana en la pantalla
   
     // 2. Creación del Panel Principal (JPanel)
     paneladd(panelBotones, BorderLayout.CENTER);Principal = new JPanel();
     panelPrincipal.setLayout(new BorderLayout()); // Usamos un layout para organizar los componentes
     panelPrincipal.setBackground(new Color(240, 248, 255)); // Un color de fondo suave
   
     // 3. Creación y Configuración del Título
     etiquetaTitulo = new JLabel("Bienvenido al Sistema de Capacitación Corporativa", SwingConstants.CENTER); // Texto centrado
     etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente, estilo, tamaño
     etiquetaTitulo.setForeground(new Color(25, 25, 112)); // Color de texto azul oscuro
     panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH); // Agrega el título en la parte superior del panel
    
     // 4. Creación y Configuración de los Botones
     JPanel panelBotones = new JPanel(); // Un nuevo panel para agrupar los botones
     panelBotones.setBackground(new Color(240, 248, 255)); // Mismo color de fondo
    
     // Podemos usar otro layout para los botones, por ahora usaremos el default (FlowLayout)
     btnGestionCarreras = new JButton("Gestión de Carreras");
     btnGestionCarreras.setFont(new Font("Segoe UI", Font.PLAIN, 16));
     btnGestionCarreras.setBackground(new Color(100, 149, 237)); // Azul claro
     btnGestionCarreras.setForeground(Color.WHITE);
     btnGestionCarreras.setFocusPainted(false); // Quita el borde de foco al hacer clic
    
     btnGestionAlumnos = new JButton("Gestión de Alumnos");
     btnGestionAlumnos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
     btnGestionAlumnos.setBackground(new Color(100, 149, 237));
     btnGestionAlumnos.setForeground(Color.WHITE);
     btnGestionAlumnos.setFocusPainted(false);
    
     btnGestionCursos = new JButton("Gestión de Cursos");
     btnGestionCursos.setFont(new Font("Segoe UI", Font.PLAIN, 16));
     btnGestionCursos.setBackground(new Color(100, 149, 237));
     btnGestionCursos.setForeground(Color.WHITE);
     btnGestionCursos.setFocusPainted(false);
    
     btnSalir = new JButton("Salir");
     btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
     btnSalir.setBackground(new Color(255, 99, 71)); // Rojo tomate
     btnSalir.setForeground(Color.WHITE);
     btnSalir.setFocusPainted(false);
    
     // 5. Agregar los botones al panel de botones
     panelBotones.add(btnGestionCarreras);
     panelBotones.add(btnGestionAlumnos);
     panelBotones.add(btnGestionCursos);
     panelBotones.add(btnSalir);
    
     // Agrega el panel de botones al centro del panel principal
     panelPrincipal.add(panelBotones, BorderLayout.CENTER);
     // 6. Agregar el panel principal a la ventana
     getContentPane().add(panelPrincipal);
    
     // 7. Configuración de Listeners (Manejo de eventos)
     // Cuando se haga clic en el botón Salir
     btnSalir.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             System.out.println("Saliendo de la aplicación...");
             System.exit(0); // Termina la aplicación
         }
     });
     // Para los otros botones, por ahora solo mostraremos un mensaje
     btnGestionCarreras.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             System.out.println("Botón 'Gestión de Carreras' clicado.");
             // Aquí, más adelante, puedo abrir una nueva ventana para gestión de carreras
             // Ej: new VentanaGestionCarreras().setVisible(true);
         }
     });
     btnGestionAlumnos.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             System.out.println("Botón 'Gestión de Alumnos' clicado.");
         }
     });
     btnGestionCursos.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
       	  new VentanaGestionCursos().setVisible(true);
             System.out.println("Botón 'Gestión de Cursos' clicado.");
         }
     });
    
 }
 // Método principal para probar la ventana (opcional, podrías llamarlo desde MainApp)
 public static void main(String[] args) {
     // Ejecutar la interfaz en el Event Dispatch Thread (EDT)
     // Esto es crucial para la seguridad de hilos en Swing
     javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
             VentanaPrincipal ventana = new VentanaPrincipal();
             ventana.setVisible(true); // Hace visible la ventana
         }
     });
 }
}*/
