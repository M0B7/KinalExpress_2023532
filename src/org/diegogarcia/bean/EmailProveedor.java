package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez
 * 2023532
 * PE5AM
 * Fecha de creacion: 15/04/2024
 */

public class EmailProveedor {
    private int idEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    
    public EmailProveedor(){
        
    }
    
    public EmailProveedor(int idEmailProveedor, String emailProveedor, String descripcion){
        this.idEmailProveedor = idEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        
    }

    public int getIdEmailProveedor() {
        return idEmailProveedor;
    }

    public void setIdEmailProveedor(int idEmailProveedor) {
        this.idEmailProveedor = idEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
