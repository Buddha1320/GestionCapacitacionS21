package com.capacitacion.main;

import com.capacitacion.modelo.Alumno;
import com.capacitacion.modelo.Carrera;
import com.capacitacion.modelo.Curso;
import java.time.LocalDate;
import java.time.LocalTime;
public class MainApp {
   public static void main(String[] args) {
       System.out.println("¡Bienvenido al Sistema de Gestión de Capacitación Corporativa!");
       // 1. Crear algunas Carreras
       System.out.println("\n--- Creando Carreras ---");
       Carrera desarrolloJava = new Carrera(1, "Desarrollo Java Empresarial", "Carrera completa para desarrolladores Java", 24);
       Carrera gestionProyectos = new Carrera(2, "Gestión de Proyectos con Scrum", "Formación en metodologías ágiles", 12);
       Carrera dataScience = new Carrera(3, "Introducción a Data Science", "Fundamentos de análisis de datos", 16);
       System.out.println(desarrolloJava);
       System.out.println(gestionProyectos);
       System.out.println(dataScience);
       // 2. Crear algunos Alumnos
       System.out.println("\n--- Creando Alumnos ---");
       Alumno alumno1 = new Alumno(101, "34567890", "Juan", "Pérez", "juan.perez@empresa.com", "1122334455", LocalDate.of(1990, 5, 15));
       Alumno alumno2 = new Alumno(102, "40123456", "María", "González", "maria.gonzalez@empresa.com", "1166778899", LocalDate.of(1988, 11, 22));
       Alumno alumno3 = new Alumno(103, "30987654", "Carlos", "Ramírez", "carlos.ramirez@empresa.com", "1155443322", LocalDate.of(1995, 3, 10));
       Alumno alumno4 = new Alumno(104, "39012345", "Ana", "López", "ana.lopez@empresa.com", "1199887766", LocalDate.of(1992, 7, 20));
       System.out.println(alumno1);
       System.out.println(alumno2);
       System.out.println(alumno3);
       System.out.println(alumno4);
       // 3. Crear algunos Cursos
       System.out.println("\n--- Creando Cursos ---");
       Curso cursoJavaBasico = new Curso(201, "Java Básico", desarrolloJava,
                                     LocalDate.of(2025, 7, 1), LocalDate.of(2025, 7, 30),
                                     LocalTime.of(9, 0), LocalTime.of(12, 0), "Lunes-Miércoles-Viernes", 3);
       Curso cursoScrum = new Curso(202, "Scrum Master", gestionProyectos,
                                    LocalDate.of(2025, 8, 5), LocalDate.of(2025, 8, 20),
                                    LocalTime.of(14, 0), LocalTime.of(17, 0), "Martes-Jueves", 2);
       System.out.println(cursoJavaBasico);
       System.out.println(cursoScrum);
       // 4. Inscribir Alumnos a Cursos
       System.out.println("\n--- Inscripción de Alumnos ---");
       cursoJavaBasico.inscribirAlumno(alumno1);
       cursoJavaBasico.inscribirAlumno(alumno2);
       cursoJavaBasico.inscribirAlumno(alumno3);
       cursoJavaBasico.inscribirAlumno(alumno4); // Este debería exceder la capacidad
       cursoScrum.inscribirAlumno(alumno3);
       cursoScrum.inscribirAlumno(alumno4);
       cursoScrum.inscribirAlumno(alumno1); // Este debería indicar que ya está inscrito (si se ejecuta después de la primera inscripción)
       System.out.println("\n--- Estado de los Cursos después de inscripciones ---");
       System.out.println(cursoJavaBasico);
       System.out.println(cursoScrum);
       // 5. Desinscribir un Alumno
       System.out.println("\n--- Desinscripción de Alumno ---");
       cursoJavaBasico.desinscribirAlumno(alumno2);
       cursoJavaBasico.desinscribirAlumno(alumno2); // Intentar desinscribir de nuevo
       System.out.println("\n--- Estado de los Cursos después de desinscripción ---");
       System.out.println(cursoJavaBasico);
   }
}
