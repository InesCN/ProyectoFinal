package com.example.ins.chachachachi;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;


import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        //Escuchador de los beacons
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {

                for (int i = 0; i < beacons.size(); i++) {

                    showNotification("Nuevo beacon ", "Major: " + beacons.get(i).getMajor() + " Minor: " + beacons.get(i).getMinor());

                    System.out.println("###########" + beacons.get(i).toString());

                }

            }

            @Override
            public void onExitedRegion(BeaconRegion region) {

                showNotification("Beacon fuera de rango", "Un beacon ha salido del rango");

            }
        });

        //Conexiones  con los beacons
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new BeaconRegion("monitored region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 53583, 12200));//agl001

                beaconManager.startMonitoring(new BeaconRegion("monitored region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 100, 100));//agl013

            }
        });

    }

    //Método para mostrar notificaciones en el teléfono
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this).setSmallIcon(R.drawable.beacon).setContentTitle(title).setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent).build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}

