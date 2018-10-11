package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import cimarronez.org.periodico.R;

public class ComentariosActivity extends AppCompatActivity {

    public String id;
    public ArrayList<ComentariosModel> comentariosList;

    public RecyclerView lista;
    public ComentariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Comentarios");
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        id = getIntent().getStringExtra("id");
        comentariosList = new ArrayList<>();
        lista = findViewById(R.id.listaComentarios);

        EditText comentario = (EditText) findViewById(R.id.editTextAdd);
        comentario.requestFocus();

        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(comentario, InputMethodManager.SHOW_IMPLICIT);

        firebaseListener firebase = new firebaseListener();
        firebase.execute();
    }

    public void sendComentario(View view) {

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
            temp = myRef.child("comentarios").child(id);//.orderByChild("categoria").equalTo(index);

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

            /*temp.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        try {
                            NoticiasModel post = dataSnapshot.getValue(NoticiasModel.class);
                            Log.e("TAG", post.autor);
                            notas.add(post);

                            //lista.scrollToPosition(notas.size() - 1);
                            adapter.notifyItemInserted(notas.size() - 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });*/

            return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ComentariosActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
