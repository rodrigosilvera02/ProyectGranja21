package com.example.rodrigo.proyectgranja;

import android.graphics.Bitmap;

/**
 * Created by Rodrigo on 30/09/2016.
 */

public class listadoProducto {
    private String NombreProducto;
    private String TipoProducto;
    private String CalidadProducto;
    private String NombreGranja;
    private String PrecioProducto;
    private String ImgProducto;

    public String getImgProducto() {
        return ImgProducto;
    }

    public void setImgProducto(String imgProducto) {
        ImgProducto = imgProducto;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return TipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        TipoProducto = tipoProducto;
    }

    public String getCalidadProducto() {
        return CalidadProducto;
    }

    public void setCalidadProducto(String calidadProducto) {
        CalidadProducto = calidadProducto;
    }

    public String getNombreGranja() {
        return NombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        NombreGranja = nombreGranja;
    }

    public String getPrecioProducto() {
        return PrecioProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        PrecioProducto = precioProducto;
    }


    public listadoProducto(String nombreProducto, String tipoProducto, String calidadProducto, String nombreGranja, String precioProducto, String imgProducto) {
        NombreProducto = nombreProducto;
        TipoProducto = tipoProducto;
        CalidadProducto = calidadProducto;
        NombreGranja = nombreGranja;
        PrecioProducto = precioProducto;
        ImgProducto = imgProducto;
    }

    public listadoProducto() {
    }



}
