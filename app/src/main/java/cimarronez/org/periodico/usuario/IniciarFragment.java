package cimarronez.org.periodico.usuario;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import cimarronez.org.periodico.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IniciarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IniciarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IniciarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Context context;
    private FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IniciarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IniciarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IniciarFragment newInstance(String param1, String param2) {
        IniciarFragment fragment = new IniciarFragment();
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
        return inflater.inflate(R.layout.fragment_iniciar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        final ProgressBar bar = view.findViewById(R.id.progressBar3);
        final EditText correo = view.findViewById(R.id.editCorreo);
        final EditText pass = view.findViewById(R.id.editPass);
        final Button iniciar = view.findViewById(R.id.buttonStart);
        final TextView passrecover = view.findViewById(R.id.textView2);

        passrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(correo.getText().toString().equals("")){
                    correo.requestFocus();
                    Toast.makeText(getActivity(),"Introduce un correo válido",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(correo.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),"Correo enviado. Revisa tu bandeja.",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"Error al enviar correo. Intente mas tarde.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(correo.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Introduce un correo válido",Toast.LENGTH_SHORT).show();
                return;
            }
            if(pass.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Introduce password válido",Toast.LENGTH_SHORT).show();
                return;
            }

            bar.setVisibility(View.VISIBLE);
            iniciar.setVisibility(View.GONE);

            //now, check if you can login
            mAuth.signInWithEmailAndPassword(correo.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            bar.setVisibility(View.GONE);
                            iniciar.setVisibility(View.VISIBLE);

                            //recuperamps imagen y la guardams en local
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://cimarronez.appspot.com");
                            StorageReference islandRef = storageRef.child("usuarios/"+mAuth.getUid()+"/perfil.png");

                            //save name jajaja
                            //SharedPreferences preferences = getSharedPreferences(getString(R.string.sharedName), Context.MODE_PRIVATE);

                            //File localFile = File.createTempFile("images", "jpg");


                            islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //ImageView imageView = holder.thumbnail;
                                    Glide.with(getActivity())
                                        .load(uri.toString())
                                        .into(new SimpleTarget<Drawable>() {

                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                saveImage(resource);
                                            }
                                        });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    //no hay imagen...
                                    if (mListener != null) {
                                        mListener.onFragmentInteraction(null);
                                    }
                                }
                            });

                            /*islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    // Local temp file has been created
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("nombrefoto",localFile.getAbsolutePath());
                                    editor.apply();

                                    //BitmapFactory.Options options = new BitmapFactory.Options();
                                    //options.inSampleSize = 4;
                                    //Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath(), options);

                                    //imagen.setImageBitmap(bmp);
                                    if (mListener != null) {
                                        mListener.onFragmentInteraction(null);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                    if (mListener != null) {
                                        mListener.onFragmentInteraction(null);
                                    }
                                }
                            });*/

                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences preferences = getActivity().getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
                            preferences.edit().putString("nombre",user.getDisplayName()).apply();
                            preferences.edit().putString("pass",pass.getText().toString()).apply();
                            //Log.e("iamgen",user.getPhotoUrl().getPath());
                            preferences.edit().putString("correo",correo.getText().toString()).apply();


                            //updateUI(user);
                        } else {
                            bar.setVisibility(View.GONE);
                            iniciar.setVisibility(View.VISIBLE);
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Favor de revisar sus datos.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
            }
        });
    }

    private void saveImage(Drawable resource) {
        try{
            final SharedPreferences preferences = getActivity().getSharedPreferences("cimarronez", Context.MODE_PRIVATE);

            File path = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator);
            if(!path.exists()){
                path.mkdirs();
            }

            final File localFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator+"perfil.png");
            Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
            OutputStream os = new FileOutputStream(localFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nombrefoto",localFile.getAbsolutePath());
            editor.apply();

            if (mListener != null) {
                mListener.onFragmentInteraction(null);
            }
        } catch (Exception e){
            Toast.makeText(getActivity(),"Error en imagen",Toast.LENGTH_SHORT).show();
        }
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
