package cimarronez.org.periodico;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

public class ShowImageActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        mImageView=(ImageView)findViewById(R.id.imageViewExpand);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        String id = getIntent().getStringExtra("id");

        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(id+"/foto0.jpg");
        //StorageReference forestRef = storageRef.child("images/forest.jpg");

        storageRef.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
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
        });

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(ShowImageActivity.this)
                    .load(uri.toString())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                    //.load(storageRef)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            if(resource.getIntrinsicWidth() > resource.getIntrinsicHeight()){
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            }

                            mImageView.setImageDrawable(resource);
                            Log.i("width",resource.getIntrinsicWidth()+"");
                            Log.i("heigth",resource.getIntrinsicHeight()+"");
                        }
                    });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

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
}
