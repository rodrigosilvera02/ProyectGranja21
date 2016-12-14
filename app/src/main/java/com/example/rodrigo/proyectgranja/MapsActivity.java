package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Logica.Granja;
import com.example.rodrigo.proyectgranja.WebService.WSGranja;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener, Runnable {

    private TextView textol;
    private Handler handler = new Handler();
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();


    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(MapsActivity.this);
        Thread t = new Thread(this);
        t.start();
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        MenuItem searchItem = menu.findItem(R.id.menu3_buscar);
        searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Cerrarsesión) {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();
            editor.commit();
            finish();
            Intent ListSong = new Intent(this, MainActivity.class);
            startActivity(ListSong);

        }
        if (id == R.id.home) {
            ponervariablevacio ();
            Intent ListSong = new Intent(this, MainActivity.class);
            startActivity(ListSong);
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Producto","");
            editor.commit();
            return true;
        }
        if (id == R.id.filtros) {
            ponervariablevacio ();
            Intent ListSong = new Intent(this, FiltrosActivity.class);
            startActivity(ListSong);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.modificarusuario ){
            ponervariablevacio ();
            Intent ListSong = new Intent(this, modificar.class);
            startActivity(ListSong);

            // Handle the camera action
        }
        if(id == R.id.ModPasswoed) {
            ponervariablevacio ();
            Intent ListSong = new Intent(this, ActivityCamPassword.class);
            startActivity(ListSong);

        }
        if(id == R.id.MosCarrito) {
            ponervariablevacio ();
            Intent ListSong = new Intent(this, ActivityMostrarCarrito.class);
            startActivity(ListSong);

        }
        if(id == R.id.BoletaCliente) {
            Intent ListSong = new Intent(this, activity_mostrar_boleta.class);
            startActivity(ListSong);

        }/*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-32.454976027729394, -55.56258792968754);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (float) 6.45));

    }



    @Override
    public void run() {
        SharedPreferences sharedpreferences = getSharedPreferences(Main2Activity.MyPREFERENCES, Context.MODE_PRIVATE);
      final String nombreg = sharedpreferences.getString("Granja1","granja");
        final float lat = sharedpreferences.getFloat("Lat",'0');
        float lon = sharedpreferences.getFloat("Lon",'0');
        if(!nombreg.equals("")&&!nombreg.equals("granja")&&lat != 0 && lon !=0 ) {
            final LatLng sydney = new LatLng(lat, lon);
            Thread thread4 = new Thread() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.addMarker(new MarkerOptions().position(sydney).title(nombreg));
                        }
                    });
                }

                ;
            };
            thread4.start();
        }else {
            WSGranja  wsGranja = new WSGranja();
            ArrayList<Granja> granjas=  new ArrayList<>();
            try {
                 granjas= wsGranja.listarGranjas();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            for (int a = 0 ;a<granjas.size();a ++){
                final Double lat1 =  granjas.get(a).getGeoLat();
                final Double lon1 = granjas.get(a).getGeoLong();
                final String NombreG = granjas.get(a).getNombre();
                final LatLng sydney = new LatLng(lat1, lon1);
                Thread thread4 = new Thread() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.addMarker(new MarkerOptions().position(sydney).title(NombreG));

                            }
                        });
                    }

                    ;
                };
                thread4.start();



            }

        }
    }

private void ponervariablevacio (){
    SharedPreferences sharedpreferences = getSharedPreferences(Main2Activity.MyPREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedpreferences.edit();
    editor.putString("Granja1","granja");
    editor.putFloat("Lat",'0');
    editor.putFloat("Lon",'0');
    editor.commit();
}

}
