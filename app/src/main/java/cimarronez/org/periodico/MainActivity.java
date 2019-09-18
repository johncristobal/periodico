package cimarronez.org.periodico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cimarronez.org.periodico.Noticias.Adapters.ComentariosAdapter;
import cimarronez.org.periodico.Noticias.ComentariosActivity;
import cimarronez.org.periodico.Noticias.modelos.ClientModel;
import cimarronez.org.periodico.Noticias.modelos.ComentariosModel;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=4000;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;
    public String idtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String token = FirebaseInstanceId.getInstance().getToken();
        //Log.w("Tokne",token);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                idtoken = instanceIdResult.getToken();
                Log.e("newToken", idtoken);

            }
        }); /*.addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            //getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
        });*/

        //Log.d("newToken", getActivity().getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :("));

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
                String sesion = preferences.getString("sesion","null");
                if(sesion.equals("1")) {

                    String pass = (preferences.getString("pass","null"));
                    String correo = (preferences.getString("correo","null"));

                    mAuth = FirebaseAuth.getInstance();
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
                }
                else
                {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInAnonymously()
                    //mAuth.signInWithEmailAndPassword(getResources().getString(R.string.mailDefault),getResources().getString(R.string.passDefault))
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


            firebaseListener tokne = new firebaseListener();
            tokne.execute(u);

        }else{
            Toast.makeText(MainActivity.this, "Fallo la autenticacion.", Toast.LENGTH_SHORT).show();
        }
    }

//=================================GEt data from firebase===========================================
    public class firebaseListener extends AsyncTask<FirebaseUser, Void, Void> {
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ProgressBar progress = null;

        public firebaseListener(){
            //mAuth = FirebaseAuth.getInstance();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        @Override
        protected void onPreExecute() {

            try {

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
        protected Void doInBackground(final FirebaseUser... voids) {

            Query temp = null;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            temp = myRef.child("tokens").child(voids[0].getUid());//.orderByChild("categoria").equalTo(index);

            String name = voids[0].getDisplayName();

            myRef.child("tokens").child(voids[0].getUid()).push();//.push().getKey();

            ClientModel article = new ClientModel(mAuth.getUid(), 1, name,idtoken);

            Map<String, Object> postValuesArticle = article.toMap();
            myRef.child("tokens").child(voids[0].getUid()).updateChildren(postValuesArticle);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(getApplicationContext(), StartActivity.class);
            //Intent i = new Intent(getApplicationContext(), ShareSMActivity.class);
            startActivity(i);
        }
    }


}
