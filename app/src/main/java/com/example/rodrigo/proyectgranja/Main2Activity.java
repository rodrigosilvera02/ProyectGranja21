package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Runnable, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {
    private TextView textol;
    SharedPreferences sharedpreferences;
    private String texto1;
    private ListView lista;
    private Handler handler = new Handler();
    private ArrayList<GranjaProducto> ListaDeGranjaProducto;
    @Override

    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        Thread t = new Thread(this);
        t.start();

     /*   handler.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                //    textol.setText(sharedpreferences.getString("prod1",null));

            }
        });*/

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

        MenuItemCompat.setOnActionExpandListener(searchItem, this);
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
            Intent ListSong = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(ListSong);

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
        }  /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void run() {


        WSGranjaProducto granjap =  new WSGranjaProducto();
        try {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            Filtros filtro =  new Filtros();
            ArrayList<GranjaProducto> g1 = granjap.traerGranjaProducto();
            String departamento  = sharedpreferences.getString("Departamento","departamento");
            ListaDeGranjaProducto = g1 ;
            if(!departamento.equals("")&&!departamento.equals("departamento")){
                ArrayList<GranjaProducto> g2 = ListaDeGranjaProducto;
                ListaDeGranjaProducto = filtro.filtrarporLocalidad(g2,departamento);
            }

            String nombreGranjaSE =sharedpreferences.getString("Granja","granja");
            if(!nombreGranjaSE.equals("")&&!nombreGranjaSE.equals("granja")){
                ArrayList<GranjaProducto> g2 = ListaDeGranjaProducto;
                ListaDeGranjaProducto = filtro.filtrarporGranja(g2,nombreGranjaSE);
            }
            String TipoProductoSE =sharedpreferences.getString("tipoProducto","tipoP");
            if(!TipoProductoSE.equals("")&&!TipoProductoSE.equals("tipoP")){
                ArrayList<GranjaProducto> g2 = ListaDeGranjaProducto;
                ListaDeGranjaProducto = filtro.filtrarporTipo(g2,TipoProductoSE);
            }
            String ProductoSE =sharedpreferences.getString("Producto","producto");
            if(!ProductoSE.equals("")&&!ProductoSE.equals("producto")){
                ArrayList<GranjaProducto> g2 = ListaDeGranjaProducto;
                ListaDeGranjaProducto = filtro.filtrarporProducto(g2,ProductoSE);
            }

            float Distanciakm =sharedpreferences.getFloat("Kilometros",0);
            if(Distanciakm < 0){

            }


            final ArrayList<listadoProducto> listaProducto = new ArrayList<listadoProducto>();
            listadoProducto p1 ;
            for(int i = 0;i<ListaDeGranjaProducto.size();i++){
                p1 =new listadoProducto();
                p1.setNombreProducto(ListaDeGranjaProducto.get(i).getNomProd());
                p1.setTipoProducto(ListaDeGranjaProducto.get(i).getTipoProducto());
                p1.setCalidadProducto(ListaDeGranjaProducto.get(i).getCalidad());
                p1.setNombreGranja(ListaDeGranjaProducto.get(i).getNombreGranja());
                String src = ListaDeGranjaProducto.get(i).getImgProg();
                p1.setImgProducto(src);
                p1.setPrecioProducto(String.valueOf(ListaDeGranjaProducto.get(i).getPrecio()));
                listaProducto.add(p1);
            }
            lista = (ListView)findViewById(R.id.listProductosCariito);

            final adaptadorlistadoProCarrito adapter = new adaptadorlistadoProCarrito(this, listaProducto);



            Thread thread4 = new Thread(){
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
            thread4.start();




        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        //  Toast.makeText(getApplicationContext(), "EXPAND", Toast.LENGTH_SHORT).show();

        return true;

    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {

        return true;

    }
    //arreglar el tema del buscado en los filtros y el s
    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")){
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Producto","");
            editor.commit();
            Thread t = new Thread(this);
            t.start();
        }
        if (!newText.equals("")) {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            final String Producto = String.valueOf(newText);
            editor.putString("Producto", Producto);
            editor.commit();
            Thread t = new Thread(this);
            t.start();
        }
        return true;
    }
}
