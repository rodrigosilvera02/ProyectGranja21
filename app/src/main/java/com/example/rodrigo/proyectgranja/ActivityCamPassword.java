package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.WebService.WSUsuario;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ActivityCamPassword extends AppCompatActivity  implements  GridView.OnClickListener,NavigationView.OnNavigationItemSelectedListener, Runnable, MenuItemCompat.OnActionExpandListener{
private Button Aceptar;
    private Handler handler = new Handler();
    private TextView ERRORPASS;
    private TextView ERRORPASSV;
    private EditText PASSViejo;
    private EditText PASSNuevo;
    private EditText PASSNuevo1;
    private String Passviejo;
    private String Passnuevo;
    private String Passnuevo1;
    private int idUsuario;
    private String[] Resultado = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ERRORPASS = (TextView)findViewById(R.id.txtERRORPASS);

        ERRORPASSV = (TextView)findViewById(R.id.txtERRORPASSV);

        Aceptar = (Button)findViewById(R.id.btnAceptar);
        Aceptar.setOnClickListener(this);
    }
//hacer un seguimiento por q no cambia

    public void onClick(View v) {
        Thread t = new Thread(this);
        t.start();
        ERRORPASSV.setVisibility(View.INVISIBLE);
        ERRORPASS.setVisibility(View.INVISIBLE);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent ListSong = new Intent(ActivityCamPassword.this, MainActivity.class);
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
        }   if(id == R.id.MosCarrito) {
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
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void run() {

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        idUsuario = sharedpreferences.getInt("idUsuario",'0');
        PASSViejo =(EditText)findViewById(R.id.edtPassviejo);
        Passviejo = String.valueOf(PASSViejo.getText());
        PASSNuevo = (EditText)findViewById(R.id.edtPassNuevo);
        PASSNuevo1 = (EditText)findViewById(R.id.edtPassnuevo1);
        Passnuevo = String.valueOf(PASSNuevo.getText());
        Passnuevo1 = String.valueOf(PASSNuevo1.getText());
        final WSUsuario wsUsuario = new WSUsuario();

        Thread thread4 = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Resultado[0] = wsUsuario.validadPassword (idUsuario,Passviejo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                    }
                });
            };
        };

        try {
            thread4.start();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
try {
    handler.post(new Runnable() {
        @Override
        public void run() {
            if (Resultado[0].equals("true")) {
                if (Passnuevo.equals(Passnuevo1)) {
                    Thread thread5 = new Thread() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        wsUsuario.modificarPassword(idUsuario, Passnuevo);
                                        Intent ListSong = new Intent(ActivityCamPassword.this, Main2Activity.class);
                                        startActivity(ListSong);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (XmlPullParserException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        ;
                    };
                    thread5.start();

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ERRORPASS.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
            if (Resultado[0].equals("false")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ERRORPASSV.setVisibility(View.VISIBLE);
                    }
                });

            }
        }
    });

}catch (NullPointerException e){
    e.printStackTrace();
}
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    /**
     * Created by Rodrigo on 31/10/2016.
     */

    public static class adaptadorListaMostrarProCarrito {


    }
}
