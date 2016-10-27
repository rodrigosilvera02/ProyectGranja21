package com.example.rodrigo.proyectgranja;

import android.location.Location;


import com.example.rodrigo.proyectgranja.Logica.Granja;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 05/10/2016.
 */
//aca se define los filtros
public class Filtros {
    public Filtros() {
    }
    public ArrayList<mnGranjaProducto> filtrarporProducto(ArrayList<mnGranjaProducto> g1 , String producto){
        ArrayList<mnGranjaProducto> filtroProducto = new ArrayList<mnGranjaProducto>();
        int tamaño =producto.length();

        for(int i = 0 ;i<g1.size();i++){
            int tamañoNombreProducto = g1.get(i).getNomProd().length();
            String NombreProdu =  g1.get(i).getNomProd();
           for(int contador =0 ;tamañoNombreProducto>=tamaño+contador;contador++ ){
               String NombreCortadoProducto = NombreProdu.substring(contador,tamaño+contador);
               if(producto.equalsIgnoreCase(NombreCortadoProducto)){
                   if(filtroProducto.size() == 0){
                       filtroProducto.add(g1.get(i));
                   }
                   if(filtroProducto.size() != 0){
                       for (int recorrerArray=0;recorrerArray<filtroProducto.size();recorrerArray++){
                           if(filtroProducto.get(recorrerArray) != g1.get(i)){
                               filtroProducto.add(g1.get(i));
                           }
                       }
                   }
               }
           }
          /*  for(int contador = 0;;contador++)
            if(g1.get(i).getNomProd().equalsIgnoreCase(producto)){
                filtroProducto.add(g1.get(i));
            }*/
        }
        return filtroProducto;
    }
    public ArrayList<mnGranjaProducto> filtrarporLocalidad(ArrayList<mnGranjaProducto> g1, String localidad){
        ArrayList<mnGranjaProducto> filtroNombreGranja = new ArrayList<mnGranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getLocalidad().equals(localidad)){
                filtroNombreGranja.add(g1.get(i));
            }
        }
        return filtroNombreGranja;
    }
    public ArrayList<mnGranjaProducto> filtrarporGranja(ArrayList<mnGranjaProducto> g1, String NomnbreGranja){
        ArrayList<mnGranjaProducto> filtroNombreGranja = new ArrayList<mnGranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getNombreGranja().equals(NomnbreGranja)){
                filtroNombreGranja.add(g1.get(i));
            }
        }
        return filtroNombreGranja;
    }

    public ArrayList<mnGranjaProducto> filtrarporTipo(ArrayList<mnGranjaProducto> g1, String tipop){
        ArrayList<mnGranjaProducto> filtrotipo = new ArrayList<mnGranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getTipoProducto().equals(tipop)){
                filtrotipo.add(g1.get(i));
            }
        }
        return filtrotipo;
    }


    public boolean GranjaEstaAdistancia (Location location, double cordenadalat, double cordenadalong, float Distancia){

        return true;
    }
    

    public ArrayList<mnGranjaProducto> filtrarporKm(Location location, ArrayList<mnGranjaProducto> g1, float Distancia){
        float mitaddistancia = Distancia / 2;
        ArrayList<mnGranjaProducto> granjasAMostrar = new ArrayList<mnGranjaProducto>();
        for(int i =0;i<g1.size();i++){
            float latitud= g1.get(i).getGeoLat();
            float longitud = g1.get(i).getGeoLong();

            double distanciaentreYoGranja = distanciaCoord(latitud,longitud,location.getLatitude(),location.getLongitude());
            if(distanciaentreYoGranja<=Distancia/2){
                granjasAMostrar.add(g1.get(i));
            }
            if(distanciaentreYoGranja>=Distancia/2){
            if(distanciaentreYoGranja<=Distancia){
                granjasAMostrar.add(g1.get(i));
            }
            }
        }
        return g1;
    }

    public ArrayList<Granja> FiltroGranjaDepartamento(ArrayList<Granja> granja1, String departamento){
        ArrayList<Granja> g1 = granja1;
        for (int i =0 ;i<g1.size();i++){

        }

        return g1;
    }



    public double distanciaCoord(double la1, double lo1, double la2, double lo2) {
        // transforma grados en radianes
        double lat1 = graRad(la1);
        double long1 = graRad(lo1);

        double lat2 = graRad(la2);
        double long2 = graRad(lo2);
        // calcula la distancia
        double d = Math.acos( Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2) * Math.cos(long2-long1) ) * 6371;
     return d;
    }

    public double graRad(double grados){
        double radianes = (grados * Math.PI)/180;
        return radianes;

}


    public ArrayList<mnGranjaProducto> FiltroCalidad(ArrayList<mnGranjaProducto> g2, String calidadProductoSE) {
        ArrayList<mnGranjaProducto> fitlroCalidad = new ArrayList<mnGranjaProducto>();
        for(int i = 0 ;i<g2.size();i++){
            if(g2.get(i).getCalidad().equals(calidadProductoSE)){
                fitlroCalidad.add(g2.get(i));
            }
        }
        return fitlroCalidad;


    }
}
