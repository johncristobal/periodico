package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cimarronez.org.periodico.R;

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.MyViewHolder>{

    public Context context;
    public ArrayList<ComentariosModel> notas;
    private FirebaseAuth mAuth;

    public ComentariosAdapter(Context c, ArrayList<ComentariosModel> notas){
        context = c;
        this.notas = notas;
        //this.listener = listener;
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comentario_item, parent, false);

        return new ComentariosAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.title.setText(notas.get(position).getComentario());
        holder.fecha.setText(notas.get(position).getFecha());
        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("usuarios").child(notas.get(position).idUsuario+"/perfil.png");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //ImageView imageView = holder.thumbnail;
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context)
                        .load(uri.toString())
                        //.load(storageRef)
                        .apply(new RequestOptions().override(100, 100).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                        .into(holder.thumbnail);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        public TextView title, fecha;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewComentt);
            this.fecha = (TextView) view.findViewById(R.id.textViewFecha);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageView88);
            //view.setOnClickListener(this);
        }
    }
}
