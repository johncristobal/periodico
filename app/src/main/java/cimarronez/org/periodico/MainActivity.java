package cimarronez.org.periodico;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                /*mAuth.signInWithEmailAndPassword(getString(R.string.mailDefault), getString(R.string.passDefault))
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });*/
            }
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
