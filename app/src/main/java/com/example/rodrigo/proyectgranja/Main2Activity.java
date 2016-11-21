package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;
import com.example.rodrigo.proyectgranja.WebService.WSGranjaProducto;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Runnable, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final float Lat = 0;
    public static final float Lon = 0;
    public static final String Granja1 = "granja";
    public static final String Departamento = "departamento";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private TextView textol;
    SharedPreferences sharedpreferences;
    private String texto1;
    private ListView lista;
    private Handler handler = new Handler();
    private ArrayList<mnGranjaProducto> ListaDeGranjaProducto;
    private Location location;
    LocationListener locationListener;
    private LocationManager mlocManager;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    private boolean a;
    private double[] lat = {0};
    private double[] lon = {0};
    private   ArrayList<mnGranjaProducto> g1;
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
a = false;

        locationManager = (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
         bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ¿Deberíamos mostrar una explicación?
            if(ActivityCompat.shouldShowRequestPermissionRationale (this,android.Manifest.permission.ACCESS_FINE_LOCATION)){

                // Mostrar una expansión al usuario * asincrónicamente * - no bloquear
                // este hilo esperando la respuesta del usuario!  Después de que el usuario
                // ve la explicación, vuelve a intentar solicitar el permiso.

            } else {

                // No se necesita ninguna explicación, podemos solicitar el permiso.

                ActivityCompat.requestPermissions (this,
                        new String [] {android.Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS es un
                // app-defined int constante.  El método callback obtiene el
                // resultado de la solicitud.

            }
        }
        WSGranjaProducto granjap =  new WSGranjaProducto();
        try {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        lat[0] = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                        lon[0] = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                    }catch (NullPointerException ex){

                    }
                }

            });
            g1 = granjap.traerGranjaProducto();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

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
    public void run() {


        if ( a  == false) {

            ListaDeGranjaProducto = g1 ;
        }

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final Filtros filtro =  new Filtros();

        String departamento  = sharedpreferences.getString("Departamento","departamento");

        if(!departamento.equals("")&&!departamento.equals("departamento")){
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporLocalidad(g2,departamento);
        }

        String nombreGranjaSE =sharedpreferences.getString("Granja","granja");
        if(!nombreGranjaSE.equals("")&&!nombreGranjaSE.equals("granja")){
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporGranja(g2,nombreGranjaSE);
        }
        String CalidadProductoSE = sharedpreferences.getString("CalidadProducto", "calidadP");
        if (!CalidadProductoSE.equals("") && !CalidadProductoSE.equals("calidadP")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.FiltroCalidad(g2, CalidadProductoSE);
        }
        String TipoProductoSE =sharedpreferences.getString("tipoProducto","tipoP");
        if(!TipoProductoSE.equals("")&&!TipoProductoSE.equals("tipoP")){
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporTipo(g2,TipoProductoSE);
        }
        String ProductoSE =sharedpreferences.getString("Producto","producto");
        if(!ProductoSE.equals("")&&!ProductoSE.equals("producto")){
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporProducto(g2,ProductoSE);
        }

        final float Distanciakm = sharedpreferences.getFloat("Kilometros", 0);


        if (Distanciakm > 0 && a  == false) {
            try {



                                     if (lat[0] != 0.0 && lon[0] != 0.0) {
                                         ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
                                         ListaDeGranjaProducto = filtro.filtrarporKm(lat[0], lon[0], g2, Distanciakm);
                                         a = true;

                                     }
                Thread t = new Thread(this);
                t.start();
            }catch (NullPointerException ex){

            }
        }

        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        int idCliente = sharedpreferences.getInt("idCliente",'0');
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
            p1.setIdproductoGranja(ListaDeGranjaProducto.get(i).getId());
            p1.setPrecioProducto(String.valueOf(ListaDeGranjaProducto.get(i).getPrecio()));
            p1.setIdGranja(ListaDeGranjaProducto.get(i).getIdGranja());
            p1.setIdCliente(idCliente);
            p1.setLatGranja(ListaDeGranjaProducto.get(i).getGeoLat());
            p1.setLonGranja(ListaDeGranjaProducto.get(i).getGeoLong());
            p1.setStrock(ListaDeGranjaProducto.get(i).getStrock());
            p1.setUnidad(ListaDeGranjaProducto.get(i).getUnidad());
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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
