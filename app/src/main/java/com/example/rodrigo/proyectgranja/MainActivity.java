package com.example.rodrigo.proyectgranja;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;

import com.example.rodrigo.proyectgranja.Logica.Granja;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;
import com.example.rodrigo.proyectgranja.WebService.WSGranjaProducto;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GridView.OnClickListener, Runnable, OnQueryTextListener, OnActionExpandListener {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Granja = "granja";
    public static final String Departamento = "departamento";
    public static final String Producto = "producto";
    public static final float Kilometros = '0';
    public static final String tipoProducto = "tipoP";
    public static final String CalidadProducto = "calidadP";
    public static final Double lonmia=0.0;
    public static final Double latmia = 0.0;
    private boolean a;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public static int idCliente = 0;
    public static int idUsuario = 0;
    private double[] lat = {0};
    private double[] lon = {0};
    public static final ArrayList<mnGranjaProducto> prod1 = null;
    SharedPreferences sharedpreferences;
    private String texto1;
    private ListView lista;
    private Handler handler = new Handler();
    private ArrayList<mnGranjaProducto> ListaDeGranjaProducto;
    Location location;
    private   ArrayList<mnGranjaProducto> g1;
    LocationListener locationListener;
    private LocationManager mlocManager;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        texto1 = sharedpreferences.getString("Name", "nameKey");
        if (texto1.equals("nameKey")) {
            a = false;
            setContentView(R.layout.activity_main);
            lista = (ListView) findViewById(R.id.ListProductoGranjasl);
            locationManager = (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // ¿Deberíamos mostrar una explicación?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Mostrar una expansión al usuario * asincrónicamente * - no bloquear
                    // este hilo esperando la respuesta del usuario!  Después de que el usuario
                    // ve la explicación, vuelve a intentar solicitar el permiso.

                } else {

                    // No se necesita ninguna explicación, podemos solicitar el permiso.

                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS es un
                    // app-defined int constante.  El método callback obtiene el
                    // resultado de la solicitud.

                }
            }
            WSGranjaProducto granjap = new WSGranjaProducto();
            try {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lat[0] = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                        lon[0] = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();

                    }

                });
                g1 = granjap.traerGranjaProducto();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            Thread t = new Thread(this);
            t.start();

        } else {
            Intent ListSong = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(ListSong);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.login) {
            Intent ListSong = new Intent(MainActivity.this, login.class);
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

    @Override
    public void onClick(View v) {

    }


    @Override
    public void run() {


        if ( a  == false) {
                  ListaDeGranjaProducto = g1;
        }
        Filtros filtro = new Filtros();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String departamento = sharedpreferences.getString("Departamento", "departamento");



        if (!departamento.equals("") && !departamento.equals("departamento")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporLocalidad(g2, departamento);
        }

        String nombreGranjaSE = sharedpreferences.getString("Granja", "granja");
        if (!nombreGranjaSE.equals("") && !nombreGranjaSE.equals("granja")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporGranja(g2, nombreGranjaSE);
        }
        String TipoProductoSE = sharedpreferences.getString("tipoProducto", "tipoP");
        if (!TipoProductoSE.equals("") && !TipoProductoSE.equals("tipoP")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporTipo(g2, TipoProductoSE);
        }
        String CalidadProductoSE = sharedpreferences.getString("CalidadProducto", "calidadP");
        if (!CalidadProductoSE.equals("") && !CalidadProductoSE.equals("calidadP")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.FiltroCalidad(g2, CalidadProductoSE);
        }

        String ProductoSE = sharedpreferences.getString("Producto", "producto");
        if (!ProductoSE.equals("") && !ProductoSE.equals("producto")) {
            ArrayList<mnGranjaProducto> g2 = ListaDeGranjaProducto;
            ListaDeGranjaProducto = filtro.filtrarporProducto(g2, ProductoSE);
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


        final ArrayList<listadoProducto> listaProducto = new ArrayList<listadoProducto>();
        listadoProducto p1 ;
        //soluciona hilo
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
        lista = (ListView)findViewById(R.id.ListProductoGranjasl);

        final adaptadorListadoProducto adapter = new adaptadorListadoProducto(this, listaProducto);


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


    public void setLocation(Location location) {
        this.location = location;
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
