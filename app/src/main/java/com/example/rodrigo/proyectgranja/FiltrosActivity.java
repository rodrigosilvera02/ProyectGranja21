package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.os.Handler;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FiltrosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GridView.OnClickListener, Runnable, AdapterView.OnItemSelectedListener {
    private Spinner listadepartamento;
    private Spinner listaGranja;
    private EditText buscarDistankm;
    private Spinner listaTipoProdu;
    private Spinner listaNombreGranja;
    private Button establecerFiltros;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //   setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        establecerFiltros = (Button)findViewById(R.id.FiltrosOk);
        establecerFiltros.setOnClickListener(this);
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
        getMenuInflater().inflate(R.menu.menu_2main, menu);
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
        if (id == R.id.modificarusuario) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.modificarusuario) {
            // Handle the camera action
        }/*else if (id == R.id.nav_gallery) {

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
                                final float establecerKm  = Float.parseFloat(String.valueOf(buscarDistankm.getText()));
                                if(establecerKm != 0.0){

                                    editor.putFloat("Kilometros", establecerKm);

                                }



                                editor.commit();
                                finish();
                            }

    //  Intent ListSong = new Intent(FiltrosActivity.this, Main2Activity.class);
    // startActivity(ListSong);
});


        } catch (Exception e) {
        e.printStackTrace();
        }
        };
        };
        thread.start();
    }

    @Override
    public void run() {
        listadepartamento = (Spinner)findViewById(R.id.listaDepartamento);
listaTipoProdu = (Spinner)findViewById(R.id.spinner4);
        listaNombreGranja =  (Spinner)findViewById(R.id.listaNombreGranja);
        buscarDistankm = (EditText)findViewById(R.id.edtEstablecerKm);
  //cargarDatos En Spinner
WStipoProducto  tipoProducto  = new WStipoProducto();
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
        //------------ListaGranja----------------------------------//
        final WSGranja  listaGranja  = new WSGranja();
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
        if(!NombreGranjap.equals("granja")){
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
                                        listaNombreGranja.setSelection(finalI);
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

//testiar y arreglar el tema de size y por q no vicia el Spenner
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
                                                                if(!NombreGranjap.equals("granja")){
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
                                                                                                listaNombreGranja.setSelection(finalI);
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
                                                                    listaNombreGranja.setSelection(0);
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
                        if(parent.getItemAtPosition(position).equals("")){
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                listaNombreGranja.setAdapter(null);
                                                listaNombreGranja.setAdapter(dataAdapter2);
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                };
                            };
                            thread.start();
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
        float buscarkm = sharedpreferences.getFloat("Kilometros", 0);
        if(buscarkm != 0.0){
            final String km = Float.toString(buscarkm);

            Thread thread2 = new Thread(){
                @Override
                public void run() {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                    buscarDistankm.setText(km);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            };
            thread2.start();

            }


        // aca me fijaria si en la sesion existe el filtro y si existe seteo con lo q tiene en el parametro haciendo un
        //recorrido del array y setiandolo en la posiscion del mismo +

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
