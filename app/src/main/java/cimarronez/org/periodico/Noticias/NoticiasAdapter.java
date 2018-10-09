package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;

import static cimarronez.org.periodico.Noticias.Fragments.BlankFragment.categorias;
import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {


    public Context context;
    public ArrayList<NoticiasModel> notas;
    //private final RecyclerViewOnItemClickListener listener;

    public NoticiasAdapter(Context c, ArrayList<NoticiasModel> notas){
        context = c;
        this.notas = notas;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listprincipalitem, parent, false);

        return new NoticiasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
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

        //events each element
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    context.startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesActivity.class);
                    modelostatisco = notas.get(position);
                    context.startActivity(i);
                }
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    context.startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesActivity.class);
                    modelostatisco = notas.get(position);
                    context.startActivity(i);
                }
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //estyo es mas facil
                Toast.makeText(context,"Like "+position,Toast.LENGTH_SHORT).show();
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"comment "+position,Toast.LENGTH_SHORT).show();
                //AGREGAMOS comentario....ahora a afinar la logica...
                String idNota = notas.get(position).getId();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                String keyArticle = myRef.child("comentarios").child(idNota).push().getKey();
                ComentariosModel article = new ComentariosModel(keyArticle,idNota,1,"Alex","jajajaj","fecha");

                Map<String, Object> postValuesArticle = article.toMap();
                myRef.child("comentarios").child(idNota).child(keyArticle).updateChildren(postValuesArticle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        public TextView title, categoria,autor,tiempo;
        public ImageView thumbnail;
        public LinearLayout like,comment;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewTitulo);
            this.categoria = (TextView) view.findViewById(R.id.textViewCategoria);
            this.autor = (TextView) view.findViewById(R.id.textViewAutor);
            this.tiempo = (TextView) view.findViewById(R.id.textViewTime);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageView);
            this.like = (LinearLayout) view.findViewById(R.id.likelayout);
            this.comment = (LinearLayout) view.findViewById(R.id.commentlayout);
            //view.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }*/
    }
}
