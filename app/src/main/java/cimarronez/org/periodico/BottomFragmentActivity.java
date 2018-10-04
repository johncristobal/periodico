package cimarronez.org.periodico;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import cimarronez.org.periodico.Noticias.Fragments.AvisosFragment;
import cimarronez.org.periodico.Noticias.Fragments.BlankFragment;
import cimarronez.org.periodico.Noticias.Fragments.BuscarFragment;
import cimarronez.org.periodico.Noticias.Fragments.SettingsFragment;

public class BottomFragmentActivity extends AppCompatActivity implements
        BlankFragment.OnFragmentInteractionListener,
        AvisosFragment.OnFragmentInteractionListener,
        BuscarFragment.OnFragmentInteractionListenerBuscar,
        SettingsFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private FrameLayout container;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*switch (item.getItemId()) {
                case R.id.navigation_home:
                    BlankFragment blannk = new BlankFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,blannk).commit();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    AvisosFragment avisosFragment = new AvisosFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,avisosFragment).commit();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    BuscarFragment buscar = new BuscarFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,buscar).commit();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.action_settings:
                    SettingsFragment setting = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,setting).commit();
                    return true;
            }*/
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        /*BlankFragment blannk = new BlankFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,blannk).commit();

        //mTextMessage = (TextView) findViewById(R.id.message);
        container = findViewById(R.id.frameLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationFrame);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}