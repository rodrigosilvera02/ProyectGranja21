package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.rodrigo.proyectgranja.MainActivity.Departamento;
import static com.example.rodrigo.proyectgranja.MainActivity.Producto;

/**
 * Created by Rodrigo on 05/10/2016.
 */
//aca se define los filtros
public class Filtros {
    public Filtros() {
    }
    public ArrayList<GranjaProducto> filtrarporProducto(ArrayList<GranjaProducto> g1 , String producto){
        ArrayList<GranjaProducto> filtroProducto = new ArrayList<GranjaProducto>();
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
    public ArrayList<GranjaProducto> filtrarporLocalidad(ArrayList<GranjaProducto> g1,String localidad){
        ArrayList<GranjaProducto> filtroNombreGranja = new ArrayList<GranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getLocalidad().equals(localidad)){
                filtroNombreGranja.add(g1.get(i));
            }
        }
        return filtroNombreGranja;
    }
    public ArrayList<GranjaProducto> filtrarporGranja(ArrayList<GranjaProducto> g1,String NomnbreGranja){
        ArrayList<GranjaProducto> filtroNombreGranja = new ArrayList<GranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getNombreGranja().equals(NomnbreGranja)){
                filtroNombreGranja.add(g1.get(i));
            }
        }
        return filtroNombreGranja;
    }

    public ArrayList<GranjaProducto> filtrarporTipo(ArrayList<GranjaProducto> g1,String tipop){
        ArrayList<GranjaProducto> filtrotipo = new ArrayList<GranjaProducto>();
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
    

    public ArrayList<GranjaProducto> filtrarporKm(Location location,ArrayList<GranjaProducto> g1,float Distancia){
        float mitaddistancia = Distancia / 2;
        ArrayList<GranjaProducto> granjasAMostrar = new ArrayList<GranjaProducto>();
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

    public ArrayList<Granja> FiltroGranjaDepartamento(ArrayList<Granja> granja1,String departamento){
        ArrayList<Granja> g1 = granja1;
        for (int i =0 ;i<g1.size();i++){

        }

        return g1;
    }



    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        //double radioTierra = 3958.75;//en millas
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return distancia;
    }
}
