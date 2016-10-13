package com.example.rodrigo.proyectgranja;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Rodrigo on 05/10/2016.
 */

public class WSGranja {
    static DatosSoap dato = new DatosSoap();

    public WSGranja() {
    }
//llamar al traergranja para luego poder hacer filtros


    public ArrayList<String> traerLocalidad () throws IOException, XmlPullParserException {
        ArrayList<String> localidadGranjas = new ArrayList<>();
        ArrayList<Granja> g1 = listarGranjas();
        for (int a = 0; a < g1.size(); a++) {
            //hacer recorrida para q los departamentos no se repitan 
            String b = g1.get(a).getLocalidad().substring(8);
            localidadGranjas.add(b);
        }
        return localidadGranjas;

    }
    public ArrayList<String> traergranja () throws IOException, XmlPullParserException {
        ArrayList<String> NombreGranjas = new ArrayList<>();
        ArrayList<Granja> g1 = listarGranjas();
            for (int a = 0; a < g1.size(); a++) {
                NombreGranjas.add(g1.get(a).getNombre());

        }


        return NombreGranjas;

    }
    public ArrayList<String> traerGranjaDepa (String Departamento) throws IOException, XmlPullParserException {


        ArrayList<String> NombreGranjas = new ArrayList<>();
        ArrayList<Granja> g2 = listarGranjas();
        for (int a = 0; a < g2.size(); a++) {
            if(!Departamento.equals("")&&!Departamento.equals(null)) {
                String localidad = g2.get(a).getLocalidad().substring(8);
                if(Departamento.equals(localidad)){
                    NombreGranjas.add(g2.get(a).getNombre());
                }


            }


        }
        return NombreGranjas;
    }

    public boolean verificargranjalocalidad(String localidad,String NombreGranja) throws IOException, XmlPullParserException {
boolean retur= false;
        ArrayList<Granja> g1 = listarGranjas();
        for (int a = 0; a < g1.size(); a++) {
            String NombreGranja1 = g1.get(a).getNombre();
            String localidad1 = g1.get(a).getLocalidad();
            String l = localidad1.substring(8);
            if(NombreGranja.equals(NombreGranja1) && localidad.equals(l)){
                retur= true;
            }
        }
        return retur;
       }

    public ArrayList<Granja> listarGranjas ()throws IOException, XmlPullParserException{


        ArrayList<Granja> granjas = new ArrayList<Granja>() ;

        SoapObject soap = new SoapObject("http://Servicio/", "listarGranjas");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httotrans = new HttpTransportSE(dato.getDatoIP() + "GranjaWS?WSDL");
        httotrans.call("listarGranjas", envelope);
        ArrayList<TipoProducto> tipoProductos = new ArrayList<TipoProducto>();
        Vector<String> informacionGranja = (Vector<String>) envelope.getResponse();
        for (int a = 0; a < informacionGranja.size(); a++) {

            Granja granja = new Granja();
            granja.setId(Integer.parseInt(String.valueOf(informacionGranja.get(a))));
            granja.setNombre(String.valueOf(informacionGranja.get(a + 1)));
            granja.setDireccion(String.valueOf(informacionGranja.get(a + 2)));
            granja.setGeoLong(Double.valueOf(String.valueOf(informacionGranja.get(a + 3))));
            granja.setGeoLat(Double.valueOf(String.valueOf(informacionGranja.get(a + 4))));
            granja.setLocalidad(String.valueOf(informacionGranja.get(a + 5)));
            granja.setUniVenta(String.valueOf(informacionGranja.get(a + 6)));
            a = a +6;
            granjas.add(granja);
        }

        return granjas;
    }
}
