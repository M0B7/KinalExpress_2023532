package org.diegogarcia.bean;


public class Facturas {
    
    private int idFactura;
    private String estado;
    private double totalFactura;
    private String fechaFactura;
    
    public Facturas(){
        
    }
    
    public Facturas(int idFactura, String estado, double totalFactura, String fechaFactura){
        this.idFactura = idFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
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
    
    
}
