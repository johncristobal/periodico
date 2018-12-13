package cimarronez.org.periodico.Noticias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;
import jp.wasabeef.blurry.Blurry;

import static cimarronez.org.periodico.Noticias.Fragments.AvisosFragment.modelostatiscoedit;

public class DetallesEditorialActivity extends AppCompatActivity {

    Uri ligaimagen = null;
    File imagen;
    Drawable picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_editorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView titulo = findViewById(R.id.textView5Title);
        TextView subtitulo = findViewById(R.id.textView6Subtitle);
        //TextView fecha = findViewById(R.id.textViewFecha);
        TextView descripcion = findViewById(R.id.textViewContentDetail);

        final ImageView foto = findViewById(R.id.imageViewEditDetail);

        titulo.setText(modelostatiscoedit.getTitulo());
        subtitulo.setText((modelostatiscoedit.getCategoria()));
        //fecha.setText(modelostatisco.getFecha());
        descripcion.setText(modelostatiscoedit.getDescripcion());

        if(modelostatiscoedit.getImagen().equals("")){
            foto.setVisibility(View.GONE);
        }else{
            //recuperas imagen y show
            String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/inpaint/"+"seconds"+".png";//photoFile.getPath();
            //Bitmap bmp = BitmapFactory.decodeFile(filePath);
            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 4;
            Bitmap bmp = BitmapFactory.decodeFile(filePath);

            //flagFoto = true;
            foto.setImageBitmap(bmp);

            /*final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("editoriales").child(modelostatiscoedit.getId()+"/foto0.jpg");

            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    ligaimagen = uri;
                    //getTempFile(DetallesActivity.this,uri.toString());
                    Glide.with(DetallesEditorialActivity.this)
                            .load(uri.toString())
                            .apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                            //.load(storageRef)
                            .into(new SimpleTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    //saveImage(resource);
                                    scheduleStartPostponedTransition(foto);
                                    foto.setImageDrawable(resource);
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

            supportPostponeEnterTransition();
            foto.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            foto.getViewTreeObserver().removeOnPreDrawListener(this);
                            supportStartPostponedEnterTransition();
                            return true;
                        }
                    }
            );*/

            /*foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ii = new Intent(DetallesEditorialActivity.this, ShowImageActivity.class);
                    ii.putExtra("id",modelostatiscoedit.getId());
                    startActivity(ii);
                    //Toast.makeText(DetallesActivity.this,"Abrir imagen",Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    /*private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            supportFinishAfterTransition();
            //onBackPressed();
            //finish();
        }

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent();

            if (!modelostatiscoedit.getImagen().equals(""))
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, modelostatiscoedit.getDescripcion());
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "send"));

        }

        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }*/

}
