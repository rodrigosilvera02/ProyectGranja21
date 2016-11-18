package com.example.rodrigo.proyectgranja.WebService;

import com.example.rodrigo.proyectgranja.DatosSoap;

import android.content.Intent;
import android.os.Handler;
import com.example.rodrigo.proyectgranja.DatosSoap;
import com.example.rodrigo.proyectgranja.Logica.BolProd;
import com.example.rodrigo.proyectgranja.Logica.Boleta;
import com.example.rodrigo.proyectgranja.Logica.CalidadProducto;
import com.example.rodrigo.proyectgranja.Logica.TipoProducto;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;
/**
 * Created by Rodrigo on 13/11/2016.
 */

/**
 * Created by Rodrigo on 17/11/2016.
 */

public class WSBoletaProducto {
    static DatosSoap dato = new DatosSoap();
    private Vector<String> a = new Vector<>();
    public ArrayList<BolProd> ListarBolCliente(final int idboleta) throws IOException, XmlPullParserException {
        ArrayList<BolProd> listaProducto = new ArrayList<>();
        SoapObject soap = new SoapObject("http://Servicio/", "listarProductoBoleta");
        soap.addProperty("idBoleta",idboleta);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        final HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "BoletaWS?WSDL");
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    httotrans.call("listarProductoBoleta", envelope);
                    a = (Vector<String>) envelope.getResponse();
                } catch (IOException e) {
                    try {
                        ListarBolCliente(idboleta);
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
            Vector<String> listarProdbol = a;


            for (int a = 0; a < listarProdbol.size(); a++) {

                BolProd prod = new BolProd();
                prod.setId(Integer.parseInt(String.valueOf(listarProdbol.get(a))));
                prod.setIdProdGran(Integer.parseInt(String.valueOf(listarProdbol.get(a + 1))));
                prod.setNombreProd(String.valueOf(listarProdbol.get(a + 2)));
                prod.setImgProd(String.valueOf(listarProdbol.get(a + 3)));
                prod.setCantidad(Integer.parseInt(String.valueOf(listarProdbol.get(a + 4))));
                prod.setPrecio(Float.valueOf(String.valueOf(listarProdbol.get(a + 5))));
                prod.setPrecioTotal(Float.valueOf(String.valueOf(listarProdbol.get(a + 6))));
                prod.setEstado(String.valueOf(listarProdbol.get(a + 7)));

                listaProducto.add(prod);
                a = a + 7;
            }
        }catch (NullPointerException e){
        }


        return listaProducto ;
    }
}
