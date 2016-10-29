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
    private int idGranja;
    private int idProdGran;

    private int idProdCarrito;
    private String nombProd;
    private String imgProd;
    private Handler handler = new Handler();
    private int cantidad;

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

    public String getNombProd() {
        return nombProd;
    }

    public void setNombProd(String nombProd) {
        this.nombProd = nombProd;
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

    public mnCarrito(int idCliente, int idCarrito, int idGranja, int idProdGran, int idProdCarrito, String nombProd, String imgProd, int cantidad) {
        this.idCliente = idCliente;
        this.idCarrito = idCarrito;
        this.idGranja = idGranja;
        this.idProdGran = idProdGran;
        this.idProdCarrito = idProdCarrito;
        this.nombProd = nombProd;
        this.imgProd = imgProd;
        this.cantidad = cantidad;
    }

    public void agregarProductoCarrito() throws IOException, XmlPullParserException {
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

    public ArrayList<Carprod> cargarDatosProductoCarrito() throws IOException, XmlPullParserException {
        listarProdCarrito.clear();
        cargarProductoCarrito(getIdCarrito());
      return listarProdCarrito;

    }

    public void cargarProductoCarrito(int idCarrito) throws IOException, XmlPullParserException {
        WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
        ArrayList<String> _listaProdCarrito = new ArrayList<String>();
        _listaProdCarrito = (ArrayList<String>) wsProductoCarrito.listarProductosCarrito(idCarrito);
        for (int a = 0; a < _listaProdCarrito.size(); a++) {

            Carprod Producto = new Carprod();
            Producto.setId(Integer.parseInt(_listaProdCarrito.get(a)));

            Producto.setNomProd(_listaProdCarrito.get(a + 1));
            Producto.setImgProd(_listaProdCarrito.get(a+2));
            Producto.setCantidad(Integer.parseInt(_listaProdCarrito.get(a+3)));

            listarProdCarrito.add(Producto);
            a = a + 3;
        }



    }



    //---------------------------Carrito-----------------------------------------------
    public void cargarDatosCarrito(int idCliente) throws IOException, XmlPullParserException {
        listarCarrito.clear();
        cargarCarrito(idCliente);

    }
    public ArrayList<Carrito> cargarCarrito(int idCliente) throws IOException, XmlPullParserException {
        WSCarrito wsCarrito = new WSCarrito();
        ArrayList<String> _listaCarrito = new ArrayList<String>();
        _listaCarrito = (ArrayList<String>) wsCarrito.listarCarrito(idCliente);
        for (int a = 0; a < _listaCarrito.size(); a++) {

            Carrito Carrito = new Carrito();
            Carrito.setId(Integer.parseInt(_listaCarrito.get(a)));
            Carrito.setNombreGranja(_listaCarrito.get(a + 1));

            listarCarrito.add(Carrito);
            a = a + 1;
        }
        return listarCarrito;
        }
    }
