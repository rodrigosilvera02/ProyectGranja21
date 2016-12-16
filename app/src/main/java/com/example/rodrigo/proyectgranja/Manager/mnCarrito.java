package com.example.rodrigo.proyectgranja.Manager;

import android.os.Handler;

import com.example.rodrigo.proyectgranja.Logica.BolProd;
import com.example.rodrigo.proyectgranja.Logica.Boleta;
import com.example.rodrigo.proyectgranja.Logica.Carprod;
import com.example.rodrigo.proyectgranja.Logica.Carrito;
import com.example.rodrigo.proyectgranja.WebService.WSBoleta;
import com.example.rodrigo.proyectgranja.WebService.WSCarrito;
import com.example.rodrigo.proyectgranja.WebService.WSGranjaProducto;
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


    // Datos de ProductoGranja
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    private int strock;
    private String calidad;
    private String tipoProducto;
    private String unidad;
    private float precio;
    private boolean zafra;
    private Float precioZafra;

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
//recorrerlo con un debugh
    // hacer un if cuando el carrito no tiene productos para no generar boleta
    //ver el tema del precio total q me suma todos los precios de los productos , ver la variable
    // y fijarme por q no agrega tosos los productos a todas las boletas cuando es necesario
    //revizar todoel metodo por q no funciona bn
    public void ComprarCarritos(int idCliente) {
        try {
            int resStock = 0;

            ArrayList<String> _listaProdCarrito = new ArrayList<String>();
            ArrayList<String> _listaCarrito = new ArrayList<String>();
            WSCarrito wsCarrito = new WSCarrito();
            WSBoleta wsBoleta = new WSBoleta();
            WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
            try {
                _listaCarrito = (ArrayList<String>) wsCarrito.listarCarrito(idCliente);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            WSGranjaProducto wsGranjaProducto = new WSGranjaProducto();
            int idBoleta=0;
            for (int b = 0; b < _listaCarrito.size(); b++) {

                Carrito Carrito = new Carrito();
                int id = Integer.parseInt(String.valueOf(_listaCarrito.get(b)));
                Carrito.setId(id);

                try {
                    _listaProdCarrito = (ArrayList<String>) wsProductoCarrito.listarProductosCarrito(Carrito.getId());
                } catch (IOException e) {
                    ComprarCarritos(idCliente);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                if (_listaProdCarrito.size()>0)
                {


                    wsBoleta.nuevaBoleta(id);
                    idBoleta = wsBoleta.traerUltimaBoleta();
                    float PrecioTotalBoleta = 0;
                    for (int a = 0; a < _listaProdCarrito.size(); a++) {
                        Boleta bol = new Boleta();
                        BolProd producto = new BolProd();
                        bol.setId(idBoleta);
                        producto.setBoleta(bol);
                        producto.setId(Integer.parseInt(String.valueOf(_listaProdCarrito.get(a))));
                        producto.setIdProdGran(Integer.parseInt(String.valueOf(_listaProdCarrito.get(a + 1))));
                        producto.setCantidad(Integer.parseInt(String.valueOf(_listaProdCarrito.get(a + 4))));

                        ArrayList<String> _listaProdGran = new ArrayList<String>();
                        try {
                            _listaProdGran = (ArrayList<String>) wsGranjaProducto.informacionProductoGranja(producto.getIdProdGran());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }

                        setId(Integer.parseInt(String.valueOf(_listaProdGran.get(0))));
                        setStrock(Integer.parseInt(String.valueOf(_listaProdGran.get(3))));
                        setCalidad(String.valueOf(_listaProdGran.get(4)));
                        setPrecio(Float.valueOf(String.valueOf(_listaProdGran.get(5))));
                        setPrecioZafra(Float.valueOf(String.valueOf(_listaProdGran.get(6))));
                        setTipoProducto(String.valueOf(_listaProdGran.get(7)));
                        setUnidad(String.valueOf(_listaProdGran.get(8)));

                        producto.setPrecio(getPrecio());
                        producto.setPrecioTotal(getPrecio() * producto.getCantidad());
                        producto.setEstado("espera");
                        PrecioTotalBoleta = PrecioTotalBoleta + producto.getPrecioTotal();
                        resStock = getStrock() - producto.getCantidad();

                        try {
                            wsBoleta.agregarProductoBoleta(idBoleta, producto.getIdProdGran(), producto.getCantidad(), producto.getPrecio(), producto.getPrecioTotal());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                        try {
                            wsProductoCarrito.eliminarProdCarrito(producto.getId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                        wsGranjaProducto.modificarStockProdGranja(producto.getIdProdGran(), resStock);
                        a = a + 5;
                    }

                    wsBoleta.modificarPrecioTotalBoleta(idBoleta, PrecioTotalBoleta);
                }
                    b = b + 1;



            }
            }catch(NullPointerException ex){

            }

        }



    }
