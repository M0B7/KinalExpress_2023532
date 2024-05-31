package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class TelefonoProveedor {

    private int idTelefonoProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int idProveedores;

    public TelefonoProveedor() {

    }

    public TelefonoProveedor(int idTelefonoProveedor, String numeroPrincipal, String numeroSecundario, String obersvaciones, int idProveeedores) {
        this.idTelefonoProveedor = idTelefonoProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.idProveedores = idProveedores;

    }

    public int getIdTelefonoProveedor() {
        return idTelefonoProveedor;
    }

    public void setIdTelefonoProveedor(int idTelefonoProveedor) {
        this.idTelefonoProveedor = idTelefonoProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdProveedores() {
        return idProveedores;
    }

    public void setIdProveedores(int idProveedores) {
        this.idProveedores = idProveedores;
    }

}
