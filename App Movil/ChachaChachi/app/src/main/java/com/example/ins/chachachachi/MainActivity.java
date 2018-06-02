package com.example.ins.chachachachi;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText username;
    EditText password;


    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login = (Button) findViewById(R.id.btnEntrar);
        username = (EditText) findViewById(R.id.etUser);
        password = (EditText) findViewById(R.id.etPassword);





        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String z = "";
                if (user.trim().equals("") || pass.trim().equals("")) {
                    Toast.makeText(getBaseContext(),"Por favor introduzca el usuario y la contraseña",Toast.LENGTH_SHORT).show();
                } else {
                    CheckLogin checkLogin = new CheckLogin();
                    System.out.println("execute");

                    activarBluetooth();
                    checkLogin.execute(user, pass);


                }

            }
        });
    }

    private void activarBluetooth () {
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBtAdapter.isEnabled()) {
            mBtAdapter.enable();
            Toast.makeText(getBaseContext(), "Activando Bluetooth ... ", Toast.LENGTH_LONG).show();
        }
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";

        Boolean isSuccess = false;


        @Override
        protected String doInBackground(String... params) {
            System.out.println("En el execute");
                try {
                    connectionClass = new ConnectionClass();
                    if (connectionClass.conectar() == null) {
                        z = "Comprueba tu conexión a internet";
                    } else {
                        System.out.println("select");
                        String query = "SELECT usuario, contrasena FROM empleado WHERE usuario = '"
                                + params[0]+ "' AND contrasena = '"
                                + params[1]+ "';";
                        Statement stmt = connectionClass.conectar().createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            z = "Autentificando ...";
                            isSuccess = true;

                        } else {
                            z = "Usuario o contraseña inválidos";
                            isSuccess = false;
                        }
                    }
                } catch (SQLException ex) {
                 //   z = ex.getMessage();
                    ex.printStackTrace();
                }
            System.out.println("resultado"+z);
                return z;
            }

            @Override
        protected  void onPostExecute(String s){
                super.onPostExecute(s);
                if (!isSuccess){
                   // Toast.makeText(getBaseContext(), "Error de login. " + s, Toast.LENGTH_LONG).show();
                    Log.d("fallo","Error de login. " + s);
                }
                else{
                    Intent ventanaTarea = new Intent(MainActivity.this, Tareas.class);
                    startActivity(ventanaTarea);
                }

            }
            }

}
