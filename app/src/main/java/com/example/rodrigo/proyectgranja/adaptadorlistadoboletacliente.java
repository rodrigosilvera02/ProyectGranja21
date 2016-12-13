package com.example.rodrigo.proyectgranja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Logica.Boleta;
import com.example.rodrigo.proyectgranja.Logica.Granja;
import com.example.rodrigo.proyectgranja.Manager.mnCarrito;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 14/10/2016.
 */

public class adaptadorlistadoboletacliente extends BaseAdapter  {


    protected Activity activity;
    protected ArrayList<Boleta> items;


    public adaptadorlistadoboletacliente(Activity activity, int listaproducto, ArrayList<Boleta> items) {
        this.activity = activity;
        this.items = items;
    }

    public adaptadorlistadoboletacliente(activity_mostrar_boleta mainActivity, ArrayList<Boleta> listabol) {
        this.activity = mainActivity;
        this.items = listabol;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public void clear() {
        items.clear();
    }
    public void addAll(ArrayList<Boleta> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.listaboleta, null);
        }

        final Boleta dir = items.get(position);

        TextView IdBoleta = (TextView) v.findViewById(R.id.txtNumeroBol);
        int idbol = dir.getId();
         IdBoleta.setText(String.valueOf(idbol));
        TextView fecha = (TextView) v.findViewById(R.id.txtFechaBol);
        String fechabol = String.valueOf(dir.getFecha());
        fecha.setText(fechabol);
        TextView estado = (TextView) v.findViewById(R.id.txtEstado);
        String Estado = String.valueOf(dir.isPediListo());

        String e = null;
        if(Estado.equals("true")){
            e = "Listo";
        }
        if(Estado.equals("false")){
            e = "En Proceso";
        }
        estado.setText(e);
        TextView precioTotal = (TextView) v.findViewById(R.id.txtPrecio);
        String total = String.valueOf(dir.getPrecioTotal());
        precioTotal.setText(total);
        Button verProdBol = (Button)v.findViewById(R.id.btnVerProducto);
        verProdBol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = activity.getSharedPreferences(Main2Activity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("idBoleta",dir.getId());

                editor.commit();
                Intent ListSong = new Intent(activity, ActivityMostrarDatosBoleta.class);
                activity.startActivity(ListSong);
            }
        });
        return v;
    }

}
