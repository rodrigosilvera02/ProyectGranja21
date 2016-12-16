package com.example.rodrigo.proyectgranja;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rodrigo.proyectgranja.Logica.BolProd;
import com.example.rodrigo.proyectgranja.Logica.Boleta;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 14/10/2016.
 */

public class adaptadorlistadoprodbolCliente extends BaseAdapter  {


    protected Activity activity;
    protected ArrayList<BolProd> items;


    public adaptadorlistadoprodbolCliente(Activity activity, int listaproducto, ArrayList<BolProd> items) {
        this.activity = activity;
        this.items = items;
    }

    public adaptadorlistadoprodbolCliente(ActivityMostrarDatosBoleta mainActivity, ArrayList<BolProd> listabol) {
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
    public void addAll(ArrayList<BolProd> category) {
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
            v = inf.inflate(R.layout.listaproductoboleta, null);
        }

        final BolProd dir = items.get(position);
        TextView NombreProducto = (TextView) v.findViewById(R.id.txtNombreProducto);
        String  idbol = dir.getNombreProd();
        NombreProducto.setText(String.valueOf(idbol));
        TextView Cantidad = (TextView) v.findViewById(R.id.txtCantidad);
        int cantidad = dir.getCantidad();
        Cantidad.setText(String.valueOf(cantidad));
        TextView Precio = (TextView) v.findViewById(R.id.txtPrecio);
        String precio = String.valueOf(dir.getPrecio());
        Precio.setText(precio);
        TextView precioTotal = (TextView) v.findViewById(R.id.txtPreciototal);
        String total = String.valueOf(dir.getPrecioTotal());
        precioTotal.setText(total);
        TextView Estado = (TextView) v.findViewById(R.id.txtEstado);
        String estado = dir.getEstado();
        Estado.setText("  "+ estado);

        return v;
    }

}
