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
    private  int idproductoGranja;
    private int idGranja;
    private float latGranja;
    private float lonGranja;
    private int Strock;
    private Bitmap img ;
    private String Unidad;

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public int getStrock() {
        return Strock;
    }

    public void setStrock(int strock) {
        Strock = strock;
    }

    public float getLatGranja() {
        return latGranja;
    }

    public void setLatGranja(float latGranja) {
        this.latGranja = latGranja;
    }

    public float getLonGranja() {
        return lonGranja;
    }

    public void setLonGranja(float lonGranja) {
        this.lonGranja = lonGranja;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    private int idCliente;

    public int getIdGranja() {
        return idGranja;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    public int getIdproductoGranja() {
        return idproductoGranja;
    }

    public void setIdproductoGranja(int idproductoGranja) {
        this.idproductoGranja = idproductoGranja;
    }

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
