package com.example.rodrigo.proyectgranja;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Rodrigo on 15/10/2016.
 */
public class MyLocationListener implements LocationListener {
public static Double Latitud;
    public static Double Longitud;
    public static boolean StadoGps;
    public static Location cordenadas;



    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        Latitud = location.getLatitude();
        Longitud =location.getLongitude();
        cordenadas = location;
        return;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}