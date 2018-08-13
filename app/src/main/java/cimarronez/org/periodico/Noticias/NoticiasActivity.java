package cimarronez.org.periodico.Noticias;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cimarronez.org.periodico.BaseActivity;
import cimarronez.org.periodico.Noticias.Fragments.Notafragment;
import cimarronez.org.periodico.R;

public class NoticiasActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RelativeLayout dynamicContent;
    public static Context context;
    public ViewPager viewPager;
    public static List<String> categorias = new ArrayList<>();

    public View wizard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_noticias);
        //setSupportActionBar(toolbar);

        dynamicContent = findViewById(R.id.dynamicContent);
        BottomNavigationView bottonNavBar= findViewById(R.id.bottom_navigation);
        wizard = getLayoutInflater().inflate(R.layout.content_noticias, null);
        Toolbar toolbar = (Toolbar) wizard.findViewById(R.id.toolbar);
        toolbar.setTitle("Noticias...");

        context = this;
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //menu();

        viewPager = (ViewPager)wizard.findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout) wizard.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        firebaseCategoriasListener sync = new firebaseCategoriasListener();
        sync.execute();

        //navigationMenu();
        //mDatabase = FirebaseDatabase.getInstance().getReference();//.getReference("https://cimarronez.firebaseio.com/noticias");
        //ref = mDatabase.child("noticias");

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/


        //recupero categorias

    }

    private void setupViewPager() {
        ViewPagerAdapterNoticias adapter = new ViewPagerAdapterNoticias(getSupportFragmentManager());
        for(int i=0;i<categorias.size();i++){

            Bundle bundle = new Bundle();
            bundle.putInt("indice", i);
            Notafragment onefragment = new Notafragment();
            onefragment.setArguments(bundle);

            adapter.addFragment(onefragment, categorias.get(i));
        }

        viewPager.setAdapter(adapter);

        dynamicContent.addView(wizard);

    }

    /*public void menu(){
        ListView opciones = findViewById(R.id.lst_menu_items);
        String listaops[] = {"Noticias"};

        ArrayAdapter<String> adap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaops);
        opciones.setAdapter(adap);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.noticias, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//=================================GEt data from firebase===========================================
    public class firebaseCategoriasListener extends AsyncTask<Void, Void, Void> {
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        ProgressDialog progress = new ProgressDialog(NoticiasActivity.this);

        public firebaseCategoriasListener(){
            //mAuth = FirebaseAuth.getInstance();
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


            progress.setTitle("Actualizando");
            progress.setMessage("Recuperando información...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            myRef.child("categorias").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                        String key = Snapshot.getKey();
                        String value = Snapshot.getValue().toString();

                        categorias.add(value);
                    }
                    setupViewPager();
                    progress.hide();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}
