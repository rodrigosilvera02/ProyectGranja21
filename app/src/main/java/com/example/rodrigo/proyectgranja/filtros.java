package com.example.rodrigo.proyectgranja;

import android.content.Context;

import java.util.ArrayList;

import static com.example.rodrigo.proyectgranja.MainActivity.Departamento;

/**
 * Created by Rodrigo on 05/10/2016.
 */
//aca se define los filtros
public class Filtros {
    public Filtros() {
    }
    public ArrayList<GranjaProducto> filtrarporProducto(ArrayList<GranjaProducto> g1 , String producto){
        ArrayList<GranjaProducto> filtroProducto = new ArrayList<GranjaProducto>();
        for(int i = 0 ;i<g1.size();i++){
            if(g1.get(i).getNomProd().equalsIgnoreCase(producto)){
                filtroProducto.add(g1.get(i));
            }
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

    public float[]obtenerNuestraUbicacion (){

        float localzacion[]= new float[2];
        return localzacion;
    }
    public boolean GranjaEstaAdistancia (float cordenadalat, float cordenadalong,float Distancia){

        return true;
    }
    

    public ArrayList<GranjaProducto> filtrarporKm(ArrayList<GranjaProducto> g1,float Distancia){
        float mitaddistancia = Distancia / 2;
        ArrayList<GranjaProducto> granjasAMostrar = new ArrayList<GranjaProducto>();
        for(int i =0;i<g1.size();i++){
            float latitud= g1.get(i).getGeoLat();
            float longitud = g1.get(i).getGeoLong();
            boolean cumple = GranjaEstaAdistancia(latitud,longitud,mitaddistancia);
            if(cumple == true){
                granjasAMostrar.add(g1.get(i));
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
}
