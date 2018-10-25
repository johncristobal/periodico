package cimarronez.org.periodico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import cimarronez.org.periodico.Noticias.Fragments.AvisosFragment;
import cimarronez.org.periodico.Noticias.Fragments.BlankFragment;
import cimarronez.org.periodico.Noticias.Fragments.BuscarFragment;
import cimarronez.org.periodico.settings.SettingsActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BlankFragment.OnFragmentInteractionListener,
        AvisosFragment.OnFragmentInteractionListener,
        BuscarFragment.OnFragmentInteractionListenerBuscar{

    DrawerLayout drawer;
    public static boolean flag = false;

    public CircleImageView imagen;
    public TextView textViewHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("");
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new BlankFragment())
                .commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            imagen = headerView.findViewById(R.id.imageViewHeader);
            textViewHeader = headerView.findViewById(R.id.textViewHeader);

        }

        SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);

        if(preferences.getString("sesion", "null").equals("1")) {

            textViewHeader.setText(String.format("Hola de nuevo %s", preferences.getString("nombre", "null")));

            if (!preferences.getString("nombrefoto", "null").equals("null")) {
                String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
                //Bitmap bmp = BitmapFactory.decodeFile(filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

                imagen.setImageBitmap(bmp);
            }
        }
    }

    public void updateView(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        } else if (id == R.id.nav_gallery) {
            fragment = new AvisosFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        } else if (id == R.id.nav_slideshow) {
            //fragment = new BuscarFragment();
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);

        }/* else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(flag) {
            BlankFragment fragment = new BlankFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }

        final SharedPreferences preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);

        if(preferences.getString("sesion", "null").equals("1")) {

            textViewHeader.setText(String.format("Hola de nuevo %s", preferences.getString("nombre", "null")));

            if (!preferences.getString("nombrefoto", "null").equals("null")) {
                String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
                //Bitmap bmp = BitmapFactory.decodeFile(filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

                imagen.setImageBitmap(bmp);
            }else{
                imagen.setImageResource(R.drawable.backicon);
            }
        }else{
            textViewHeader.setText("NCAS");
            imagen.setImageResource(R.drawable.backicon);

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //when press fragment button, comes here
    }
}
