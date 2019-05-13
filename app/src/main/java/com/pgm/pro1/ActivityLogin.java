package com.pgm.pro1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pgm.pro1.Beans.User;
import com.pgm.pro1.Database.GestionBD;
import com.pgm.pro1.Tools.Constant;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    private Button ingresar;
    private Spinner Sdependencia;
    private Spinner Sinspector;
    private EditText etContraseña;
    private ArrayAdapter adapterDirecciones,adapterInspectores;
    private ArrayList nombreDirecciones,nombreInspectores;
    private String username, contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ingresar= findViewById(R.id.login_ingresar_button);
        Sinspector = findViewById(R.id.spinner_login_inspector);
        Sdependencia = findViewById(R.id.spinner_login_dependencia);
        etContraseña = findViewById(R.id.login_contraseña);

        nombreDirecciones = DirectionNames();
        adapterDirecciones = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,nombreDirecciones);
        Sdependencia.setAdapter(adapterDirecciones);

        nombreInspectores = InspectorNames();
        adapterInspectores = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,nombreInspectores);
        Sinspector.setAdapter(adapterInspectores);


        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contraseña =  etContraseña.getText().toString();


                if(!contraseña.isEmpty()){

                    if(LoginValidation(Sinspector.getSelectedItem().toString(),etContraseña.getText().toString())){
                        username = Sinspector.getSelectedItem().toString();
                        String dependencia = Sdependencia.getSelectedItem().toString();
                        savePreferences(username,dependencia);
                        Intent intent = new Intent(ActivityLogin.this ,MainActivity.class);
                        startActivity(intent);

                    }else
                        Toast.makeText(getApplicationContext(), "contraseña incorrecta",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(getApplicationContext(), "Ingrese contraseña",Toast.LENGTH_LONG).show();
            }
        });
    }

    public ArrayList<String> DirectionNames(){

        GestionBD gestion = new GestionBD(getApplicationContext(),"Infraccion",null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM c_direccion", null);
        ArrayList<String> nombresDirecciones= new ArrayList<>();
        String nombre;

       while (cursor.moveToNext()){
           nombre = cursor.getString(1);
           nombresDirecciones.add(nombre);

       }

        return nombresDirecciones;
    }


    public ArrayList<String> InspectorNames(){

        GestionBD gestion = new GestionBD(getApplicationContext(),"Infraccion",null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM c_inspector", null);
        ArrayList<String> nombresInspectore= new ArrayList<>();
        String nombre;

       while (cursor.moveToNext()){
           nombre = cursor.getString(1);
           nombresInspectore.add(nombre);

       }

        return nombresInspectore;
    }

    public boolean LoginValidation (String username, String password){

        GestionBD gestionBD = new GestionBD(getApplicationContext(),"Infraccion", null, 1);
        SQLiteDatabase db = gestionBD.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM c_inspector WHERE nombre = '"+username+"'",null);
        String TruePass="";
        while (cursor.moveToNext()){
            TruePass = cursor.getString(7);
            Log.e("Password BD", TruePass +"");
        }

        //if(TruePass.equalsIgnoreCase(password))
        return TruePass.equalsIgnoreCase(password) ? true : false;



    }
    public void savePreferences(String username, String dependencia){
        User user = new User();
        user.setName(username);
        user.setDependencia(dependencia);
        user.setLogged(true);
        SharedPreferences sharedPreferences =
                getSharedPreferences(Constant.USER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", user.getName());
        editor.putString("DEPENDENCIA", user.getDependencia());
        editor.putBoolean("LOGGED", user.isLogged());
        editor.apply();
    }



}
