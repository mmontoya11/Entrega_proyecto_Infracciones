package com.pgm.pro1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.pgm.pro1.Beans.User;
import com.pgm.pro1.Database.GestionBD;
import com.pgm.pro1.Tools.Connection;
import com.pgm.pro1.Tools.Constant;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    private Connection c;

    Context context;
    private String msj="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.slpash_screen);
        c = new Connection(getApplicationContext());


        //Persmiso de Internet para descargar catalogos
        if (ContextCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.INTERNET}, 9999);
        }


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                User user = loadPreferences();
                Intent mainIntent;
                if(user.isLogged()) {
                    mainIntent = new Intent().setClass(SplashScreen.this, MainActivity.class);

                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();

                    startActivity(mainIntent);
                }
                else
                    mainIntent = new Intent().setClass(SplashScreen.this, ActivityLogin.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(mainIntent);



            }
        };




        //Revisamos el estado de la conexiccion para saber que hacer
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(SplashScreen.this," No cuenta con conexion a interent ", Toast.LENGTH_LONG).show();
            timer.schedule(task,1000);
        }else{

            //Toast.makeText(SplashScreen.this," NetInfo " + netInfo, Toast.LENGTH_LONG).show();

            new ingresarBd().execute();
            timer.schedule(task,1000);
        }








    }

    public  void insertar() {
        if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
            if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("null")) {
                eliminaRegistros("C_Direccion");
                c.insetarRegistros("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getC_Direccion.php", "C_Direccion");
            }
            if (!c.search("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getc_insepctor.php").trim().equalsIgnoreCase("null")) {
                eliminaRegistros("C_inspector");
                c.insetarRegistros("http://apidiv.guadalajara.gob.mx:8085/serverSQL/getc_insepctor.php", "C_inspector");
            }



        }
    }

    public void eliminaRegistros(String tabla) {
        GestionBD gestion = new GestionBD(getApplicationContext(), "Infraccion",null,1);
        SQLiteDatabase db = gestion.getReadableDatabase();
        db.beginTransaction();
        try {

            db.delete(tabla, "1", null);

            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.e("SQLiteException ", e.getMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }



     class ingresarBd extends AsyncTask<String,String,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            insertar();

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public User loadPreferences(){
        SharedPreferences sharedPreferences =
                getSharedPreferences(Constant.USER_PREFERENCES, MODE_PRIVATE);
        User user = new User();
        user.setName(sharedPreferences.getString("USER", null));
        user.setDependencia(sharedPreferences.getString("DEPENDENCIA", null));
        user.setLogged(sharedPreferences.getBoolean("LOGGED", false));
        sharedPreferences = null;
        return user;
    }






}

