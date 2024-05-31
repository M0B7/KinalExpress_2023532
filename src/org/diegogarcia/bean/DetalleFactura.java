package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class DetalleFactura {

    private int idDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int idFactura;
    private int idProducto;

    public DetalleFactura() {

    }

    public DetalleFactura(int idDetalleFactura, double precioUnitario, int cantidad, int idFactura, int idProducto) {
        this.idDetalleFactura = idDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
    }

    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

}
