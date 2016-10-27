package com.example.rodrigo.proyectgranja.WebService;


import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Logica.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Rodrigo on 11/09/2016.
 */

public class WSUsuario {

   static DatosSoap dato = new DatosSoap();

    public WSUsuario(){

    }
   public void modificarUsuario (Usuario u1) throws IOException, XmlPullParserException, InterruptedException {

        SoapObject soap = new SoapObject("http://Servicio/","modificarUsuario");
        soap.addProperty("idUsuario",u1.getId());
        soap.addProperty("nickName",u1.getNickName());
        soap.addProperty("nombre",u1.getNombre());
        soap.addProperty("apellido",u1.getApellido());
        soap.addProperty("email",u1.getEmail());
        soap.addProperty("direccion",u1.getDireccion());
        soap.addProperty("telefono",u1.getTelefono());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        httotrans.call("",envelope);




   }





   public Usuario infoUsuario(String nickName) throws IOException, XmlPullParserException,HttpResponseException{
       Usuario u1 =  new Usuario();
       SoapObject soap = new SoapObject("http://Servicio/","infoUsuario ");
       soap.addProperty("nickName",nickName);
       SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
       envelope.setOutputSoapObject(soap);
       ArrayList<String> informacionUsuario = null;
       HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
      httotrans.call("infoUsuario ",envelope);
       Vector<Object> informacionUsuario1= (Vector<Object>) envelope.getResponse();
       SoapPrimitive id = (SoapPrimitive) informacionUsuario1.get(0);
       SoapPrimitive nombre = (SoapPrimitive) informacionUsuario1.get(2);
       SoapPrimitive apellido = (SoapPrimitive) informacionUsuario1.get(3);
       SoapPrimitive email = (SoapPrimitive) informacionUsuario1.get(4);
       SoapPrimitive direccion = (SoapPrimitive) informacionUsuario1.get(5);
       SoapPrimitive telefono = (SoapPrimitive) informacionUsuario1.get(6);

       u1.setId((Integer.parseInt(String.valueOf(id))));
       u1.setNickName(String.valueOf(nickName));
       u1.setNombre(String.valueOf(nombre));
       u1.setApellido(String.valueOf(apellido));
       u1.setEmail(String.valueOf(email));
       u1.setDireccion(String.valueOf(direccion));
       u1.setTelefono(String.valueOf(telefono));
       return u1;
   }



    public String validarUsuario(String nickName,String password) throws IOException, XmlPullParserException {
        String valor = "false";
        SoapObject soap = new SoapObject("http://Servicio/","validarUsuario");
        soap.addProperty("nickName",nickName);
        soap.addProperty("password",password);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        httotrans.call("validarUsuario",envelope);

        SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
        valor  = resultado.toString();


        return valor;
    }

    public static int traerIdUsuario(String nickName) throws IOException, XmlPullParserException {
        SoapObject soap = new SoapObject("http://Servicio/","traerIdUsuario");
        soap.addProperty("nickName",nickName);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("traerIdUsuario",envelope);
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
        return Integer.parseInt(resultado.toString());
    }
    public String validarNickName(String nickName) throws IOException, XmlPullParserException {
        String valor = "false";
        SoapObject soap = new SoapObject("http://Servicio/","validarNickName");
        soap.addProperty("nickName",nickName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        httotrans.call("validarNickName",envelope);

        SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
     valor  = resultado.toString();


        return valor;
    }

    public String agregarUsuario(String nickName,String password, String nombre,String apellido,String email, String direccion,String telefono) throws IOException,XmlPullParserException{




        SoapObject soap = new SoapObject("http://Servicio/","nuevoUsuario");
        soap.addProperty("nickName",nickName);
        soap.addProperty("password",password);
        soap.addProperty("nombre",nombre);
        soap.addProperty("apellido",apellido);
        soap.addProperty("email",email);
        soap.addProperty("direccion",direccion);
        soap.addProperty("telefono",telefono);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        httotrans.call("nuevoUsuario",envelope);
        Object resultado =  envelope.getResponse();


        return String.valueOf(resultado);
    }

}
