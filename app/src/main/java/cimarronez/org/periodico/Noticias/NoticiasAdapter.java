package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cimarronez.org.periodico.R;

import static cimarronez.org.periodico.Noticias.Fragments.BlankFragment.categorias;
import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {


    public Context context;
    public ArrayList<NoticiasModel> notas;
    private final RecyclerViewOnItemClickListener listener;

    public NoticiasAdapter(Context c, ArrayList<NoticiasModel> notas,RecyclerViewOnItemClickListener listener){
        context = c;
        this.notas = notas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listprincipalitem, parent, false);

        return new NoticiasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        NoticiasModel modelo = notas.get(position);
        holder.title.setText(modelo.getTitulo());
        holder.autor.setText(modelo.getAutor());
        holder.categoria.setText(categorias.get(modelo.getCategoria()));//String.format("%d", modelo.getCategoria()));
        //holder.categoria.setText("Categoria "+position);//String.format("%d", modelo.getCategoria()));
        holder.tiempo.setText(modelo.getFecha());

        /*Glide.with(context)
                //.load(uri.toString())
                .load("https://firebasestorage.googleapis.com/v0/b/cimarronez.appspot.com/o/noticias%2F-LNznTU9l9Hp2OMHDFH-%2Ffoto0.jpg?alt=media&token=e35da883-3b8a-4a7c-bdd4-f6e77ea15624")
                .apply(new RequestOptions().override(100, 100).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                .into(holder.thumbnail);*/

        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(modelo.getId()+"/foto0.jpg");

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


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, categoria,autor,tiempo;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewTitulo);
            this.categoria = (TextView) view.findViewById(R.id.textViewCategoria);
            this.autor = (TextView) view.findViewById(R.id.textViewAutor);
            this.tiempo = (TextView) view.findViewById(R.id.textViewTime);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
}
