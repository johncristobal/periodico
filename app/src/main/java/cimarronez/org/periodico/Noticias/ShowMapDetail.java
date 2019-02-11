package cimarronez.org.periodico.Noticias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import cimarronez.org.periodico.R;


public class ShowMapDetail extends AppCompatActivity {

    public boolean flag;
    public WebView imageView13;
    TextView textPueblo,textLengua,textPoblacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map_detail);

        imageView13 = findViewById(R.id.imageView13);
        textPueblo = findViewById(R.id.textPueblo);
        textLengua = findViewById(R.id.textLengua);
        textPoblacion = findViewById(R.id.textPoblacion);

        Intent extras = getIntent();
        String imagen = extras.getStringExtra("img");
        String title = extras.getStringExtra("titulo");
        String desc = extras.getStringExtra("desc");
        String pob = extras.getStringExtra("pob");

        textPueblo.setText(title);
        textLengua.setText(desc);
        textPoblacion.setText(pob);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NestedScrollView llBottomSheet = findViewById(R.id.design_bottom_sheet);

        // init the bottom sheet behavior
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        // change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        flag = false;

        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("mapas").child(imagen);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageView13.loadDataWithBaseURL("file:///android_asset/","<img src='"+uri+"' style='width:100%' />", "text/html", "utf-8", null);
                imageView13.getSettings().setBuiltInZoomControls(true);
                imageView13.setInitialScale(120);
                imageView13.getSettings().setDisplayZoomControls(false);

                //ligaimagen = uri;
                //getTempFile(DetallesActivity.this,uri.toString());
                /*Glide.with(ShowImageActivity.this)
                        .load(uri.toString())
                        //.apply(new RequestOptions().override(240, 300).centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                        //.load(storageRef)
                        .into(new SimpleTarget<Drawable>() {

                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                //saveImage(resource);
                                //foto.setImageDrawable(resource);
                                picture = resource;
                                progressBarCargaImagen.setVisibility(View.INVISIBLE);
                                mImageView.setVisibility(View.VISIBLE);
                            }
                        });*/


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
                //progressBarCargaImagen.setVisibility(View.INVISIBLE);
                imageView13.setVisibility(View.VISIBLE);
            }
        });
        //imageView13.loadDataWithBaseURL("file:///android_res/drawable/", "<img src='"+name+ "' />", "text/html", "utf-8", null);

        // set the peek height
        //bottomSheetBehavior.setPeekHeight(340);

        // set hideable or not
        bottomSheetBehavior.setHideable(false);

        // set callback for changes
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        final TextView titles = findViewById(R.id.title_sheet);
        titles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    titles.setText("Ver detalles");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                else {
                    titles.setText("Ocultar");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                flag = !flag;
            }
        });
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
