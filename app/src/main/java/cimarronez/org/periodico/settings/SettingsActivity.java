package cimarronez.org.periodico.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cimarronez.org.periodico.MainActivity;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.usuario.LoginActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    public TextView misdatos;
    public TextView usuarioClose;
    TextView textViewNombre;
    TextView textViewCorreo;
    public CircleImageView imageView11;
    public static FirebaseAuth mAuth;
    public ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Configuración");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bar = findViewById(R.id.progressBar2);
        misdatos = findViewById(R.id.misdatosText);
        usuarioClose = findViewById(R.id.usuarioClose);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewCorreo = findViewById(R.id.textViewCorreo);
        imageView11 = findViewById(R.id.imageViewProfile);

        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        String sesion = preferences.getString("sesion","null");
        if(sesion.equals("1")) {
            misdatos.setVisibility(View.VISIBLE);
            usuarioClose.setVisibility(View.VISIBLE);

            textViewNombre.setText(preferences.getString("nombre","null"));
            textViewCorreo.setText(preferences.getString("correo","null"));

            if(!preferences.getString("nombrefoto", "null").equals("null")){
                String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
                //Bitmap bmp = BitmapFactory.decodeFile(filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

                imageView11.setImageBitmap(bmp);
            }
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

    public void misDatos(View v){
        Intent intent = new Intent(this, DatosActivity.class);
        // Pass data object in the bundle and populate details activity.
        //intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView11, ViewCompat.getTransitionName(imageView11));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }
    }

    public void iniciarSesion(View view) {
        Intent act = new Intent(this, LoginActivity.class);
        startActivity(act);
    }

    public void cerrarSesion(View view) {
        //borrar preferencia...
        bar.setVisibility(View.VISIBLE);
        textViewNombre.setVisibility(View.INVISIBLE);
        textViewCorreo.setVisibility(View.INVISIBLE);
        imageView11.setVisibility(View.INVISIBLE);

        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        preferences.edit().putString("sesion","null").apply();
        //preferences.edit().putStringSet("idList",null).apply();

        FirebaseAuth.getInstance().signOut();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
            .addOnCompleteListener(SettingsActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        finish();

                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInAnonymously:failure", task.getException());
                        Toast.makeText(SettingsActivity.this, "Fallo la autenticaciòn...", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        textViewNombre.setVisibility(View.VISIBLE);
                        textViewCorreo.setVisibility(View.VISIBLE);
                        imageView11.setVisibility(View.VISIBLE);

                        //updateUI(null);
                    }
                }
            });
        //quitar usuasrio de firebase...
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        String sesion = preferences.getString("sesion","null");
        if(sesion.equals("1")) {
            misdatos.setVisibility(View.VISIBLE);
            usuarioClose.setVisibility(View.VISIBLE);

            textViewNombre.setText(preferences.getString("nombre","null"));
            textViewCorreo.setText(preferences.getString("correo","null"));

            if(!preferences.getString("nombrefoto", "null").equals("null")){
                String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
                //Bitmap bmp = BitmapFactory.decodeFile(filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

                imageView11.setImageBitmap(bmp);
            }
        }
    }
}
