package cimarronez.org.periodico;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;

public class ShowImageActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ZoomageView mImageView;

    Uri ligaimagen = null;
    File imagen;
    String id,name;
    Drawable picture;
    public ProgressBar progressBarCargaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetalles);
        //toolbar.setTitle("");
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = findViewById(R.id.imageViewExpand);
        //mImageView.getSettings().setBuiltInZoomControls(true);
        //mImageView.getSettings().setDisplayZoomControls(false);

        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("imagename");
        if (name == null){
            name = "foto0.jpg";
        }

        progressBarCargaImagen = findViewById(R.id.progressBarCargaImagen);

        firebaseListener lis = new firebaseListener();
        lis.execute();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }*/

    public void cerrarVentana(View v){
        finish();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent();

            Uri imageUri = null;
            try {
                Bitmap bitmap = ((BitmapDrawable)picture).getBitmap();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title", null);

                imageUri = Uri.parse(bitmapPath);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            shareIntent.setType("*/*");
            shareIntent.setAction(Intent.ACTION_SEND);
            //shareIntent.putExtra(Intent.EXTRA_TEXT, modelostatisco.getDescripcion());
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


//=================================GEt data from firebase===========================================
    public class firebaseListener extends AsyncTask<Void, Void, Void> {
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ProgressBar progress = null;

        public firebaseListener(){
            //mAuth = FirebaseAuth.getInstance();

        }

        @Override
        protected void onPreExecute() {

            try {

                progressBarCargaImagen.setVisibility(View.VISIBLE);

                // Use the application default credentials
                /*GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setDatabaseUrl("https://cimarronez.firebaseio.com")
                        //.setProjectId("cimarronez")
                        .build();
                FirebaseApp.initializeApp(options);

                db = FirestoreClient.getFirestore();*/
                //borro local database
                //borrarDB();

                //get num elements into articulo

                /*progress.setTitle("Actualizando");
                progress.setMessage("Recuperando información...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();*/

                //progress = getActivity().findViewById(R.id.progressBar);
                //progress.setVisibility(View.VISIBLE);
                //Drawable progressDrawable = progress.getProgressDrawable().mutate();
                //progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                //progress.setProgressDrawable(progressDrawable);
                //progress.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                //progress.setIndeterminate(true);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(id+"/"+name);
            //StorageReference forestRef = storageRef.child("images/forest.jpg");

            /*storageRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                @Override
                public void onSuccess(StorageMetadata storageMetadata) {

                    //here, storageMetadata contains all details about your file stored on FirebaseStorage
                    Log.i("tag", "name of file: "+storageMetadata.getName());
                    Log.i("tag", "size of file in bytes: "+storageMetadata.getSizeBytes());
                    Log.i("tag", "width: "+storageMetadata.getCustomMetadata("width"));
                    Log.i("tag", "content type of file: "+storageMetadata.getContentType());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                    Log.i("tag", "Exception occur while gettig metadata: "+exception.toString());
                }
            });*/

            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //mImageView.loadDataWithBaseURL("file:///android_asset/","<img src='"+uri+"' style='width:100%' />", "text/html", "utf-8", null);

                    ligaimagen = uri;
                    //getTempFile(DetallesActivity.this,uri.toString());
                    Glide.with(ShowImageActivity.this)
                            .load(uri.toString())
                            //.apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                            //.load(storageRef)
                            .into(new SimpleTarget<Drawable>() {

                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    //saveImage(resource);
                                    //foto.setImageDrawable(resource);
                                    mImageView.setImageDrawable(resource);
                                    picture = resource;
                                    progressBarCargaImagen.setVisibility(View.INVISIBLE);
                                    mImageView.setVisibility(View.VISIBLE);
                                }
                            });


                    // Got the download URL for 'users/me/profile.png'
            /*Glide.with(ShowImageActivity.this)
                    .load(uri.toString())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                    //.load(storageRef)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            //if(resource.getIntrinsicWidth() > resource.getIntrinsicHeight()){
                             //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                           // }

                            //mImageView.setImageDrawable(resource);
                            Log.i("width",resource.getIntrinsicWidth()+"");
                            Log.i("heigth",resource.getIntrinsicHeight()+"");
                        }
                    });*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    progressBarCargaImagen.setVisibility(View.INVISIBLE);
                    mImageView.setVisibility(View.VISIBLE);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


        }

    }
}
