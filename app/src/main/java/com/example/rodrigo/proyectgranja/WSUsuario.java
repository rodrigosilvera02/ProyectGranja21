package com.example.rodrigo.proyectgranja;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Rodrigo on 11/09/2016.
 */

public class WSUsuario {

   static DatosSoap dato = new DatosSoap();

    public WSUsuario(){

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
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"UsuarioWS?WSDL");
        httotrans.call("traerIdUsuario",envelope);
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
