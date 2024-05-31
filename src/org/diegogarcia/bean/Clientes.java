package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024 Fecha de Modificacion: 17/04, 23/04, 24/04, 30/04, 06/05, 07/05,
 * 8/5
 */
public class Clientes {

    private int idCliente;
    private String nitCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String correoCliente;

    public Clientes() {
    }

    public Clientes(int idCliente, String nitCliente, String nombresCliente, String apellidosCliente, String direccionCliente, String telefonoCliente, String correoCliente) {
        this.idCliente = idCliente;
        this.nitCliente = nitCliente;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.correoCliente = correoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    @Override
    public String toString() {
        return getIdCliente() + "   ";
    }
}
