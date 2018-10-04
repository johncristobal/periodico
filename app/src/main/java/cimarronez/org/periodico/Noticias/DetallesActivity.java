package cimarronez.org.periodico.Noticias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;

import static cimarronez.org.periodico.Noticias.Fragments.BlankFragment.categorias;
import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class DetallesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetalles);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView titulo = findViewById(R.id.textViewTitulo);
        TextView categoria = findViewById(R.id.textViewCategoria);
        TextView fecha = findViewById(R.id.textViewFecha);
        TextView descripcion = findViewById(R.id.textViewDescripcion);

        final ImageView foto = findViewById(R.id.imageViewFoto);

        titulo.setText(modelostatisco.getTitulo());
        categoria.setText(categorias.get(modelostatisco.getCategoria()));
        fecha.setText(modelostatisco.getFecha());
        descripcion.setText(modelostatisco.getDescripcion());

        if(modelostatisco.getImagen().equals("")){
            foto.setVisibility(View.GONE);
        }else{
            //recuperas imagen y show
            final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(modelostatisco.getId()+"/foto0.jpg");

            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Glide.with(DetallesActivity.this)
                            .load(uri.toString())
                            .apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                            //.load(storageRef)
                            .into(foto);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors

                }
            });
            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ii = new Intent(DetallesActivity.this, ShowImageActivity.class);
                    ii.putExtra("id",modelostatisco.getId());
                    startActivity(ii);
                    //Toast.makeText(DetallesActivity.this,"Abrir imagen",Toast.LENGTH_SHORT).show();
                }
            });
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

}
