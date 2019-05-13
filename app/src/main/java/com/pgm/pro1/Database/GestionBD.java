package com.pgm.pro1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;

public class GestionBD extends SQLiteOpenHelper {

        private String result,tipo;
        private int aux = 0;
    // private Connection conn = new Connection();
        private JSONArray jarray,jsonA;
        private JSONObject json_data,jObject;
        private StringBuilder sb = new StringBuilder();

    String sqlCrearTablaDireccion = "create table C_Direccion(id_c_direccion INTEGER PRIMARY KEY AUTOINCREMENT, direccion TEXT,capturo Text,fecha numeric)";
    String sqlCrearTablaInspectores = "create table C_inspector(id_c_inspector INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT not null,ife TEXT not null, vigente text, no_empleado int not null,vigencia NUMERIC,id_c_direccion int not null,contrasena text,capturo text,fecha numeric)";

    public GestionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCrearTablaDireccion);
        db.execSQL(sqlCrearTablaInspectores);

        Log.i("Gestionar", sqlCrearTablaDireccion);
        Log.i("Gestionar", sqlCrearTablaInspectores);

        ingresarDireccion(db);
        ingresarInspectores(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void ingresarDireccion(SQLiteDatabase db){
        //db = this.getWritableDatabase();
        try{
            if(db != null){

                ContentValues cv = new ContentValues();

                cv.put("id_c_direccion", 1);
                cv.put("direccion", "Administracion");
                cv.put("capturo","");
                cv.put("fecha","");

                db.insert("C_Direccion", null, cv);

            }
        }catch (Exception e) {
            Log.i("insertar", e.getMessage());
        }


    }

    public void ingresarInspectores(SQLiteDatabase db){
        if(db != null){

            ContentValues cv = new ContentValues();

            cv.put("id_c_inspector", 1);
            cv.put("nombre", "administrador");
            cv.put("ife", "");
            cv.put("no_empleado", 2);
            cv.put("vigencia", "");
            cv.put("id_c_direccion", 1);
            cv.put("contrasena", "4dm1n");
            cv.put("capturo","");
            cv.put("fecha","");
            cv.put("vigente", "S");

            db.insert("C_inspector", null, cv);

        }
    }
}
