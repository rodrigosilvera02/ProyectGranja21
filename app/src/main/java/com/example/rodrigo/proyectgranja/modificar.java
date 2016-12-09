package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Logica.Usuario;
import com.example.rodrigo.proyectgranja.WebService.WSUsuario;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class modificar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GridView.OnClickListener, Runnable {
    private Handler handler = new Handler();
    private Usuario usuario;
    private TextView nombre;
    private TextView Apellido;
    private TextView Email;
    private TextView Direccion;
    private TextView Telefono;
    private Boolean onclik = false;
    private Boolean pasada2 = true;
    private ArrayList<String> u1;
    private String nickname;
    private Button modiusuario;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
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
        nickname = sharedpreferences.getString("Name", "nameKey");
        WSUsuario webservice = new WSUsuario();
        try {
            usuario = webservice.infoUsuario(nickname);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(this);
        t.start();
        modiusuario = (Button) findViewById(R.id.btnModificar);
        modiusuario.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
            Intent ListSong = new Intent(modificar.this, MainActivity.class);
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

        if (id == R.id.modificarusuario) {


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
    public void onClick(View view) {
        Thread t = new Thread(this);
        t.start();

        onclik = true;


    }

    @Override
    public void run() {

        try {


            if (onclik == false) {
                nombre = (TextView) findViewById(R.id.edtmodNombre);
                Apellido = (TextView) findViewById(R.id.edtmodApellido);
                Email = (TextView) findViewById(R.id.edtmodEmail);
                Direccion = (TextView) findViewById(R.id.edtmodDireccion);
                Telefono = (TextView) findViewById(R.id.edtmodTelefono);


                nombre.setText(usuario.getNombre());
                Apellido.setText(usuario.getApellido());
                Email.setText(usuario.getEmail());
                Direccion.setText(usuario.getDireccion());
                Telefono.setText(usuario.getTelefono());

            }
        } catch (Exception ex) {
            Log.e("modificar", "Error", ex);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (onclik == true) {
                    try {

                        WSUsuario webservice = new WSUsuario();
                        nombre = (TextView) findViewById(R.id.edtmodNombre);
                        Apellido = (TextView) findViewById(R.id.edtmodApellido);
                        Email = (TextView) findViewById(R.id.edtmodEmail);
                        Direccion = (TextView) findViewById(R.id.edtmodDireccion);
                        Telefono = (TextView) findViewById(R.id.edtmodTelefono);
                        Thread t = new Thread(this);
                        t.start();
                        onclik = false;
                        usuario.setNombre(String.valueOf(nombre.getText()));
                        usuario.setApellido(String.valueOf(Apellido.getText()));
                        usuario.setEmail(String.valueOf(Email.getText()));
                        usuario.setDireccion(String.valueOf(Direccion.getText()));
                        usuario.setTelefono(String.valueOf(Telefono.getText()));
                        webservice.modificarUsuario(usuario);


                    } catch (Exception ex) {
                        Log.e("modificar", "Error", ex);
                    }
                    Intent ListSong = new Intent(modificar.this, Main2Activity.class);
                   startActivity(ListSong);

                }

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "modificar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rodrigo.proyectgranja/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "modificar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rodrigo.proyectgranja/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

