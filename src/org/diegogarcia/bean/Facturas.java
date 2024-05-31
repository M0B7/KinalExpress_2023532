package org.diegogarcia.bean;

public class Facturas {

    private int idFactura;
    private String estado;
    private double totalFactura;
    private String fechaFactura;
    private int idEmpleado;
    private int idCliente;

    public Facturas() {

    }

    public Facturas(int idFactura, String estado, double totalFactura, String fechaFactura, int idEmpleado, int idCliente) {
        this.idFactura = idFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
