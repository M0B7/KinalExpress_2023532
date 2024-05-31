package org.diegogarcia.bean;

/**
 * Nombre: Diego Fernando Garcia Galvez 2023532 PE5AM Fecha de creacion:
 * 15/04/2024
 */
public class DetalleCompras {

    private int idDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int idProducto;
    private int idCompra;

    public DetalleCompras() {

    }

    public DetalleCompras(int idDetalleFactura, double precioUnitario, int cantidad, int idProducto, int idCompra) {
        this.idDetalleFactura = idDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.idCompra = idCompra;
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

}
