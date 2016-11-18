package com.example.rodrigo.proyectgranja.WebService;

import android.os.Handler;
import android.view.View;

import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Manager.mnCarrito;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Rodrigo on 22/10/2016.
 */

public class WSCarrito {
    static DatosSoap dato = new DatosSoap();
    private Handler handler = new Handler();
    private Vector<String> a = new Vector<>();


public String agregarCarrito(int idCliente, int idGranja) throws IOException, XmlPullParserException {

    SoapObject soap = new SoapObject("http://Servicio/","agregarCarrito");
    soap.addProperty("idCliente",idCliente);
    soap.addProperty("idGranja",idGranja);
    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);
    final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CarritoWS?WSDL");
    Thread thread4 = new Thread(){
        @Override
        public void run() {
            try {
                httotrans.call("agregarCarrito",envelope);
            } catch (IOException e) {
                e.printStackTrace();
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



    Object resultado =  envelope.getResponse();


    return String.valueOf(resultado);

}


public Integer existeCarrito(int idCliente, int idGranja) throws IOException, XmlPullParserException {
    final int[] valor = new int[1];
    final String[] v = new String[1];
    SoapObject soap = new SoapObject("http://Servicio/","existeCarrito");
    soap.addProperty("idCliente",idCliente);
    soap.addProperty("idGranja",idGranja);
    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);
    final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CarritoWS?WSDL");
    final SoapPrimitive[] resultado = new SoapPrimitive[1];
    Thread thread4 = new Thread(){
        @Override
        public void run() {
            try {
                httotrans.call("existeCarrito",envelope);
            } catch (IOException e) {
                e.printStackTrace();
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

    Thread thread5 = new Thread(){
        @Override
        public void run() {
            try {
               resultado[0] = (SoapPrimitive) envelope.getResponse();
               v[0] = String.valueOf(resultado[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    };

    try {
        thread5.start();
        thread5.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


    if(!v[0].equals("null")){
        valor[0] = Integer.valueOf(String.valueOf(resultado[0]));
    }
    else{
        valor[0] = 0;
    }




    return valor[0];

}


    public ArrayList<String> listarCarrito(final int idCliente) throws IOException, XmlPullParserException  {
        ArrayList<String> listacarrito = new ArrayList<>();
        SoapObject soap = new SoapObject("http://Servicio/", "listarCarrito");
        soap.addProperty("idCliente",idCliente);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "CarritoWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listarCarrito", envelope);
                    a = (Vector<String>) envelope.getResponse();
                } catch (IOException e) {
                    try {
                        listarCarrito(idCliente);
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
        for (int cont = 0 ;cont<a.size(); cont ++ )
        {

            listacarrito.add(a.get(cont));
        }


        return listacarrito;
    }
}
