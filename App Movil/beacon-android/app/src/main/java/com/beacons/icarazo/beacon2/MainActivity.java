package com.beacons.icarazo.beacon2;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private BeaconRegion region;
    private ListAdapter adaptador;
    private ListView lista;
    private TextView txtNombre;
    private List<Beacon> listB = new ArrayList<>();
    BluetoothAdapter mBtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);

        final TextView txtNombre = (TextView) findViewById(R.id.nombreBeacon);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        beaconManager = new BeaconManager(this);

        //Escuchador de beacons por región
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, final List<Beacon> list) {
                if (!list.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @SuppressLint("ResourceAsColor")
                        @Override public void run() {
                            adaptador.replaceWith(list);

                        }
                    });
                }
            }
        });

        adaptador = new ListAdapter(this, listB);
        lista.setAdapter(adaptador);

        region = new BeaconRegion("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        if (!mBtAdapter.isEnabled()) {
            Toast.makeText(getBaseContext(), "Activando Bluetooth ... ", Toast.LENGTH_LONG).show();
            mBtAdapter.enable();
        }
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }
/*
    @Override
    public void onPause(){
        beaconManager.stopRanging(region);
        super.onPause();
    }*/

    private static final Map<String, List<String>> PLACES_BY_BEACONS;

    //Método para declarar las zonas más cercanas
    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("53583:12200", new ArrayList<String>(){{
            add("agl001");
            add("agl013");
        }});

        placesByBeacons.put("100:100", new ArrayList<String>(){{

            add("agl013");
            add("agl001");
        }});

        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    //Método para encontrar los beacons en las zonas más cercanas
    private List<String> placesNearBeacon(Beacon beacon){
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return  PLACES_BY_BEACONS.get(beaconKey);
        }
        return  Collections.emptyList();
    }
}
