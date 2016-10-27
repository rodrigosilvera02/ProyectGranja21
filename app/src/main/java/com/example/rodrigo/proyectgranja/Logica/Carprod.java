package com.example.rodrigo.proyectgranja.Logica;

import com.example.rodrigo.proyectgranja.Manager.mnCarrito;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

/**
 * Created by Rodrigo on 21/10/2016.
 */

public class Carprod {
    private int id;
    private mnCarrito carrito;
    private mnGranjaProducto prodgranja;
    private String nomProd;
    private String imgProd;
    private int cantidad;

    public Carprod() {
    }

    public Carprod(int id, mnCarrito carrito, mnGranjaProducto prodgranja, String nomProd, String imgProd, int cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.prodgranja = prodgranja;
        this.nomProd = nomProd;
        this.imgProd = imgProd;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public mnCarrito getCarrito() {
        return carrito;
    }

    public void setCarrito(mnCarrito carrito) {
        this.carrito = carrito;
    }

    public mnGranjaProducto getProdgranja() {
        return prodgranja;
    }

    public void setProdgranja(mnGranjaProducto prodgranja) {
        this.prodgranja = prodgranja;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
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
}
