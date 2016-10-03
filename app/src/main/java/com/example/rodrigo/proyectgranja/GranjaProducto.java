package com.example.rodrigo.proyectgranja;

/**
 * Created by Rodrigo on 30/09/2016.
 */

public class GranjaProducto {
    private Integer id;
    private int strock;
    private String calidad;
    private String tipoProducto;
    private String unidad;
    private float precio;
    private boolean zafra;
    private Float precioZafra;
    private boolean borrrado;

    private String NomProd;
    private String ImgProg;

    private int idGranja;
    private String NombreGranja;
    private String Localidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Float GeoLat;
    private Float GeoLong;

    public int getStrock() {
        return strock;
    }

    public void setStrock(int strock) {
        this.strock = strock;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isZafra() {
        return zafra;
    }

    public void setZafra(boolean zafra) {
        this.zafra = zafra;
    }

    public Float getPrecioZafra() {
        return precioZafra;
    }

    public void setPrecioZafra(Float precioZafra) {
        this.precioZafra = precioZafra;
    }

    public boolean isBorrrado() {
        return borrrado;
    }

    public void setBorrrado(boolean borrrado) {
        this.borrrado = borrrado;
    }

    public String getNomProd() {
        return NomProd;
    }

    public void setNomProd(String nomProd) {
        NomProd = nomProd;
    }

    public String getImgProg() {
        return ImgProg;
    }

    public void setImgProg(String imgProg) {
        ImgProg = imgProg;
    }

    public int getIdGranja() {
        return idGranja;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    public String getNombreGranja() {
        return NombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        NombreGranja = nombreGranja;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public Float getGeoLat() {
        return GeoLat;
    }

    public void setGeoLat(Float geoLat) {
        GeoLat = geoLat;
    }

    public Float getGeoLong() {
        return GeoLong;
    }

    public void setGeoLong(Float geoLong) {
        GeoLong = geoLong;
    }

    public GranjaProducto() {
    }
}
