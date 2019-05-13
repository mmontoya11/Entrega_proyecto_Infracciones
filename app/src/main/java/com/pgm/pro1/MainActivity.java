package com.pgm.pro1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgm.pro1.Beans.User;
import com.pgm.pro1.Database.GestionBD;
import com.pgm.pro1.Tools.Constant;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    Button btnDescarga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        User user = loadPreferences();

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navUsername);
        TextView navDireccion = (TextView) headerView.findViewById(R.id.navDireccion);
        ImageView navImage =  (ImageView) headerView.findViewById(R.id.navImageView);
        navUsername.setText(user.getName());
        navDireccion.setText(user.getDependencia());

        switch (user.getDependencia()){
            case Constant.DIR_OBRAS_PUBLICAS:
                navImage.setImageDrawable(getResources().getDrawable(R.drawable.obreas_publicas));
                break;


                case Constant.DIR_SANITARIO:
                navImage.setImageDrawable(getResources().getDrawable(R.drawable.sanitario));
                break;


                case Constant.DIR_AMBIENTAL:
                navImage.setImageDrawable(getResources().getDrawable(R.drawable.ambiental));
                break;

            default:
                navImage.setImageDrawable(getResources().getDrawable(R.drawable.reglamentos));
                break;

        }



        Button NuevoDocuemento = findViewById(R.id.NuevoDocuemntoBoton);
        NuevoDocuemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ActivityInfraccion.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reglamentos) {
            // Handle the camera action
        } else if (id == R.id.nav_infraccion) {

        } else if (id == R.id.nav_ajustes) {

        } else if (id == R.id.nav_compartir_error){
            Intent intent = new Intent(MainActivity.this,ActivityReporteError.class);
            startActivity(intent);

        } else if (id == R.id.nav_descargas) {

        }else if (id == R.id.LogOut) {
            Intent intent= new Intent(MainActivity.this, ActivityLogin.class);
            SharedPreferences sharedPreferences =
                    getSharedPreferences(Constant.USER_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
