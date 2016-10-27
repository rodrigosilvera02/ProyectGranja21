package com.example.rodrigo.proyectgranja.Logica;

/**
 * Created by Rodrigo on 24/10/2016.
 */

public class Cliente {
    private Integer id;
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente() {
    }

    public Cliente(Integer id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }
}
