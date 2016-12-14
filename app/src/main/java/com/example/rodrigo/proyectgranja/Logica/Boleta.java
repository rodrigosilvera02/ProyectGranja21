package com.example.rodrigo.proyectgranja.Logica;

import java.util.Date;

/**
 * Created by Rodrigo on 24/10/2016.
 */

public class Boleta {
    private Integer id;
    private Carrito carrito;
    private float precioTotal;
    private Date fecha;
    private boolean pediListo;
    private boolean borrado;
    private String Nombre;
    private String Apellido;
    private String Telefono;

    public Boleta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isPediListo() {
        return pediListo;
    }

    public void setPediListo(boolean pediListo) {
        this.pediListo = pediListo;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
