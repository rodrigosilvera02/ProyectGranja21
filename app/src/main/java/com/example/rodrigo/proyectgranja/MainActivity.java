package com.example.rodrigo.proyectgranja;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GridView.OnClickListener, Runnable, OnQueryTextListener, OnActionExpandListener {
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
    private ArrayList<GranjaProducto> ListaDeGranjaProducto;

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
            Filtros filtro =  new Filtros();
            ArrayList<GranjaProducto> g1 = granjap.traerGranjaProducto();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
