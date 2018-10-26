package cimarronez.org.periodico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=4000;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
                String sesion = preferences.getString("sesion","null");
                if(sesion.equals("1")) {

                    String pass = (preferences.getString("pass","null"));
                    String correo = (preferences.getString("correo","null"));

                    mAuth.signInWithEmailAndPassword(correo,pass)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);

                                        //updateUI(user);
                                    } else {
                                        //updateUI(null);
                                        Toast.makeText(MainActivity.this, "Fallo la autenticaciòn...", Toast.LENGTH_SHORT).show();

                                        updateUI(null);
                                    }

                                }
                            });
                }else

                {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInAnonymously()
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "signInAnonymously:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInAnonymously:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Fallo la autenticaciòn...", Toast.LENGTH_SHORT).show();

                                        updateUI(null);
                                    }
                                }
                            });
                }

            };
        };

        Timer timer= new Timer();
        timer.schedule(task,SPLASH_SCREEN_DELAY);
    }

    public void updateUI(FirebaseUser u)
    {
        if(u != null){
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            //Intent i = new Intent(getApplicationContext(), ShareSMActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(MainActivity.this, "Fallo la autenticacion.", Toast.LENGTH_SHORT).show();
        }
    }
}
