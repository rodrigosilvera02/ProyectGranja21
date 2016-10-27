package com.example.rodrigo.proyectgranja.Logica;

/**
 * Created by Rodrigo on 05/10/2016.
 */

public class Productor {
    private Integer id;
    private String nombre;
    private String imagen;
    private boolean borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public Productor() {
    }
}
