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
public class WScliente {

    DatosSoap dato = new DatosSoap();
    public WScliente(){

    }
public String nuevoUsuarioCliente (int nuemroUsuario)throws IOException,XmlPullParserException{
    String valor = "false";
    SoapObject soap = new SoapObject("http://Servicio/","AgregarCliente");
    soap.addProperty("idUsuario",nuemroUsuario);

    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
    envelope.setOutputSoapObject(soap);

    HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP()+"CatUsuarioWS?WSDL");
    httotrans.call("AgregarCliente",envelope);
    SoapPrimitive resultado= (SoapPrimitive) envelope.getResponse();
    valor  = "true";


    return valor;
}




}
