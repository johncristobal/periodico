package cimarronez.org.periodico.Noticias.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cimarronez.org.periodico.Noticias.Adapters.MapasAdapter;
import cimarronez.org.periodico.Noticias.modelos.MapasModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.StartActivity;

public class MapsFragment extends Fragment {

    public RecyclerView lista;
    public MapasAdapter adapter;
    public ArrayList<MapasModel> notas;
    public MapsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StartActivity activity = (StartActivity) getActivity();
        activity.updateView("Mapas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        lista = view.findViewById(R.id.listaMapas);
        notas = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get categorias...
        firebaseListener sync = new firebaseListener();
        sync.execute();
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
                //show progress
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        @Override
        protected Void doInBackground(Void... voids) {

            Query temp = null;
            temp = myRef.child("mapas");//.child(idNota);//.orderByChild("categoria").equalTo(index);

            //this is for show items...
            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    notas.clear();
                    for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                        try {
                            MapasModel post = Snapshot.getValue(MapasModel.class);
                            //Log.e("TAG", post.autor);
                            notas.add(post);

                            //lista.scrollToPosition(notas.size() - 1);
                            //adapter.notifyItemInserted(notas.size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

                    lista.setLayoutManager(gridLayoutManager);
                    lista.setItemAnimator(new DefaultItemAnimator());

                    adapter = new MapasAdapter(getActivity(), notas);
                    lista.setAdapter(adapter);
                    //Collections.reverse(comentariosList);
                    //progress.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FAIL","No carga datos");
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


        }
    }
}
