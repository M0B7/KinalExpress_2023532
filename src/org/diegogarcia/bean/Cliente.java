package org.diegogarcia.bean;

public class Cliente {
    

    private int clienteId;
    private String nombreClientes;
    private String apellidoClientes;
    private String nitClientes;
    private String telefonoCliente;
    private String direccionCliente;
    private String correoCliente;

    public Cliente(int clienteId, String nombreClientes, String apellidoClientes, String nitClientes, String telefonoCliente, String direccionCliente, String correoCliente) {
        this.clienteId = clienteId;
        this.nombreClientes = nombreClientes;
        this.apellidoClientes = apellidoClientes;
        this.nitClientes = nitClientes;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.correoCliente = correoCliente;
    }

    public Cliente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreClientes;
    }

    public void setNombreCliente(String nombreClientes) {
        this.nombreClientes = nombreClientes;
    }

    public String getApellidoCliente() {
        return apellidoClientes;
    }

    public void setApellidoCliente(String apellidoClientes) {
        this.apellidoClientes = apellidoClientes;
    }

    public String getNitCliente() {
        return nitClientes;
    }

    public void setNitCliente(String nitClientes) {
        this.nitClientes = nitClientes;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }
    
    
}

