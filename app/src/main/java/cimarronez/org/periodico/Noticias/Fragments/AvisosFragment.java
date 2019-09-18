package cimarronez.org.periodico.Noticias.Fragments;

import android.content.Context;
import android.net.Uri;
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

import cimarronez.org.periodico.Noticias.Adapters.EditorialAdapter;
import cimarronez.org.periodico.Noticias.EditorialModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvisosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvisosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AvisosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mRecyclerView;
    public ArrayList<EditorialModel> notas;
    public RecyclerView lista;
    public EditorialAdapter adapter;

    public int index = 0;

    public static EditorialModel modelostatiscoedit;
    public Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AvisosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisosFragment newInstance(String param1, String param2) {
        AvisosFragment fragment = new AvisosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avisos, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StartActivity activity = (StartActivity) getActivity();
        activity.updateView("Editorial");
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        index = 0;//getArguments().getInt("indice");

        notas = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);

        lista = view.findViewById(R.id.listaeditoriales);
        lista.setLayoutManager(linearLayoutManager);
        lista.setItemAnimator(new DefaultItemAnimator());

        adapter = new EditorialAdapter(context, notas);/*, new RecyclerViewOnItemClickListener() {
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

                progress = getActivity().findViewById(R.id.progressBarEdit);
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
                temp = myRef.child("editoriales").orderByChild("estatus").equalTo(1);;
            }else{
                temp = myRef.child("editoriales");//.orderByChild("categoria").equalTo(index);
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
                            EditorialModel post = Snapshot.getValue(EditorialModel.class);
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

            return null;
        }
    }

}
