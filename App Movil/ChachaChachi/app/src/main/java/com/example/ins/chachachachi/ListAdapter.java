package com.example.ins.chachachachi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estimote.coresdk.recognition.packets.Beacon;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


public class ListAdapter extends BaseAdapter {
    protected Activity activity;
    private ArrayList<ElementoBeacon> lista = new ArrayList<>();


   // private ListView lista;
    //private ImageView img;
    //int i = 0;
  //  Random random = new Random();

    private final Activity context;
    //Lista de beacons
    List<Beacon> beacons;

    public ListAdapter(Activity context, List<Beacon> beacons) {
        this.context = context;
        this.beacons = beacons;
    }

    public void replaceWith(Collection<Beacon> newBeacons) {
        this.beacons.clear();
        this.beacons.addAll(newBeacons);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beacons.size();
    }

    @Override
    public Object getItem(int position) {
        return beacons.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    //Método para mostrar los datos de los beacons en TextFields
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.lista_beacons, null, true);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.nombreBeacon);
        txtNombre.setText(beacons.get(position).getMacAddress().toString().replace("[]", ""));

        TextView txtMajorMinor = (TextView) rowView.findViewById(R.id.txtMajorMinor);
        txtMajorMinor.setText("Major: " + beacons.get(position).getMajor() + " Minor: " + beacons.get(position).getMinor());

        /*img = (ImageView) rowView.findViewById(R.id.imagen);

        listaImagenes.add(R.drawable.beaconblue);
        listaImagenes.add(R.drawable.beacondarkblue);
        listaImagenes.add(R.drawable.beacondarkgreen);
        listaImagenes.add(R.drawable.beaconpink);

        while (i < beacons.size()) {


            int color = i % 4;
            System.out.println("~~INDICE " + i);
            int idImagen = listaImagenes.get(color);

            // Crear ImageView con ese id
            img.setImageResource(idImagen);
            i++;
        }*/
        return rowView;
    }
}
