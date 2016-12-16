package com.example.rodrigo.proyectgranja.WebService;

import android.content.Intent;
import android.os.Handler;
import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Logica.Boleta;
import com.example.rodrigo.proyectgranja.Logica.CalidadProducto;
import com.example.rodrigo.proyectgranja.Logica.TipoProducto;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;
/**
 * Created by Rodrigo on 13/11/2016.
 */

public class WSBoleta {
    static DatosSoap dato = new DatosSoap();
    private Vector<String> a = new Vector<>();
    public void nuevaBoleta(final int idCarrito) {
        SoapObject soap = new SoapObject("http://Servicio/","nuevaBoleta");
        soap.addProperty("idCarrito",idCarrito);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("nuevaBoleta",envelope);

                } catch (IOException e) {
                    nuevaBoleta(idCarrito);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            };
        };
        try {
            SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }


        try {
            thread4.start();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    public int traerUltimaBoleta() {
        int idBoleta = 0;
        SoapObject soap = new SoapObject("http://Servicio/","traerUltimaBoleta");
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("traerUltimaBoleta",envelope);
                } catch (IOException e) {
                    traerUltimaBoleta();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            };
        };

        try {
            thread4.start();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
            idBoleta= Integer.parseInt(String.valueOf(resultado));
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
       
        return idBoleta;
    }

    public void agregarProductoBoleta(final int idBoleta, final int idProdGran, final int cantidad, final float precio, final float precioTotal) throws IOException, XmlPullParserException {
        SoapObject soap = new SoapObject("http://Servicio/","agregarProductoBoleta");
String pre = String.valueOf(precio);
        String precioT = String.valueOf(precioTotal);
        soap.addProperty("idBoleta",idBoleta);
        soap.addProperty("idProdGran",idProdGran);
        soap.addProperty("cantidad",cantidad);
        soap.addProperty("precio",pre);
        soap.addProperty("precioTotal",precioT);

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("agregarProductoBoleta",envelope);
                } catch (IOException e) {
                    try {
                        agregarProductoBoleta(idBoleta,idProdGran,cantidad,precio, precioTotal);
                    } catch (IOException e1) {
                        try {
                            agregarProductoBoleta(idBoleta,idProdGran, cantidad, precio,precioTotal);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        } catch (XmlPullParserException e2) {
                            e2.printStackTrace();
                        }
                    } catch (XmlPullParserException e1) {
                        e1.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            };
        };

        try {
            thread4.start();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void modificarPrecioTotalBoleta(int idBoleta, float precioTotalBoleta) {
String precioTotal = Float.toHexString(precioTotalBoleta);
        SoapObject soap = new SoapObject("http://Servicio/","modificarPrecioTotalBoleta");
        soap.addProperty("idBoleta",idBoleta);
        soap.addProperty("precioTotal",precioTotal);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("modificarPrecioTotalBoleta",envelope);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            };
        };
        try {
            SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        thread4.start();
    }

    public ArrayList<Boleta> ListarBolCliente(final int idcliente) throws IOException, XmlPullParserException {
        ArrayList<Boleta> listaboleta = new ArrayList<>();
        SoapObject soap = new SoapObject("http://Servicio/", "listarBoletaCliente");
        soap.addProperty("idCliente",idcliente);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listarBoletaCliente", envelope);
                    a = (Vector<String>) envelope.getResponse();
                } catch (IOException e) {
                    try {
                        ListarBolCliente(idcliente);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (XmlPullParserException e1) {
                        e1.printStackTrace();
                    }
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

        try{
            Vector<String> listarProdGranja = a;


            for (int a = 0; a < listarProdGranja.size(); a++) {

                Boleta bol=new Boleta();
                bol.setId(Integer.parseInt(String.valueOf(listarProdGranja.get(a))));
                String fecha =  String.valueOf(listarProdGranja.get(a + 1));
                bol.setFecha(Date.valueOf(fecha));
                bol.setPrecioTotal(Float.valueOf(String.valueOf(listarProdGranja.get(a + 2))));
                bol.setPediListo(Boolean.valueOf(String.valueOf(listarProdGranja.get(a + 3))));
                bol.setBorrado(Boolean.valueOf(String.valueOf(listarProdGranja.get(a + 4))));
                bol.setNombre(String.valueOf(listarProdGranja.get(a+5)));
                bol.setApellido(String.valueOf(listarProdGranja.get(a+6)));
                bol.setTelefono(String.valueOf(listarProdGranja.get(a+7)));
                listaboleta.add(bol);
                a = a + 7;
            }
        }catch (NullPointerException e){
        }


        return listaboleta ;
    }
}
