package com.example.rodrigo.proyectgranja;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GridView.OnClickListener, Runnable, OnQueryTextListener, OnActionExpandListener  {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Granja = "granja";
    public static final String Departamento = "departamento";
    public static final String Producto = "producto";
    public static final float Kilometros = 0;
    public static final String tipoProducto = "tipoP";
public static final ArrayList<GranjaProducto> prod1 = null;
    SharedPreferences sharedpreferences;
    private String texto1;
    private ListView lista;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        texto1 = sharedpreferences.getString("Name","nameKey");
        if(texto1.equals("nameKey")) {
            setContentView(R.layout.activity_main);
    lista = (ListView)findViewById(R.id.ListProductoGranjasl);
            Thread t = new Thread(this);
            t.start();

        }
        else{
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void run() {
        WSGranjaProducto granjap =  new WSGranjaProducto();
        try {
           ArrayList<GranjaProducto> g1 = granjap.traerGranjaProducto();
            final ArrayList<listadoProducto> listaProducto = new ArrayList<listadoProducto>();
            listadoProducto p1 ;
                for(int i = 0;i<g1.size();i++){
              p1 =new listadoProducto();
                    p1.setNombreProducto(g1.get(i).getNomProd());
                    p1.setTipoProducto(g1.get(i).getTipoProducto());
                    p1.setCalidadProducto(g1.get(i).getCalidad());
                    p1.setNombreGranja(g1.get(i).getNombreGranja());
                    p1.setImgProducto(g1.get(i).getImgProg());
                    p1.setPrecioProducto(String.valueOf(g1.get(i).getPrecio()));
                    listaProducto.add(p1);
                }
            lista = (ListView)findViewById(R.id.ListProductoGranjasl);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Thread t = new Thread(this);
                    t.start();

                }
            });
            lista.setAdapter(new adaptadorListadoProducto(this,R.layout.listaproducto,listaProducto));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getApplicationContext(), "EXPAND", Toast.LENGTH_SHORT).show();
        return true;

    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
