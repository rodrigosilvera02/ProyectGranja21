package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

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
    private Vector<String> a = new Vector<>();

    public WSGranjaProducto() {
    }

    public ArrayList<mnGranjaProducto> traerGranjaProducto() throws IOException, XmlPullParserException {
        SoapObject soap = new SoapObject("http://Servicio/", "listarProdGranja");
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);
        ArrayList<mnGranjaProducto> listaProdGran = new ArrayList<mnGranjaProducto>();
        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "GranjaProductWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listarProdGranja", envelope);
                    a = (Vector<String>) envelope.getResponse();
                } catch (IOException e) {
                    try {
                        traerGranjaProducto();
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

        try{
            Vector<String> listarProdGranja = a;


            for (int a = 0; a < listarProdGranja.size(); a++) {

                mnGranjaProducto prodg=new mnGranjaProducto();
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
        }catch (NullPointerException e){
            traerGranjaProducto();
        }

        return listaProdGran;
    }
}
