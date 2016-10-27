package com.example.rodrigo.proyectgranja.Logica;

/**
 * Created by Rodrigo on 24/10/2016.
 */

public class BolProd {

    private Integer id;
    private Boleta boleta;
    private Prodgranja prodgranja;
    private int cantidad;
    private String estado;

    public BolProd() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boleta getBoleta() {
        return boleta;
    }

    public void setBoleta(Boleta boleta) {
        this.boleta = boleta;
    }

    public Prodgranja getProdgranja() {
        return prodgranja;
    }

    public void setProdgranja(Prodgranja prodgranja) {
        this.prodgranja = prodgranja;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
