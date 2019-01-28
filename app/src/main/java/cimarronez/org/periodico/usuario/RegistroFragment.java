package cimarronez.org.periodico.usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Map;

import cimarronez.org.periodico.Noticias.modelos.ClientModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public String idtoken = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth mAuth;
    public ProgressBar bar;
    public Button registro;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        final View viewFinal = view;
        registro  = view.findViewById(R.id.buttonRegistro);
        bar = view.findViewById(R.id.progressBar33);
        ImageView info1 = view.findViewById(R.id.imageView15);
        ImageView info2 = view.findViewById(R.id.imageView16);

        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
        info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();

            }
        });


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nombre = viewFinal.findViewById(R.id.editNombre);
                EditText correo = viewFinal.findViewById(R.id.editCorreo);
                EditText pass1 = viewFinal.findViewById(R.id.editPass);
                EditText pass2 = viewFinal.findViewById(R.id.editPassAgain);

                if(!pass1.getText().toString().equals(pass2.getText().toString())){
                    Snackbar.make(v,"Las contraseñas no coinciden...",Snackbar.LENGTH_SHORT).show();
                }else if(nombre.getText().toString().equals("")) {
                    Snackbar.make(v,"Coloca un nombre de usuario...",Snackbar.LENGTH_SHORT).show();

                }else if(correo.getText().toString().equals("")) {
                    Snackbar.make(v,"Coloca un correo electrónico válido...",Snackbar.LENGTH_SHORT).show();
                }else{
                    //1. lregistras a autnetication y haces loguin
                    //si el registro queda, entonces si lanzas a firebase los datos

                    firebaseListener listener = new firebaseListener();
                    listener.execute(correo.getText().toString(),pass1.getText().toString(),nombre.getText().toString());
                    //registroFirebase();
                }
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Atención");
        builder.setMessage("La contraseña debe contener mínimo 8 caracteres incluyendo al menos un dígito.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();

    }

    //=================================GEt data from firebase===========================================
    public class firebaseListener extends AsyncTask<String, Void, Void> {
        String ErrorCode = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ProgressBar progress = null;

        public firebaseListener(){
            //mAuth = FirebaseAuth.getInstance();

        }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        bar.setVisibility(View.GONE);
        registro.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "Fallo el registro. Intenta mas tarde...", Toast.LENGTH_SHORT).show();

    }

    @Override
        protected void onPreExecute() {

            try {
                bar.setVisibility(View.VISIBLE);
                registro.setVisibility(View.GONE);

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
        protected Void doInBackground(final String... voids) {

            AuthCredential credential = EmailAuthProvider.getCredential(voids[0], voids[1]);
            mAuth.getCurrentUser().linkWithCredential(credential)
                    //mAuth.createUserWithEmailAndPassword(correo, pass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                cancel(true);
                            }else{
                                //sucess on login, then update data correo and telefono,
                                //also, create user firabase...
                                SharedPreferences preferences = getActivity().getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
                                preferences.edit().putString("nombre",voids[2]).apply();
                                preferences.edit().putString("sesion","1").apply();
                                preferences.edit().putString("correo",voids[0]).apply();
                                preferences.edit().putString("pass",voids[1]).apply();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(voids[2])
                                        //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                        .build();

                                final FirebaseUser user = mAuth.getCurrentUser();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //bar.setVisibility(View.GONE);
                                                    //registro.setVisibility(View.VISIBLE);

                                                    if (mListener != null) {
                                                        mListener.onFragmentInteraction(null);
                                                    }
                                                    //Log.d(TAG, "User profile updated.");
                                                }else{
                                                    cancel(true);
                                                }
                                            }
                                        });
                                //guardar datos de usuario en firebaswe???
                                //2. lasnzas a firebase los datos del usuario
                            }
                        }
                    });

            //}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Intent i = new Intent(getApplicationContext(), StartActivity.class);
            //Intent i = new Intent(getApplicationContext(), ShareSMActivity.class);
            //startActivity(i);
        }
    }

    public void registroFirebase(final String correo, final String pass, final String nombre){

        AuthCredential credential = EmailAuthProvider.getCredential(correo, pass);
        mAuth.getCurrentUser().linkWithCredential(credential)
        //mAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            bar.setVisibility(View.GONE);
                            registro.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Fallo el registro. Intenta mas tarde...", Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                @Override
                                public void onSuccess(InstanceIdResult instanceIdResult) {
                                    idtoken = instanceIdResult.getToken();
                                    Log.e("newToken", idtoken);
                                }
                            });

                            //sucess on login, then update data correo and telefono,
                            //also, create user firabase...
                            SharedPreferences preferences = getActivity().getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
                            preferences.edit().putString("nombre",nombre).apply();
                            preferences.edit().putString("sesion","1").apply();
                            preferences.edit().putString("correo",correo).apply();
                            preferences.edit().putString("pass",pass).apply();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nombre)
                                //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                .build();

                            final FirebaseUser user = mAuth.getCurrentUser();
                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //bar.setVisibility(View.GONE);
                                            //registro.setVisibility(View.VISIBLE);


                                            //}

                                            if (mListener != null) {
                                                mListener.onFragmentInteraction(null);
                                            }
                                            //Log.d(TAG, "User profile updated.");
                                        }else{
                                            bar.setVisibility(View.GONE);
                                            registro.setVisibility(View.VISIBLE);
                                            Toast.makeText(getActivity(), "Fallo el registro. Intenta mas tarde...", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            //guardar datos de usuario en firebaswe???
                            //2. lasnzas a firebase los datos del usuario
                        }
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
