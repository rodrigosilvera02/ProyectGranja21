package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Logica.CalidadProducto;
import com.example.rodrigo.proyectgranja.Logica.TipoProducto;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Rodrigo on 25/10/2016.
 */

public class WSCalidad {

    static DatosSoap dato = new DatosSoap();

    public ArrayList<CalidadProducto> cargarlistaCalidad() throws IOException, XmlPullParserException {
        ArrayList<CalidadProducto> listaCalidadProd = new ArrayList<>();
        ArrayList<String> listaTipoProd = new ArrayList<String>();

        SoapObject soap = new SoapObject("http://Servicio/", "listaCalidadProducto");
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "CalidadWS?WSDL");

        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listaCalidadProducto", envelope);

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

        Vector<String> listacalidadProducto = (Vector<String>) envelope.getResponse();


        for (int a = 0; a < listacalidadProducto.size(); a++) {

            CalidadProducto tipoProd = new CalidadProducto();
            tipoProd.setId(Integer.parseInt(String.valueOf(listacalidadProducto.get(a))));
            tipoProd.setCalidadProductto(String.valueOf(listacalidadProducto.get(a + 1)));
            listaCalidadProd.add(tipoProd);
            a = a + 1;
        }
        return listaCalidadProd;
    }
}
