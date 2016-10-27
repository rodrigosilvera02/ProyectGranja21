package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 22/10/2016.
 */

public class WSProductoCarrito {
    static DatosSoap dato = new DatosSoap();
//aca la comunicaion con el web service productocarrito

/*    @WebMethod(operationName = "nuevoProductoCarrito")
*/
   public String nuevoProductoCarrito  (int idcarrito, int idpriductogranja, int cantidad) throws IOException, XmlPullParserException {

       ArrayList<String> arrayClientecarrito= new ArrayList<>();
       SoapObject soap = new SoapObject("http://Servicio/","nuevoProductoCarrito");
       soap.addProperty("intCarrito",idcarrito);
       soap.addProperty("intProdGranja",idpriductogranja);
       soap.addProperty("cantidad",cantidad);
       final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
       envelope.setOutputSoapObject(soap);
       final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"ProductoCarrito?WSDL");
       Thread thread4 = new Thread(){
           @Override
           public void run() {
               try {
                   httotrans.call("nuevoProductoCarrito",envelope);
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
       return String.valueOf(resultado);
   }


    /**
     * Web service operation*/
     public ArrayList<String> listarProductosCarrito (int idCarrito) throws IOException, XmlPullParserException {
         ArrayList <String> listraProductoCarrito =  new ArrayList<>();
         SoapObject soap = new SoapObject("http://Servicio/","listarProductosCarrito");
         soap.addProperty("intCarrito",idCarrito);

         final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
         envelope.setOutputSoapObject(soap);
         final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"ProductoCarrito?WSDL");
         Thread thread4 = new Thread(){
                     @Override
                     public void run() {
                         try {
                     httotrans.call("listarProductosCarrito",envelope);
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

         listraProductoCarrito = (ArrayList<String>) envelope.getResponse();
         return listraProductoCarrito;
     }

    /**
     * Web service operation*/
     public String modificarProdCar(int idProCar , int cantidad) throws IOException, XmlPullParserException {

         SoapObject soap = new SoapObject("http://Servicio/","modificarProdCar");
         soap.addProperty("idProdCar",idProCar);
         soap.addProperty("cantidad",cantidad);
         final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
         envelope.setOutputSoapObject(soap);
         final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"ProductoCarrito?WSDL");
         Thread thread4 = new Thread(){
             @Override
             public void run() {
                 try {
                     httotrans.call("modificarProdCar",envelope);
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

         SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
         String valor = resultado.toString();


     return valor;
     }
}
