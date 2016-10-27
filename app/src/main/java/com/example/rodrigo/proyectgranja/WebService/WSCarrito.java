package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 22/10/2016.
 */

public class WSCarrito {
    static DatosSoap dato = new DatosSoap();

public String agregarCarrito(int idCliente, int idGranja) throws IOException, XmlPullParserException {

    SoapObject soap = new SoapObject("http://Servicio/","agregarCarrito");
    soap.addProperty("idCliente",idCliente);
    soap.addProperty("idGranja",idGranja);
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);
    HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CarritoWS?WSDL");
    httotrans.call("agregarCarrito",envelope);
    Object resultado =  envelope.getResponse();


    return String.valueOf(resultado);

}
    //aca la comunicaion con el web service carrito
 /*   private String agregarCarrito(int idCliente, int idGranja) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        servicio.CarritoWS port = service.getCarritoWSPort();
        return port.agregarCarrito(idCliente, idGranja);
    }
*/

public Integer existeCarrito(int idCliente, int idGranja) throws IOException, XmlPullParserException {

    SoapObject soap = new SoapObject("http://Servicio/","existeCarrito");
    soap.addProperty("idCliente",idCliente);
    soap.addProperty("idGranja",idGranja);
    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);
    final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CarritoWS?WSDL");

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
    thread4.start();
    try {
        thread4.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    Object resultado =  envelope.getResponse();


    return Integer.parseInt(String.valueOf(resultado));

}



  /*  private Integer existeCarrito(int idCliente, int idGranja) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        servicio.CarritoWS port = service.getCarritoWSPort();
        return port.existeCarrito(idCliente, idGranja);
    }*/

public ArrayList<String> listarCarrito (int idCliente) throws IOException, XmlPullParserException {
    ArrayList<String> arrayClientecarrito= new ArrayList<>();
    SoapObject soap = new SoapObject("http://Servicio/","listarCarrito");
    soap.addProperty("idCliente",idCliente);
    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);
    final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CarritoWS?WSDL");
    Thread thread4 = new Thread(){
        @Override
        public void run() {
            try {
                httotrans.call("listarCarrito",envelope);
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



    arrayClientecarrito = (ArrayList<String>) envelope.getResponse();

    return arrayClientecarrito;

}



    /* private java.util.List<java.lang.String> listarCarrito(int idCliente) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        servicio.CarritoWS port = service.getCarritoWSPort();
        return port.listarCarrito(idCliente);
    }*/
}
