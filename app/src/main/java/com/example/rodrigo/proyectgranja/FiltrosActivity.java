package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.os.Handler;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Logica.CalidadProducto;
import com.example.rodrigo.proyectgranja.Logica.TipoProducto;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;
import com.example.rodrigo.proyectgranja.WebService.WSCalidad;
import com.example.rodrigo.proyectgranja.WebService.WSGranja;
import com.example.rodrigo.proyectgranja.WebService.WStipoProducto;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class FiltrosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GridView.OnClickListener, Runnable, AdapterView.OnItemSelectedListener {
    private Spinner listadepartamento;
    private Spinner listaGranja;
    private Spinner listaCalidad;
    private EditText buscarDistankm;
    private Spinner listaTipoProdu;
    private Spinner listaNombreGranja;
    private Button establecerFiltros;
    private TextView buscarnombre;
    private TextView km;
    Handler handler;
    private String texto1;
    private CheckBox checkGranja;
    private CheckBox checkKm;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;
    private Double lat;
    private Double lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        texto1 = sharedpreferences.getString("Name","nameKey");
        if(!texto1.equals("")&& !texto1.equals("nameKey")) {
            setContentView(R.layout.activity_filtros);
           Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           setSupportActionBar(toolbar);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
        else{
            setContentView(R.layout.filtrossl);
            Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
         setSupportActionBar(toolbar1);

        }


        establecerFiltros = (Button)findViewById(R.id.FiltrosOk);
        establecerFiltros.setOnClickListener(this);
        float a = sharedpreferences.getFloat("Kilometros", 0 );

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
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        texto1 = sharedpreferences.getString("Name","nameKey");
        if(!texto1.equals("")&& !texto1.equals("nameKey")) {
            getMenuInflater().inflate(R.menu.main2, menu);
            MenuItem searchItem = menu.findItem(R.id.menu3_buscar);
            searchItem.setVisible(false);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_2main, menu);

        }
        MenuItem configurarfil = menu.findItem(R.id.filtros);
        configurarfil.setVisible(false);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.modificarusuario) {
            return true;
        }
        if (id == R.id.home) {
            Intent ListSong = new Intent(this, MainActivity.class);
            startActivity(ListSong);
            return true;
        }
        if (id == R.id.Cerrarsesión) {
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();
            editor.commit();
            finish();
            Intent ListSong = new Intent(FiltrosActivity.this, MainActivity.class);
            startActivity(ListSong);

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.modificarusuario) {
            Intent ListSong = new Intent(this, modificar.class);
            startActivity(ListSong);
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

        }
        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        final Intent ListSong1 = new Intent(this, MainActivity.class);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listadepartamento = (Spinner)findViewById(R.id.listaDepartamento);
                                listaGranja  = (Spinner)findViewById(R.id.listaNombreGranja);
                                listaTipoProdu=(Spinner)findViewById(R.id.spinner4);
                                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                final String Departamento  = String.valueOf(listadepartamento.getSelectedItem());
                                if(!Departamento.equals("null")){

                                    editor.putString("Departamento",Departamento);

                                }
                                final String granja  = String.valueOf(listaGranja.getSelectedItem());
                                if(!granja.equals("null")){
                                    editor.putString("Granja",granja);

                                }

                                final String TipoProducto  = String.valueOf(listaTipoProdu.getSelectedItem());
                                if(!TipoProducto.equals("null")){

                                    editor.putString("tipoProducto",TipoProducto);

                                }

                                final String calidad  = String.valueOf(listaCalidad.getSelectedItem());
                                if(!TipoProducto.equals("null")){

                                    editor.putString("CalidadProducto",calidad);

                                }
                                float establecerKm = 0;

                                String p = String.valueOf(buscarDistankm.getText());
                                if(String.valueOf(buscarDistankm.getText()).equals(null)|| String.valueOf(buscarDistankm.getText()).equals("")){
                                    establecerKm = 0;
                                    editor.putFloat("Kilometros", establecerKm);
                                }
                                else{
                                    try{


                                        establecerKm   = Float.parseFloat(String.valueOf(buscarDistankm.getText()));
                                        editor.putFloat("Kilometros", establecerKm);



                                    }catch (NumberFormatException e){
                                        e.printStackTrace();
                                    }

                                }



                                editor.commit();




                            }


});


        } catch (Exception e) {
        e.printStackTrace();
        }
        };


        };
        thread.start();
        Intent ListSong = new Intent(FiltrosActivity.this, MainActivity.class);
        startActivity(ListSong);


    }

    @Override
    public void run() {
        checkKm = (CheckBox)findViewById(R.id.checkProximidad);
        checkGranja = (CheckBox)findViewById(R.id.checkBox);
        listadepartamento = (Spinner)findViewById(R.id.listaDepartamento);
listaTipoProdu = (Spinner)findViewById(R.id.spinner4);
        listaNombreGranja =  (Spinner)findViewById(R.id.listaNombreGranja);
        listaCalidad = (Spinner)findViewById(R.id.spnCalidad);
        buscarDistankm = (EditText)findViewById(R.id.edtEstablecerKm);
        buscarnombre = (TextView)findViewById(R.id.textView22) ;
        km = (TextView)findViewById(R.id.textView21) ;
  //cargarDatos En Spinner
WStipoProducto tipoProducto  = new WStipoProducto();
    final    List<String> tipo = new ArrayList<String>();
        try {
            listaTipoProdu.setOnItemSelectedListener(this);
            ArrayList <TipoProducto> Lista = tipoProducto.traerListaTiposProductos();
            tipo.add("");
           for(int i = 0;i<Lista.size();i++){
               tipo.add(Lista.get(i).getTipoProducto());
           }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // attaching data adapter to spinner

                final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Thread thread3 = new Thread(){
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                listaTipoProdu.setAdapter(dataAdapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        thread3.start();

        final SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String tipop = sharedpreferences.getString("tipoProducto", "tipoP");
        if(!tipop.equals("tipoP")){
            for(int i = 0;i<tipo.size();i++){
                if(tipop.equals(tipo.get(i))){
                    final int finalI = i;
                    Thread thread5 = new Thread(){
                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listaTipoProdu.setSelection(finalI);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        };
                    };
                    thread5.start();
                }
            }
        }
        //--------------fin TipoProducto --------------------------//
        //lista de CalidadProducto

        WSCalidad calidadp  = new WSCalidad();
        final    List<String> calidad = new ArrayList<String>();
        try {
            listaCalidad.setOnItemSelectedListener(this);
            ArrayList <CalidadProducto> Lista = calidadp.cargarlistaCalidad();
            calidad.add("");
            for(int i = 0;i<Lista.size();i++){
                calidad.add(Lista.get(i).getCalidadProductto());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // attaching data adapter to spinner

        final ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calidad);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Thread thread7 = new Thread(){
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listaCalidad.setAdapter(dataAdapter6);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        thread7.start();


        String calidad1 = sharedpreferences.getString("CalidadProducto", "calidadP");
        if(!calidad1.equals("calidadP")){
            for(int i = 0;i<calidad.size();i++){
                if(calidad1.equals(calidad.get(i))){
                    final int finalI = i;
                    Thread thread8 = new Thread(){
                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listaCalidad.setSelection(finalI);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        };
                    };
                    thread8.start();
                }
            }
        }







        //------------ListaGranja----------------------------------//
        final WSGranja listaGranja  = new WSGranja();
        final    List<String> NombreGranja = new ArrayList<String>();
        try {
            listaNombreGranja.setOnItemSelectedListener(this);
           ArrayList <String> ListaNombre = listaGranja.traergranja();
            NombreGranja.add("");
            for(int i = 0;i<ListaNombre.size();i++){
                NombreGranja.add(ListaNombre.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // attaching data adapter to spinner

        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NombreGranja);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

        listaNombreGranja.setAdapter(dataAdapter1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();
        String NombreGranjap = sharedpreferences.getString("Granja", "granja");
        if(!NombreGranjap.equals("granja") && !NombreGranjap.equals("")){
            for(int i = 0;i<NombreGranja.size();i++){
                if(NombreGranjap.equals(NombreGranja.get(i))){
                    final int finalI = i;
                    Thread thread4 = new Thread(){
                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        checkGranja.setChecked(true);

                                       listaNombreGranja.setSelection(finalI);
                                        listaNombreGranja.setVisibility(VISIBLE);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        };
                    };
                    thread4.start();
                }
            }
        }


        //-----------------------------------------------------------------------------------//
        final WSGranja  listadepartamento1  = new WSGranja();
        final    List<String> granjaLocalidad = new ArrayList<String>();
        try {
            listadepartamento.setOnItemSelectedListener(this);
            ArrayList <String> ListaDepartamento = listadepartamento1.traerLocalidad();
            granjaLocalidad.add("");
            for(int i = 0;i<ListaDepartamento.size();i++){
                granjaLocalidad.add(ListaDepartamento.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // attaching data adapter to spinner

        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, granjaLocalidad);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final   List<String> NombreGranja1 = new ArrayList<String>();
        final ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NombreGranja1);
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


        listadepartamento.setAdapter(dataAdapter2);
        listadepartamento.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {

                        if(!parent.getItemAtPosition(position).equals("")){
                            final String FiltroGranjaS = (String) listaNombreGranja.getSelectedItem();
                            final String FiltroLocalidadS = (String) parent.getItemAtPosition(position);


                            Thread thread2 = new Thread(){
                                @Override
                                public void run() {
                                        try {
                                           final boolean DepartamentoGranja=  listaGranja.verificargranjalocalidad(FiltroLocalidadS,FiltroGranjaS);
                                          final ArrayList<String> GranjaDepartamento = listaGranja.traerGranjaDepa((String) parent.getItemAtPosition(position));
                                                if(NombreGranja.size()!=0) {
                                                    NombreGranja1.removeAll(NombreGranja1);
                                                }
                                            NombreGranja1.add("");
                                            for(int i = 0;i<GranjaDepartamento.size();i++){
                                                NombreGranja1.add(GranjaDepartamento.get(i));
                                            }



                                                Thread thread7 = new Thread(){
                                                @Override
                                                public void run() {
                                                    try {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {


                                                                Thread thread = new Thread(){
                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                    listaNombreGranja.setAdapter(null);
                                                                                    listaNombreGranja.setAdapter(dataAdapter5);
                                                                                }
                                                                            });
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    };
                                                                };
                                                                thread.start();
                                                                String NombreGranjap = sharedpreferences.getString("Granja", "granja");
                                                                            if(!NombreGranjap.equals("granja")&& !NombreGranjap.equals("")){
                                                                                for(int i = 0;i<NombreGranja1.size();i++){
                                                                                    if(NombreGranjap.equals(NombreGranja1.get(i))){
                                                                                        final int finalI = i;
                                                                                        Thread thread4 = new Thread(){
                                                                                            @Override
                                                                                            public void run() {
                                                                                                try {
                                                                                                    runOnUiThread(new Runnable() {
                                                                                                        @Override
                                                                                                        public void run() {
                                                                                                            checkGranja.setChecked(true);

                                                                                                            listaNombreGranja.setSelection(finalI);
                                                                                                            listaNombreGranja.setVisibility(VISIBLE);
                                                                                                        }
                                                                                                    });
                                                                                                } catch (Exception e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                            };
                                                                                        };
                                                                                        thread4.start();
                                                                        }
                                                                    }
                                                                }
                                                                else {
                                                                    //  listaNombreGranja.setSelection(0);
                                                                }

                                                            }
                                                        });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                };
                                            };
                                            thread7.start();




                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        } catch (XmlPullParserException e1) {
                                            e1.printStackTrace();
                                        }};
                            };
                            thread2.start();








                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
        thread1.start();

        String granjaLocalidadp = sharedpreferences.getString("Departamento", "departamento");
        if(!granjaLocalidadp.equals("departamento")){
            for(int i = 0;i<granjaLocalidad.size();i++){
                if(granjaLocalidadp.equals(granjaLocalidad.get(i))){
                    final int finalI = i;
                    Thread thread2 = new Thread(){
                        @Override
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                    listadepartamento.setSelection(finalI);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        };
                    };
                    thread2.start();
                }
            }
        }



//-------------------------------------------------------------------------------------------------//


        System.out.print(sharedpreferences.getFloat("Kilometros", 0));
        String buscars = String.valueOf(sharedpreferences.getFloat("Kilometros", 0));
        final float buscarkm=sharedpreferences.getFloat("Kilometros", 0);
        if(buscarkm != 0.0 ){
            final String km = Float.toString(buscarkm);
            Thread thread2 = new Thread(){
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 buscarDistankm.setText(km);
                                buscarDistankm.setVisibility(VISIBLE);
                                checkKm.setChecked(true);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            };
            thread2.start();

            }

       checkGranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean click  = checkGranja.isChecked();
                if (click == true) {
                    listaNombreGranja.setVisibility(VISIBLE);
                    buscarnombre.setVisibility(VISIBLE);
                    if(checkKm.isChecked()==true){
                        km.setVisibility(INVISIBLE);
                        checkKm.setChecked(false);
                        buscarDistankm.setVisibility(INVISIBLE);
                        buscarDistankm.setText("");
                    }
                }
                if (click == false){
                    buscarnombre.setVisibility(INVISIBLE);
                    listaNombreGranja.setSelection(0);
                    listaNombreGranja.setVisibility(INVISIBLE);
                }

            }
        });

        checkKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean click  = checkKm.isChecked();
                if (click == true) {
                    buscarDistankm.setVisibility(VISIBLE);
                    km.setVisibility(VISIBLE);
                    if(checkGranja.isChecked()==true){
                        checkGranja.setChecked(false);
                        listaNombreGranja.setVisibility(INVISIBLE);
                        buscarnombre.setVisibility(INVISIBLE);
                        listaNombreGranja.setSelection(0);
                    }
                }
                if(click==false){
                    buscarDistankm.setVisibility(INVISIBLE);
                    km.setVisibility(INVISIBLE);
                    buscarDistankm.setText("");
                }
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
