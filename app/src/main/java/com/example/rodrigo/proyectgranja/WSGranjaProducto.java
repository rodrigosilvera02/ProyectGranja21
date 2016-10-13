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
 * Created by Rodrigo on 30/09/2016.
 */

public class WSGranjaProducto {
    static DatosSoap dato = new DatosSoap();
    public WSGranjaProducto() {
    }

    public ArrayList<GranjaProducto> traerGranjaProducto() throws IOException, XmlPullParserException {
        SoapObject soap = new SoapObject("http://Servicio/", "listarProdGranja");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "GranjaProductWS?WSDL");
        httotrans.call("listarProdGranja", envelope);
        ArrayList<GranjaProducto> listaProdGran = new ArrayList<GranjaProducto>();
      Vector<String> listarProdGranja = (Vector<String>) envelope.getResponse();
        for (int a = 0; a < listarProdGranja.size(); a++) {

            GranjaProducto prodg=new GranjaProducto();
           prodg.setId(Integer.parseInt(String.valueOf(listarProdGranja.get(a))));
            prodg.setNomProd(String.valueOf(listarProdGranja.get(a+1)));
            prodg.setImgProg(String.valueOf(listarProdGranja.get(a+2)));

            prodg.setIdGranja(Integer.parseInt(String.valueOf(listarProdGranja.get(a+3))));
            prodg.setNombreGranja(String.valueOf(listarProdGranja.get(a+4)));
            String localidad = String.valueOf(listarProdGranja.get(a+5));

            prodg.setLocalidad(localidad.substring(8));
            prodg.setGeoLat(Float.valueOf(String.valueOf(listarProdGranja.get(a+6))));
            prodg.setGeoLong(Float.valueOf(String.valueOf(listarProdGranja.get(a+7))));

            prodg.setStrock(Integer.parseInt(String.valueOf(listarProdGranja.get(a+8))));
            prodg.setCalidad(String.valueOf(listarProdGranja.get(a+9)));
            prodg.setPrecio(Float.valueOf(String.valueOf(listarProdGranja.get(a+10))));
            prodg.setPrecioZafra(Float.valueOf(String.valueOf(listarProdGranja.get(a+11))));
            prodg.setTipoProducto(String.valueOf(listarProdGranja.get(a+12)));
            prodg.setUnidad(String.valueOf(listarProdGranja.get(a+13)));

            listaProdGran.add(prodg);
            a = a + 13;
        }
        return listaProdGran;
    }
}
