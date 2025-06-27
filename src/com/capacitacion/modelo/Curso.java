package com.capacitacion.modelo;

import java.time.LocalDate;
import java.time.LocalTime; // Para la hora
import java.util.ArrayList; // Para la lista de alumnos
import java.util.List;      // Para la interfaz List
public class Curso {
   private int idCurso;
   private String nombreMateria; // Puedes asociarlo a una clase Materia más adelante
   private Carrera carreraAsociada; // Relación con la clase Carrera
   private LocalDate fechaInicio;
   private LocalDate fechaFin;
   private LocalTime horaInicio;
   private LocalTime horaFin;
   private String diaSemana; // Ej: "Lunes", "Miércoles", "Viernes"
   private int capacidadMaxima;
   private List<Alumno> alumnosInscritos; // Lista de alumnos inscritos en este curso
   public Curso(int idCurso, String nombreMateria, Carrera carreraAsociada, LocalDate fechaInicio, LocalDate fechaFin,
                LocalTime horaInicio, LocalTime horaFin, String diaSemana, int capacidadMaxima) {
       this.idCurso = idCurso;
       this.nombreMateria = nombreMateria;
       this.carreraAsociada = carreraAsociada;
       this.fechaInicio = fechaInicio;
       this.fechaFin = fechaFin;
       this.horaInicio = horaInicio;
       this.horaFin = horaFin;
       this.diaSemana = diaSemana;
       this.capacidadMaxima = capacidadMaxima;
       this.alumnosInscritos = new ArrayList<>(); // Inicializamos la lista vacía
   }
   // Getters
   public int getIdCurso() {
       return idCurso;
   }
   public String getNombreMateria() {
       return nombreMateria;
   }
   public Carrera getCarreraAsociada() {
       return carreraAsociada;
   }
   public LocalDate getFechaInicio() {
       return fechaInicio;
   }
   public LocalDate getFechaFin() {
       return fechaFin;
   }
   public LocalTime getHoraInicio() {
       return horaInicio;
   }
   public LocalTime getHoraFin() {
       return horaFin;
   }
   public String getDiaSemana() {
       return diaSemana;
   }
   public int getCapacidadMaxima() {
       return capacidadMaxima;
   }
   public List<Alumno> getAlumnosInscritos() {
       return alumnosInscritos;
   }
   // Setters
   public void setIdCurso(int idCurso) {
       this.idCurso = idCurso;
   }
   public void setNombreMateria(String nombreMateria) {
       this.nombreMateria = nombreMateria;
   }
   public void setCarreraAsociada(Carrera carreraAsociada) {
       this.carreraAsociada = carreraAsociada;
   }
   public void setFechaInicio(LocalDate fechaInicio) {
       this.fechaInicio = fechaInicio;
   }
   public void setFechaFin(LocalDate fechaFin) {
       this.fechaFin = fechaFin;
   }
   public void setHoraInicio(LocalTime horaInicio) {
       this.horaInicio = horaInicio;
   }
   public void setHoraFin(LocalTime horaFin) {
       this.horaFin = horaFin;
   }
   public void setDiaSemana(String diaSemana) {
       this.diaSemana = diaSemana;
   }
   public void setCapacidadMaxima(int capacidadMaxima) {
       this.capacidadMaxima = capacidadMaxima;
   }
   // Métodos para gestionar alumnos inscritos
   public boolean inscribirAlumno(Alumno alumno) {
       if (alumnosInscritos.size() < capacidadMaxima && !alumnosInscritos.contains(alumno)) {
           alumnosInscritos.add(alumno);
           System.out.println("Alumno " + alumno.getNombre() + " inscrito en el curso " + nombreMateria);
           return true;
       } else if (alumnosInscritos.contains(alumno)) {
           System.out.println("El alumno " + alumno.getNombre() + " ya está inscrito en este curso.");
           return false;
       } else {
           System.out.println("El curso " + nombreMateria + " ha alcanzado su capacidad máxima.");
           return false;
       }
   }
   public boolean desinscribirAlumno(Alumno alumno) {
       if (alumnosInscritos.remove(alumno)) {
           System.out.println("Alumno " + alumno.getNombre() + " desinscrito del curso " + nombreMateria);
           return true;
       } else {
           System.out.println("El alumno " + alumno.getNombre() + " no estaba inscrito en el curso " + nombreMateria);
           return false;
       }
   }
   @Override
   public String toString() {
       return "Curso{" +
              "idCurso=" + idCurso +
              ", nombreMateria='" + nombreMateria + '\'' +
              ", carreraAsociada=" + carreraAsociada.getNombre() + // Solo mostramos el nombre de la carrera
              ", fechaInicio=" + fechaInicio +
              ", fechaFin=" + fechaFin +
              ", horaInicio=" + horaInicio +
              ", horaFin=" + horaFin +
              ", diaSemana='" + diaSemana + '\'' +
              ", capacidadMaxima=" + capacidadMaxima +
              ", alumnosInscritos=" + alumnosInscritos.size() + " de " + capacidadMaxima + // Solo el número de alumnos
              '}';
   }
}

