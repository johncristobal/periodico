package cimarronez.org.periodico.Noticias;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cimarronez.org.periodico.Noticias.Adapters.GaleriaAdapter;
import cimarronez.org.periodico.Noticias.Adapters.MapasAdapter;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;

public class GalleryActivity extends AppCompatActivity {

    public ProgressBar progressBarCargaImagen;
    String id;
    public RecyclerView lista;
    public GaleriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        id = getIntent().getStringExtra("id");
        String [] imagenes = getIntent().getStringExtra("imagenes").split(",");
        progressBarCargaImagen = findViewById(R.id.progressBarCargaGaleria);

        lista = findViewById(R.id.listaimagenes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);

        lista.setLayoutManager(gridLayoutManager);
        lista.setItemAnimator(new DefaultItemAnimator());

        progressBarCargaImagen.setVisibility(View.VISIBLE);

        adapter = new GaleriaAdapter(this, imagenes, id);
        lista.setAdapter(adapter);

        progressBarCargaImagen.setVisibility(View.INVISIBLE);
        lista.setVisibility(View.VISIBLE);
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
