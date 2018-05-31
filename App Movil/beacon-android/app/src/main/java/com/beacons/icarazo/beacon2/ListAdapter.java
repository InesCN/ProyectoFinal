package com.beacons.icarazo.beacon2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.coresdk.recognition.packets.Beacon;


import java.util.Collection;
import java.util.List;


public class ListAdapter extends BaseAdapter {

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

        return  position;
    }

    //Método para mostrar los datos de los beacons en TextFields
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView= inflater.inflate(R.layout.lista_beacons, null, true);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.nombreBeacon);

        TextView txtMajorMinor = (TextView) rowView.findViewById(R.id.txtMajorMinor);
        txtNombre.setText(beacons.get(position).getMacAddress().toString().replace("[]", ""));
        txtMajorMinor.setText("Major: "+ beacons.get(position).getMajor() + " Minor: " + beacons.get(position).getMinor());
        /*if (beacons.get(0).getMacAddress() == txtNombre.getText()){
            txtNombre.setTextColor(R.color.verde);
        }
         if (beacons.get(1).getMacAddress() == txtNombre.getText()){
            txtNombre.setBackgroundColor(R.color.amarillo);
        }else if (beacons.get(2).getMacAddress() == txtNombre.getText()){
            txtNombre.setBackgroundColor(R.color.rojo);
        }*/

        return rowView;
    }
}
