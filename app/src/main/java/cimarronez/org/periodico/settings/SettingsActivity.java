package cimarronez.org.periodico.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.usuario.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Configuración");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        String sesion = preferences.getString("sesion","null");
        if(sesion.equals("1")) {
            TextView misdatos = findViewById(R.id.misdatosText);
            misdatos.setVisibility(View.VISIBLE);
            TextView usuarioClose = findViewById(R.id.usuarioClose);
            usuarioClose.setVisibility(View.VISIBLE);

            TextView textViewNombre = findViewById(R.id.textViewNombre);
            TextView textViewCorreo = findViewById(R.id.textViewCorreo);

            textViewNombre.setText(preferences.getString("nombre","null"));
            textViewCorreo.setText(preferences.getString("correo","null"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void iniciarSesion(View view) {
        Intent act = new Intent(this, LoginActivity.class);
        startActivity(act);
    }

    public void cerrarSesion(View view) {
        //borrar preferencia...
        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        preferences.edit().putString("sesion","null").apply();
        finish();


        //quitar usuasrio de firebase...
    }
}
