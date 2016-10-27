package com.example.rodrigo.proyectgranja;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.WebService.WSUsuario;
import com.example.rodrigo.proyectgranja.WebService.WScliente;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static android.view.View.VISIBLE;

public class login extends AppCompatActivity implements GridView.OnClickListener, Runnable {

    private TextView Registrar;
    private Button loguearse;
    private EditText Nickname;
    private EditText Password;
    private TextView ErrorLogin;
    private Handler handler = new Handler();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Registrar = (TextView) findViewById(R.id.txtregistrarse);
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrar = new Intent(login.this, Registrarse.class);
                startActivity(registrar);
            }
        });
        Nickname = (EditText) findViewById(R.id.EdtUsuario);
        Password = (EditText) findViewById(R.id.EdtPassword);
        ErrorLogin = (TextView) findViewById(R.id.txtErrorlogin);
        loguearse = (Button) findViewById(R.id.BtnLogin);
        loguearse.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_2main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    @Override
    public void onClick(View view) {
        Thread t = new Thread(this);
        t.start();
        ErrorLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void run() {
        try {


         final   String nickname = String.valueOf(Nickname.getText().toString());
            String password = String.valueOf(Password.getText().toString());
            final WSUsuario webservice = new WSUsuario();
            final WScliente wscliente = new WScliente();
            final String valido = webservice.validarUsuario(nickname, password);
            if (valido.equals("true")) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Name", nickname);
                        int idCliente = 0;
                        try {
                            int idUsuario = webservice.traerIdUsuario(nickname);
                            idCliente =  wscliente.traerIdCliente(idUsuario);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                        editor.putInt("idCliente", idCliente);
                       //agregar el id a una variable de sesion
                        editor.commit();
                        finish();

                        Intent ListSong = new Intent(login.this, Main2Activity.class);
                        startActivity(ListSong);
            //hacer variable de session y poner el id del cliente
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ErrorLogin.setVisibility(VISIBLE);
                    }
                });


            }

        } catch (Exception ex) {
            Log.e("Myactivity", "Error", ex);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "login Page", // TODO: Define a title for the content shown.
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
                "login Page", // TODO: Define a title for the content shown.
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
