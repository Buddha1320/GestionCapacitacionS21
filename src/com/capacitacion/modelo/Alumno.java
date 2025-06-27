package com.capacitacion.modelo;

import java.time.LocalDate; // Importamos la clase LocalDate para fechas
public class Alumno {
  private int idAlumno;
  private String dni;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private LocalDate fechaNacimiento; // Para almacenar la fecha de nacimiento
  public Alumno(int idAlumno, String dni, String nombre, String apellido, String email, String telefono, LocalDate fechaNacimiento) {
      this.idAlumno = idAlumno;
      this.dni = dni;
      this.nombre = nombre;
      this.apellido = apellido;
      this.email = email;
      this.telefono = telefono;
      this.fechaNacimiento = fechaNacimiento;
  }
  // Getters
  public int getIdAlumno() {
      return idAlumno;
  }
  public String getDni() {
      return dni;
  }
  public String getNombre() {
      return nombre;
  }
  public String getApellido() {
      return apellido;
  }
  public String getEmail() {
      return email;
  }
  public String getTelefono() {
      return telefono;
  }
  public LocalDate getFechaNacimiento() {
      return fechaNacimiento;
  }
  // Setters
  public void setIdAlumno(int idAlumno) {
      this.idAlumno = idAlumno;
  }
  public void setDni(String dni) {
      this.dni = dni;
  }
  public void setNombre(String nombre) {
      this.nombre = nombre;
  }
  public void setApellido(String apellido) {
      this.apellido = apellido;
  }
  public void setEmail(String email) {
      this.email = email;
  }
  public void setTelefono(String telefono) {
      this.telefono = telefono;
  }
  public void setFechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
  }
  @Override
  public String toString() {
      return "Alumno{" +
             "idAlumno=" + idAlumno +
             ", dni='" + dni + '\'' +
             ", nombre='" + nombre + '\'' +
             ", apellido='" + apellido + '\'' +
             ", email='" + email + '\'' +
             ", telefono='" + telefono + '\'' +
             ", fechaNacimiento=" + fechaNacimiento +
             '}';
  }
}


