package cimarronez.org.periodico.usuario;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cimarronez.org.periodico.R;

public class LoginActivity extends AppCompatActivity implements
        IniciarFragment.OnFragmentInteractionListener,
        RegistroFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setTitle("");

        IniciarFragment fragment = new IniciarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,fragment).commit();
    }

    public void registroUser(View v){

        //open fragment para registrara datos
        //nombre,correo, password,telefono
        RegistroFragment fragment = new RegistroFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        getSharedPreferences("cimarronez", Context.MODE_PRIVATE).edit().putString("sesion","1").apply();
        finish();
    }

    public void cerrar(View v){
        finish();
    }
}
