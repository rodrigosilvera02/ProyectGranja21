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

import com.example.rodrigo.proyectgranja.Logica.Granja;
import com.example.rodrigo.proyectgranja.Manager.mnCarrito;
import com.example.rodrigo.proyectgranja.Manager.mnGranjaProducto;
import com.example.rodrigo.proyectgranja.WebService.WSGranja;
import com.google.android.gms.analytics.ExceptionParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Rodrigo on 14/10/2016.
 */

public class adaptadorlistadoProCarrito  extends BaseAdapter  {


    protected Activity activity;
    protected ArrayList<listadoProducto> items;


    public adaptadorlistadoProCarrito(Activity activity, int listaproducto, ArrayList<listadoProducto> items) {
        this.activity = activity;
        this.items = items;
    }

    public adaptadorlistadoProCarrito(Main2Activity mainActivity, ArrayList<listadoProducto> listaProducto) {
        this.activity = mainActivity;
        this.items = listaProducto;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    public void clear() {
        items.clear();
    }
    public void addAll(ArrayList<listadoProducto> category) {
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
            v = inf.inflate(R.layout.listaproducticarrito, null);
        }

        final listadoProducto dir = items.get(position);

        TextView nombreproducto = (TextView) v.findViewById(R.id.txtNombreProducto);
        nombreproducto.setText(dir.getNombreProducto());
        TextView tipoProducto = (TextView) v.findViewById(R.id.txtTipoProducto);
        tipoProducto.setText(dir.getTipoProducto());
        TextView calidadProducto = (TextView) v.findViewById(R.id.txtCalidadProducto);
        calidadProducto.setText(dir.getCalidadProducto());
        TextView nombreGranja = (TextView) v.findViewById(R.id.txtNombreGranja);
        nombreGranja.setText(dir.getNombreGranja());
        final View finalV = v;
        nombreGranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                Granja granja = new Granja();
                   granja.setNombre(dir.getNombreGranja());
                   granja.setGeoLat(Double.valueOf(dir.getLatGranja()));
                   granja.setGeoLong(Double.valueOf(dir.getLonGranja()));
                SharedPreferences sharedpreferences = activity.getSharedPreferences(Main2Activity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Granja1",dir.getNombreGranja());
                editor.putFloat("Lat",dir.getLatGranja());
                editor.putFloat("Lon",dir.getLonGranja());
                editor.commit();
//hacer bariable de sesion para poder mandar los datos al mapa y tomarlos



                Intent ListSong = new Intent(activity, MapsActivity.class);
                activity.startActivity(ListSong);
            }


        });
        TextView presioProducto = (TextView) v.findViewById(R.id.txtPrecio);
        presioProducto.setText(dir.getPrecioProducto());
        final EditText Cantidad = (EditText)v.findViewById(R.id.edtCantidad);
        final  TextView ErrorEnt = (TextView)v.findViewById(R.id.errorEnt);
        ImageView imagen = (ImageView) v.findViewById(R.id.imageView5);
        Button agregarCarrito  = (Button)v.findViewById(R.id.btnAgregarCarrito);
        agregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorEnt.setVisibility(View.INVISIBLE);
                String cantidad = String.valueOf(Cantidad.getText());
                int idCliente = dir.getIdCliente();
                int idGranja =  dir.getIdGranja();

                int idProducto = dir.getIdproductoGranja();

                  try {
                      int cantidad1 = Integer.parseInt(cantidad);
                      mnCarrito mnCarrito = new mnCarrito();
                      mnCarrito.setIdCliente(idCliente);
                      mnCarrito.setIdGranja(idGranja);
                      mnCarrito.setIdProdGran(idProducto);
                      mnCarrito.setCantidad(cantidad1);
                      mnCarrito.agregarProductoCarrito();
                      Cantidad.setText("");
                  }
                catch (XmlPullParserException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  catch (NumberFormatException e) {
                    ErrorEnt.setVisibility(View.VISIBLE);
                      Cantidad.setText("");
                  }



            }
        });
        final Bitmap[] a = new Bitmap[1];
        Thread thread4 = new Thread(){
            @Override
            public void run() {
                a[0] = getBitmapFromURL(dir.getImgProducto());

            };
        };
        thread4.start();
        try {
            thread4.join();

            imagen.setImageBitmap(a[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return v;
    }


    private Bitmap getBitmapFromURL(String src) {
        Bitmap bm = null;
        try {
            URL _url = new URL(src);
            URLConnection con = _url.openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {

        }
        return bm;


    }
}
