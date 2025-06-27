package com.capacitacion.modelo;

public class Carrera {
	   // Atributos (variables de instancia)
	   private int idCarrera;
	   private String nombre;
	   private String descripcion;
	   private int duracionSemanas;
	   // Constructor (método especial para crear objetos de esta clase)
	   public Carrera(int idCarrera, String nombre, String descripcion, int duracionSemanas) {
	       this.idCarrera = idCarrera;
	       this.nombre = nombre;
	       this.descripcion = descripcion;
	       this.duracionSemanas = duracionSemanas;
	   }
	   // Métodos Getters (para obtener el valor de los atributos)
	   public int getIdCarrera() {
	       return idCarrera;
	   }
	   public String getNombre() {
	       return nombre;
	   }
	   public String getDescripcion() {
	       return descripcion;
	   }
	   public int getDuracionSemanas() {
	       return duracionSemanas;
	   }
	   // Métodos Setters (para modificar el valor de los atributos)
	   public void setIdCarrera(int idCarrera) {
	       this.idCarrera = idCarrera;
	   }
	   public void setNombre(String nombre) {
	       this.nombre = nombre;
	   }
	   public void setDescripcion(String descripcion) {
	       this.descripcion = descripcion;
	   }
	   public void setDuracionSemanas(int duracionSemanas) {
	       this.duracionSemanas = duracionSemanas;
	   }
	   // Método toString (para una representación de cadena del objeto)
	   @Override
	   public String toString() {
	       return "Carrera{" +
	              "idCarrera=" + idCarrera +
	              ", nombre='" + nombre + '\'' +
	              ", descripcion='" + descripcion + '\'' +
	              ", duracionSemanas=" + duracionSemanas +
	              '}';
	   }
	}
