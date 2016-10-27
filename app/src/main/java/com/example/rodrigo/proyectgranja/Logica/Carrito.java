package com.example.rodrigo.proyectgranja.Logica;

/**
 * Created by Rodrigo on 24/10/2016.
 */

public class Carrito {
    private Integer id;
    private Cliente cliente;
    private Granja granja;
    private String NombreGranja;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Granja getGranja() {
        return granja;
    }

    public void setGranja(Granja granja) {
        this.granja = granja;
    }

    public String getNombreGranja() {
        return NombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        NombreGranja = nombreGranja;
    }

    public Carrito(Integer id, Cliente cliente, Granja granja, String nombreGranja) {
        this.id = id;
        this.cliente = cliente;
        this.granja = granja;
        NombreGranja = nombreGranja;
    }

    public Carrito() {
    }
}
