package com.example.rodrigo.proyectgranja;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.view.View.VISIBLE;

public class Registrarse extends AppCompatActivity  implements GridView.OnClickListener, Runnable{
    private Button registrar;
    private EditText Nickname;
    private EditText Nombre;
    private EditText Apellido;
    private EditText Password;
    private EditText passwoed1;
    private EditText Email;
    private EditText telefono;
    private EditText direccion;
    private EditText txtresultado;
    private TextView txtErrorregistrar;
    private Handler handler = new Handler();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Nickname = (EditText) findViewById(R.id.edNickname);
        Nombre = (EditText) findViewById(R.id.edNombre);
        Apellido = (EditText) findViewById(R.id.edapellido);
        Password = (EditText) findViewById(R.id.edpassword);
        passwoed1 = (EditText) findViewById(R.id.edpaswwordr);
        Email = (EditText) findViewById(R.id.edEmail);
        telefono = (EditText) findViewById(R.id.edtelefono);
        direccion = (EditText) findViewById(R.id.edDirecion);
        registrar = (Button) findViewById(R.id.btnRegistrar);


        registrar.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Thread t = new Thread(this);
        t.start();
        txtErrorregistrar = (TextView)findViewById(R.id.txtUsuarioExiste);
        txtErrorregistrar.setVisibility(View.INVISIBLE);
        txtErrorregistrar = (TextView)findViewById(R.id.txtpasswordnoconinciden);
        txtErrorregistrar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void run() {
        try {

            String nombre = String.valueOf(Nombre.getText().toString());
            String apellido = String.valueOf(Apellido.getText().toString());
            String nickname = String.valueOf(Nickname.getText().toString());
            String password = String.valueOf(Password.getText().toString());
            String password1 = String.valueOf(passwoed1.getText().toString());
            String email = String.valueOf(Email.getText().toString());
            String Telefono = String.valueOf(telefono.getText().toString());
            String Direccion = String.valueOf(direccion.getText().toString());
            WSUsuario ws = new WSUsuario();
            String validar = ws.validarNickName(nickname);
            if (password.equals(password1)) {

                if (validar.equals("false")) {
                    final String resultado = String.valueOf(ws.agregarUsuario(nickname, password, nombre, apellido, email, Direccion, Telefono));
                    final int idUsuario = ws.traerIdUsuario(nickname);
                    WScliente ws1 = new WScliente();
                    final String resultadocliente = ws1.nuevoUsuarioCliente(idUsuario);
                    if (resultadocliente.equals("true")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent ListSong = new Intent(Registrarse.this, login.class);
                                startActivity(ListSong);

                            }
                        });
                    }

                }

                if(validar.equals("true")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtErrorregistrar = (TextView) findViewById(R.id.txtUsuarioExiste);
                            txtErrorregistrar.setVisibility(VISIBLE);

                        }
                    });

                }
                }

            else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtErrorregistrar = (TextView)findViewById(R.id.txtpasswordnoconinciden);
                        txtErrorregistrar.setVisibility(VISIBLE);
                    }
                });
                if(validar.equals("true")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtErrorregistrar = (TextView) findViewById(R.id.txtUsuarioExiste);
                            txtErrorregistrar.setVisibility(VISIBLE);

                        }
                    });

                }

            }
        }catch(Exception ex){
            Log.e("Myactivity", "Error", ex);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registrarce Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rodrigo.proyectgranja/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registrarce Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rodrigo.proyectgranja/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }
}
