package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez
 * 2023532
 * PE5AM
 * Fecha de creacion: 15/04/2024
 */

public class Empleados {
    private int idEmpleado;
    private String nombresEmpleado;
    private String apellidosEmpleado;
    private double sueldo;
    private String turno;
    
    
    public Empleados(){
        
    }
    
    public Empleados(int idEmpleado, String nombresEmpleado, String apellidosEmpleado, double sueldo, String turno){
        this.idEmpleado = idEmpleado;
        this.nombresEmpleado = nombresEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.sueldo = sueldo;
        this.turno = turno;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombresEmpleado() {
        return nombresEmpleado;
    }

    public void setNombresEmpleado(String nombresEmpleado) {
        this.nombresEmpleado = nombresEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    
    
    
    
    
    
    
}



