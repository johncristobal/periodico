package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;

import static cimarronez.org.periodico.Noticias.Fragments.BlankFragment.categorias;
import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class DetallesActivity extends AppCompatActivity {

    Uri ligaimagen = null;
    File imagen;
    Drawable picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetalles);
        toolbar.setTitle("");
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

        TextView titulo = findViewById(R.id.textViewTitulo);
        TextView categoria = findViewById(R.id.textViewCategoria);
        TextView fecha = findViewById(R.id.textViewFecha);
        TextView descripcion = findViewById(R.id.textViewDescripcion);

        final ImageView foto = findViewById(R.id.imageViewFoto);

        titulo.setText(modelostatisco.getTitulo());
        //categoria.setText(categorias.get(modelostatisco.getCategoria()));
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

                    ligaimagen = uri;
                    //getTempFile(DetallesActivity.this,uri.toString());
                    Glide.with(DetallesActivity.this)
                            .load(uri.toString())
                            .apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                            //.load(storageRef)
                            .into(new SimpleTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    //saveImage(resource);
                                    foto.setImageDrawable(resource);
                                    //picture = resource;
                                    //picture = resource;
                                }
                            });
                    Glide.with(DetallesActivity.this)
                            .load(uri.toString())
                            //.apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                            //.load(storageRef)
                            .into(new SimpleTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    //saveImage(resource);
                                    //foto.setImageDrawable(resource);
                                    picture = resource;
                                }
                            });
                            //.into(foto);
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

    /*private File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
            imagen = file;
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent();

            if (!modelostatisco.getImagen().equals(""))
            {
                Uri imageUri = null;
                try {
                    Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
                    String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title", null);

                    imageUri = Uri.parse(bitmapPath);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //Uri imageUri = Uri.parse("android.resource://" + getPackageName()+ "/drawable/" + "ic_launcher");
            }

            shareIntent.setType("*/*");
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, modelostatisco.getDescripcion());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "send"));

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }



}
