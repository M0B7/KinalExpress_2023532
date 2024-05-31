package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class TipoProducto {

    private int idTipoProducto;
    private String descripcion;

    public TipoProducto() {

    }

    public TipoProducto(int idTipoProducto, String descripcion) {
        this.idTipoProducto = idTipoProducto;
        this.descripcion = descripcion;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
