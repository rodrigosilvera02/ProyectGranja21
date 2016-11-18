package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rodrigo.proyectgranja.Logica.BolProd;
import com.example.rodrigo.proyectgranja.Logica.Boleta;
import com.example.rodrigo.proyectgranja.WebService.WSBoleta;
import com.example.rodrigo.proyectgranja.WebService.WSBoletaProducto;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ActivityMostrarDatosBoleta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Runnable, SearchView.OnQueryTextListener{
    SharedPreferences sharedpreferences;
    private String texto1;
    private ListView lista;
    private adaptadorlistadoprodbolCliente adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos_boleta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

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
            Intent ListSong = new Intent(this, MainActivity.class);
            startActivity(ListSong);
            return true;
        }
        if (id == R.id.filtros) {
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
            Intent ListSong = new Intent(this, modificar.class);
            startActivity(ListSong);

            // Handle the camera action
        }

        if(id == R.id.ModPasswoed) {
            Intent ListSong = new Intent(this, ActivityCamPassword.class);
            startActivity(ListSong);

        }
        if(id == R.id.MosCarrito) {
            Intent ListSong = new Intent(this, ActivityMostrarCarrito.class);
            startActivity(ListSong);

        }
        if(id == R.id.Ubucacion) {
            Intent ListSong = new Intent(this, MapsActivity.class);
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
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    @Override
    public void run() {
        SharedPreferences sharedpreferences = getSharedPreferences(Main2Activity.MyPREFERENCES, Context.MODE_PRIVATE);
        final WSBoletaProducto wsBoletaProducto = new WSBoletaProducto();
        lista = (ListView)findViewById(R.id.lstproductoboleta);
       final int idbol  =  sharedpreferences.getInt("idBoleta",'0');
        final ArrayList<BolProd>[] listarBolprobol = new ArrayList[1];
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                try {
                    listarBolprobol[0] =wsBoletaProducto.ListarBolCliente(idbol);
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


        adapter  = new adaptadorlistadoprodbolCliente(this, listarBolprobol[0]);
        Thread thread5 = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lista.setAdapter(adapter);
                    }
                });
            };
        };
        thread5.start();

    }


}
