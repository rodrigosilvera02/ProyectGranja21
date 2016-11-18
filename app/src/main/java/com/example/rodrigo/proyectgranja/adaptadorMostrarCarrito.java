package com.example.rodrigo.proyectgranja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Manager.mnCarrito;
import com.example.rodrigo.proyectgranja.WebService.WSProductoCarrito;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rodrigo on 01/11/2016.
 */

public class adaptadorMostrarCarrito extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<mnCarrito> items;


    public adaptadorMostrarCarrito(Activity activity, int listaCarrito, ArrayList<mnCarrito> items) {
        this.activity = activity;
        this.items = items;
    }

    public adaptadorMostrarCarrito(ActivityMostrarCarrito mainActivity, ArrayList<mnCarrito> listaCarrito) {
        this.activity = mainActivity;
        this.items = listaCarrito;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<mnCarrito> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }
    @Override
    public Object getItem(final int position) {

        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int[] p = {position};
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.listacarrito, null);
        }

        final mnCarrito dir = items.get(position);
       TextView nombreGranja = (TextView)v.findViewById(R.id.txtNombreGranja);
        nombreGranja.setText(dir.getNombreGranja());
       TextView NombreProducto =  (TextView)v.findViewById(R.id.txtNombreProducto);
        NombreProducto.setText(dir.getNombreProdGranja());
        final EditText Cantidad =(EditText)v.findViewById(R.id.edtCantidad);
        Cantidad.setText(String.valueOf(dir.getCantidad()));
        TextView Precio = (TextView)v.findViewById(R.id.txtPrecio);
        Precio.setText(String.valueOf(dir.getPrecio()));
        float presio = dir.getPrecio();
        int cantidad = dir.getCantidad();
        float total = presio * cantidad;
        TextView total1 = (TextView) v.findViewById(R.id.txtPrecioTotal);
        total1.setText(String.valueOf(total));
        Button Modificar = (Button)v.findViewById( R.id.btnModificar);
        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
                int cantidad = Integer.parseInt(String.valueOf(Cantidad.getText()));
                try {

String a = wsProductoCarrito.modificarProdCar(dir.getIdProdCarrito(),cantidad);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        Button Eliminar = (Button)v.findViewById(R.id.btnEliminar);
        Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WSProductoCarrito wsProductoCarrito = new WSProductoCarrito();
                try {

                   wsProductoCarrito.EliminarProdCar(dir.getIdProdCarrito());


                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent ListSong = new Intent(activity, ActivityMostrarCarrito.class);
                activity.startActivity(ListSong);
            }

        });


        return v;
    }
}
