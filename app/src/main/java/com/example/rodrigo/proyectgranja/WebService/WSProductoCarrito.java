package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Manager.mnCarrito;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
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

public class WSProductoCarrito {
    static DatosSoap dato = new DatosSoap();
    private Vector<String> a = new Vector<>();
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



    public ArrayList<mnCarrito> listarProdCar(final int idCliente) {
        ArrayList<mnCarrito> listaCarrito = new ArrayList<>();

        SoapObject soap = new SoapObject("http://Servicio/", "listarProdCar");
        soap.addProperty("idCliente",idCliente);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "ProductoCarrito?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listarProdCar", envelope);
                    a = (Vector<String>) envelope.getResponse();
                } catch (IOException e) {
                    listarProdCar(idCliente);
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
            Vector<String> listarCarritos = a;


            for (int a = 0; a < listarCarritos.size(); a++) {

                mnCarrito prodc=new mnCarrito();
                prodc.setIdProdCarrito(Integer.parseInt(String.valueOf(listarCarritos.get(a))));
                prodc.setCantidad(Integer.parseInt(String.valueOf(listarCarritos.get(a+1))));
                prodc.setIdCarrito(Integer.parseInt(String.valueOf(listarCarritos.get(a+2))));
                prodc.setIdGranja(Integer.parseInt(String.valueOf(listarCarritos.get(a+3))));
                prodc.setNombreGranja(String.valueOf(listarCarritos.get(a+4)));
                prodc.setLocalidad(String.valueOf(listarCarritos.get(a+5)));
                prodc.setIdProdGran(Integer.parseInt(String.valueOf(listarCarritos.get(a+6))));
                prodc.setNombreProdGranja(String.valueOf(listarCarritos.get(a+7)));
                prodc.setImgProd(String.valueOf(listarCarritos.get(a+8)));

                listaCarrito.add(prodc);
                a = a + 8;
            }
        }catch (NullPointerException e){
        }




        return listaCarrito;
    }


    public String EliminarProdCar(int idProdCarrito) throws SoapFault {
        SoapObject soap = new SoapObject("http://Servicio/","eliminarProdCarrito");
        soap.addProperty("idProdCar",idProdCarrito);

        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"ProductoCarrito?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("eliminarProdCarrito",envelope);
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
