package com.example.rodrigo.proyectgranja;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Rodrigo on 05/10/2016.
 */

public class WStipoProducto {
    static DatosSoap dato = new DatosSoap();

    public WStipoProducto() {
    }


    public ArrayList<TipoProducto> traerListaTiposProductos() throws IOException, XmlPullParserException {


        SoapObject soap = new SoapObject("http://Servicio/", "listaTipoProducto");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "TipoProductoWS?WSDL");
        httotrans.call("listaTipoProducto", envelope);
        ArrayList<TipoProducto> tipoProductos = new ArrayList<TipoProducto>();
        Vector<String> listaTipoProducto = (Vector<String>) envelope.getResponse();
        for (int a = 0; a < listaTipoProducto.size(); a++) {

            TipoProducto tipoProd = new TipoProducto();
            tipoProd.setId(Integer.parseInt(String.valueOf(listaTipoProducto.get(a))));
            tipoProd.setTipoProducto(String.valueOf(listaTipoProducto.get(a + 1)));

            tipoProductos.add(tipoProd);
            a = a + 1;
        }

        return tipoProductos;

    }

}
