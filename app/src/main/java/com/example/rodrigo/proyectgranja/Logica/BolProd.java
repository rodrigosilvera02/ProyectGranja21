package com.example.rodrigo.proyectgranja.Logica;

/**
 * Created by Rodrigo on 24/10/2016.
 */

public class BolProd {

    private Integer id;
    private Boleta boleta;
    private Prodgranja prodgranja;
    private int idProdGran;
    private String nombreProd;
    private String imgProd;
    private int cantidad;
    private float precio;
    private float precioTotal;
    private String estado;

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

    public int getIdProdGran() {
        return idProdGran;
    }

    public void setIdProdGran(int idProdGran) {
        this.idProdGran = idProdGran;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public String getImgProd() {
        return imgProd;
    }

    public void setImgProd(String imgProd) {
        this.imgProd = imgProd;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BolProd() {
    }
}
