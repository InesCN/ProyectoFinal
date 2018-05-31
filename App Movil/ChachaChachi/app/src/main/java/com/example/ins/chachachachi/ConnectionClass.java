package com.example.ins.chachachachi;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Inés on 29/04/2018.
 */

public class ConnectionClass {

    /*@SuppressLint("NewApi")
    public java.sql.Connection conn(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection conn = null;
        //String connURL = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://10.60.10.27/chachachachi", "root", "");
            System.out.println("Connectado.");
           // conn = DriverManager.getConnection(connURL);
        } catch (Exception e){
            Log.e("ERROR ", e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }*/


    private static Connection connection = null;
    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://10.60.10.78:3306?characterEncoding=utf-8", "root", ""
            );

            Log.d("ConnectionClass", "Conectado");
        }
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al conectar a la base de datos.");
        }

        if (connection == null) {
            return null;
        }

            try {
                Statement s1 = connection.createStatement();
                int r1 = s1.executeUpdate("USE ChachaChachi;");

                if (r1 == 0) {
                    System.out.println("Base de datos seleccionada.");
                }
                else {
                    System.out.println("Tabla no encontrada.");
                    return null;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();

            }
        return connection;
    }






}
