package com.example.rodrigo.proyectgranja.Manager;

import android.os.Handler;

import com.example.rodrigo.proyectgranja.Logica.Carprod;
import com.example.rodrigo.proyectgranja.Logica.Carrito;
import com.example.rodrigo.proyectgranja.WebService.WSCarrito;
import com.example.rodrigo.proyectgranja.WebService.WSProductoCarrito;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 21/10/2016.
 */

public class mnCarrito {
    private int idCliente;
    private int idCarrito;
    private int cantidad;
    private int idProdCarrito;
    private int idGranja;
    private String NombreGranja;
    private String Localidad;
    private int idProdGran;
    private String NombreProdGranja;
        private String imgProd;



    ArrayList<Carrito> listarCarrito=new ArrayList<>();
    ArrayList<Carprod> listarProdCarrito=new ArrayList<Carprod>();



    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getNombreProdGranja() {
        return NombreProdGranja;
    }

    public void setNombreProdGranja(String nombreProdGranja) {
        NombreProdGranja = nombreProdGranja;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public String getNombreGranja() {
        return NombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        NombreGranja = nombreGranja;
    }

    public int getIdGranja() {
        return idGranja;
    }

    public void setIdGranja(int idGranja) {
        this.idGranja = idGranja;
    }

    public int getIdProdGran() {
        return idProdGran;
    }

    public void setIdProdGran(int idProdGran) {
        this.idProdGran = idProdGran;
    }

    public int getIdProdCarrito() {
        return idProdCarrito;
    }

    public void setIdProdCarrito(int idProdCarrito) {
        this.idProdCarrito = idProdCarrito;
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

    public mnCarrito() {
    }

    public mnCarrito(int idProdCarrito, int cantidad, int idCarrito, int idGranja, String nombreGranja, String localidad, int idProdGran, String nombreProdGranja, String imgProd) {
        this.idProdCarrito = idProdCarrito;
        this.cantidad = cantidad;
        this.idCarrito = idCarrito;
        this.idGranja = idGranja;
        NombreGranja = nombreGranja;
        Localidad = localidad;
        this.idProdGran = idProdGran;
        NombreProdGranja = nombreProdGranja;
        this.imgProd = imgProd;
    }

    public void agregarProductoCarrito() throws IOException, XmlPullParserException {
        Handler handler = new Handler();
        final int[] idCarrito = new int[1];
        final WSCarrito wsCarrito = new WSCarrito();
        final WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    idCarrito[0] =wsCarrito.existeCarrito(getIdCliente(), getIdGranja());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            };
        };
        thread4.start();
        try {
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        handler.post(new Runnable() {
            @Override
            public void run() {

        if(idCarrito[0] >0)
        {

        }else
        {
            try {
                wsCarrito.agregarCarrito(getIdCliente(), getIdGranja());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            try {
                idCarrito[0] =wsCarrito.existeCarrito(getIdCliente(), getIdGranja());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

        }
                try {
                    wsProductoCarrito.nuevoProductoCarrito(idCarrito[0], getIdProdGran(), getCantidad());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void modificarProductoCarrito() throws IOException, XmlPullParserException {
        WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
        wsProductoCarrito.modificarProdCar(getIdProdCarrito(), getCantidad());
    }



    }
