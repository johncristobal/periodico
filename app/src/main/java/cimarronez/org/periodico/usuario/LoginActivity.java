package cimarronez.org.periodico.usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cimarronez.org.periodico.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("");
    }
}
