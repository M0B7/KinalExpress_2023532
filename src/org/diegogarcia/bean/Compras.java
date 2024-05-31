package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class Compras {

    private int idCompra;
    private String fechaDocumento;
    private String descripcion;
    private double totalDocumento;

    public Compras() {

    }

    public Compras(int idCompra, String fechaDocumento, String descripcion, double totalDocumento) {
        this.idCompra = idCompra;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

}
