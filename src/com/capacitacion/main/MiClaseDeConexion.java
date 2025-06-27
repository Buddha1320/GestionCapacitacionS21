package com.capacitacion.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; // Es importante importar esta clase para manejar excepciones SQL
// También podrías necesitar:
// import java.sql.Statement;
// import java.sql.ResultSet;

public class MiClaseDeConexion {

    // Información de la conexión a tu base de datos MySQL
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/"; 
    private static final String DB_NAME = "EFIP21"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Gracias_mysql21"; 

    public static void main(String[] args) {
        Connection conexion = null; // Inicializamos la conexión a null

        try {
            // 1. Cargar el driver JDBC (esto ya no es estrictamente necesario con JDBC 4.0+,
            //    pero es una buena práctica si tienes versiones antiguas o por claridad)
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Para MySQL Connector/J 8.0+
            // Class.forName("com.mysql.jdbc.Driver"); // Para MySQL Connector/J 5.x

            // 2. Establecer la conexión
            System.out.println("Intentando conectar a la base de datos...");
            conexion = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);

            if (conexion != null) {
                System.out.println("¡Conexión exitosa a la base de datos " + DB_NAME + "!");
                // Aquí puedes añadir más código para interactuar con la base de datos
                // (ejecutar consultas, insertar datos, etc.)
            }

        } catch (SQLException e) {
            // Manejo de errores de SQL
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            // Para depuración, puedes imprimir el stack trace completo
            // e.printStackTrace();
        } /*catch (ClassNotFoundException e) { // Solo si usas Class.forName()
            System.err.println("No se encontró el driver JDBC de MySQL: " + e.getMessage());
            e.printStackTrace();
        }*/ finally {
            // 3. Cerrar la conexión (¡muy importante para liberar recursos!)
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}

