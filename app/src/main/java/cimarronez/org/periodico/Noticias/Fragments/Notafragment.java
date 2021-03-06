package cimarronez.org.periodico.Noticias.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import cimarronez.org.periodico.Noticias.Adapters.NoticiasAdapter;
import cimarronez.org.periodico.Noticias.modelos.NoticiasModel;
import cimarronez.org.periodico.R;

public class Notafragment extends Fragment {

    public DatabaseReference mDatabase;
    public DatabaseReference ref;
    public ArrayList<NoticiasModel> notas;
    public RecyclerView lista;
    public NoticiasAdapter adapter;

    public int index = 0;

    public static NoticiasModel modelostatisco;
    public Context context;

    public Notafragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_inicio, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle arguments = getArguments();
        if (arguments != null)
            updateView(getArguments().getInt("indice"));
        else if (index != -1)
            updateView(index);
    }

    private void updateView(int title) {

        //this.textTab.setText(title);
        firebaseListener firebase = new firebaseListener();
        firebase.execute();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        index = getArguments().getInt("indice");

        notas = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        lista = view.findViewById(R.id.listanoticias);
        lista.setLayoutManager(linearLayoutManager);
        lista.setItemAnimator(new DefaultItemAnimator());

        adapter = new NoticiasAdapter(context, notas);/*, new RecyclerViewOnItemClickListener() {
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

    @Override
    public void onDetach() {
        super.onDetach();
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

                progress = getActivity().findViewById(R.id.progressBar);
                progress.setVisibility(View.VISIBLE);
                //Drawable progressDrawable = progress.getProgressDrawable().mutate();
                //progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                //progress.setProgressDrawable(progressDrawable);
                //progress.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                progress.setIndeterminate(true);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Query temp = null;
            if(index == 0){
                 temp = myRef.child("noticias").orderByChild("estatus").equalTo(1);
            }else{
                 temp = myRef.child("noticias").orderByChild("categoria").equalTo(index);
            }

            temp.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    /*NoticiasModel post = dataSnapshot.getValue(NoticiasModel.class);
                    Log.i("Update","id_"+post.getAutor());

                    for (int i=0;i<notas.size();i++){
                        if(notas.get(i).getId().equals(post.getId())){
                            notas.remove(i);
                            notas.add(i,post);
                            break;
                        }
                    }

                    adapter.notifyDataSetChanged();*/
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    //update notas and update list
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //this is for show items...
            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    notas.clear();
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                        try {
                            NoticiasModel post = Snapshot.getValue(NoticiasModel.class);
                            Log.e("TAG", post.autor);
                            notas.add(post);

                            //lista.scrollToPosition(notas.size() - 1);
                            //adapter.notifyItemInserted(notas.size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Collections.reverse(notas);
                    progress.setVisibility(View.GONE);
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
    }
}
