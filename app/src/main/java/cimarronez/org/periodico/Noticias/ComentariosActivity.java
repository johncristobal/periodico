package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import cimarronez.org.periodico.Noticias.Adapters.ComentariosAdapter;
import cimarronez.org.periodico.Noticias.modelos.ComentariosModel;
import cimarronez.org.periodico.R;

public class ComentariosActivity extends AppCompatActivity {

    public String idNota;
    public ArrayList<ComentariosModel> comentariosList;

    public RecyclerView lista;
    public ComentariosAdapter adapter;
    public ImageView imageView8;

    public EditText comentario;
    private FirebaseAuth mAuth;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Comentarios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        imageView8 = findViewById(R.id.imageView8);
        idNota = getIntent().getStringExtra("id");
        comentariosList = new ArrayList<>();
        lista = findViewById(R.id.listaComentarios);

        comentario = (EditText) findViewById(R.id.editTextAdd);
        comentario.requestFocus();
        mAuth = FirebaseAuth.getInstance();

        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(comentario, InputMethodManager.SHOW_IMPLICIT);
        preferences = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
        if(preferences.getString("sesion", "null").equals("1")) {

            //textViewHeader.setText(String.format("Hola de nuevo %s", preferences.getString("nombre", "null")));

            if (!preferences.getString("nombrefoto", "null").equals("null")) {
                String filePath = preferences.getString("nombrefoto", "null");//photoFile.getPath();
                //Bitmap bmp = BitmapFactory.decodeFile(filePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inSampleSize = 4;
                Bitmap bmp = BitmapFactory.decodeFile(filePath);

                imageView8.setImageBitmap(bmp);
            }else{
                imageView8.setImageResource(R.drawable.backicon);
            }
        }else{
            imageView8.setImageResource(R.drawable.backicon);
        }

        firebaseListener firebase = new firebaseListener();
        firebase.execute();
    }

    public void sendComentario(View view) {

        if(!comentario.getText().toString().equals("")) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            String keyArticle = myRef.child("comentarios").child(idNota).push().getKey();

            Date myDate = new Date();
            SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fecha = dmyFormat.format(myDate);

            ComentariosModel article = new ComentariosModel(keyArticle, 1, mAuth.getUid(), preferences.getString("nombre","null"), comentario.getText().toString(), fecha);

            Map<String, Object> postValuesArticle = article.toMap();
            myRef.child("comentarios").child(idNota).child(keyArticle).updateChildren(postValuesArticle);

            //mandamos comentasrio, ahora actualizamos lista...
            comentariosList.add(0,article);

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
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

            Query temp = null;
            temp = myRef.child("comentarios").child(idNota);//.orderByChild("categoria").equalTo(index);

            //this is for show items...
            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    comentariosList.clear();
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                        try {
                            ComentariosModel post = Snapshot.getValue(ComentariosModel.class);
                            //Log.e("TAG", post.autor);
                            comentariosList.add(post);

                            //lista.scrollToPosition(notas.size() - 1);
                            //adapter.notifyItemInserted(notas.size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Collections.reverse(comentariosList);
                    //progress.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ComentariosActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        lista.setLayoutManager(linearLayoutManager);
        lista.setItemAnimator(new DefaultItemAnimator());

        adapter = new ComentariosAdapter(ComentariosActivity.this, comentariosList);/*, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                //validar si tiene imagen solamente o si tiene tmb texto

                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesActivity.class);
                    modelostatisco = notas.get(position);
                    startActivity(i);
                }
            }
        });*/
        lista.setAdapter(adapter);
    }
}
}
