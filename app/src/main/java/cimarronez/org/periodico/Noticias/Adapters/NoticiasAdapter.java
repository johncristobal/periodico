package cimarronez.org.periodico.Noticias.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cimarronez.org.periodico.Noticias.ComentariosActivity;
import cimarronez.org.periodico.Noticias.DetallesActivity;
import cimarronez.org.periodico.Noticias.GalleryActivity;
import cimarronez.org.periodico.Noticias.modelos.NoticiasModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;
import cimarronez.org.periodico.usuario.LoginActivity;

import static cimarronez.org.periodico.Noticias.Fragments.Notafragment.modelostatisco;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.MyViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public Context context;
    public ArrayList<NoticiasModel> notas;
    public String sesion;
    //private final RecyclerViewOnItemClickListener listener;
    public Set<String> set;

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
        //holder.categoria.setText(categorias.get(modelo.getCategoria()));//String.format("%d", modelo.getCategoria()));
        //holder.categoria.setText("Categoria "+position);//String.format("%d", modelo.getCategoria()));
        holder.tiempo.setText(modelo.getFecha());

        /*Glide.with(context)
                //.load(uri.toString())
                .load("https://firebasestorage.googleapis.com/v0/b/cimarronez.appspot.com/o/noticias%2F-LNznTU9l9Hp2OMHDFH-%2Ffoto0.jpg?alt=media&token=e35da883-3b8a-4a7c-bdd4-f6e77ea15624")
                .apply(new RequestOptions().override(100, 100).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                .into(holder.thumbnail);*/

        //Blurry.with(context).capture(holder).into(holder.thumbnail);
        /*Blurry.with(context)
                .radius(25)
                .sampling(1)
                .color(Color.argb(66, 0, 255, 255))
                .async()
                .capture(holder.thumbnail)
                .into(holder.thumbnail);*/

        if(!notas.get(position).getImagen().equals("")) {
            //ahora, son mas imagenes, entonces....
            String [] imagenes = notas.get(position).getImagen().split(",");
            if(imagenes.length > 0) {

                final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(modelo.getId() + "/"+imagenes[0]);

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
                        holder.thumbnail.setImageResource(R.mipmap.ic_launcher_round);
                    }
                });
            }
        }else{
            holder.thumbnail.setImageResource(R.mipmap.ic_launcher_round);
        }

        //events each element
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notas.get(position).getDescripcion().equals("")){
                    if (notas.get(position).getImagen().split(",").length == 1) {
                        Intent ii = new Intent(context, ShowImageActivity.class);
                        ii.putExtra("id", notas.get(position).getId());
                        ii.putExtra("imagename", notas.get(position).getImagen().split(",")[0]);
                        context.startActivity(ii);
                    }else{
                        Intent ii = new Intent(context, GalleryActivity.class);
                        ii.putExtra("id", notas.get(position).getId());
                        ii.putExtra("imagenes", notas.get(position).getImagen());
                        context.startActivity(ii);
                    }
                }
                else {
                    //en detalles validar cantidad de imagenes par amostrar una o galeria...viewpager???
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
                    if (notas.get(position).getImagen().split(",").length == 1) {
                        Intent ii = new Intent(context, ShowImageActivity.class);
                        ii.putExtra("imagename", notas.get(position).getImagen().split(",")[0]);
                        ii.putExtra("id", notas.get(position).getId());
                        context.startActivity(ii);
                    }else{
                        Intent ii = new Intent(context, GalleryActivity.class);
                        ii.putExtra("id", notas.get(position).getId());
                        ii.putExtra("imagenes", notas.get(position).getImagen());
                        context.startActivity(ii);
                    }
                }
                else {
                    Intent i = new Intent(context, DetallesActivity.class);
                    modelostatisco = notas.get(position);
                    context.startActivity(i);
                }
            }
        });

        holder.textlikes.setText(String.format("%d", notas.get(position).getLikes()));
        sesion = context.getSharedPreferences("cimarronez",Context.MODE_PRIVATE).getString("sesion","null");

        if(sesion.equals("1")) {
            set = context.getSharedPreferences("cimarronez",Context.MODE_PRIVATE).getStringSet("idList", null);
            if (set != null) {
                for (String s : set) {
                    if (notas.get(position).getId().equals(s)) {
                        notas.get(position).setSetLike(true);
                        holder.likeImage.setImageResource(R.drawable.likeplus);
                    }
                }
            } else {
                set = new HashSet<String>();
            }
        }else{
            set = new HashSet<String>();
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //estyo es mas facil
                //Toast.makeText(context,"Like "+position,Toast.LENGTH_SHORT).show();
                sesion = context.getSharedPreferences("cimarronez",Context.MODE_PRIVATE).getString("sesion","null");
                if(sesion.equals("1")){

                    int likes = notas.get(position).getLikes();

                    if(notas.get(position).isSetLike()) {
                        notas.get(position).setSetLike(false);
                        //manita blanca
                        holder.likeImage.setImageResource(R.drawable.like);
                        likes--;
                        set.remove(notas.get(position).getId());
                    }else{
                        notas.get(position).setSetLike(true);
                        holder.likeImage.setImageResource(R.drawable.likeplus);
                        //manita negra
                        likes++;
                        set.add(notas.get(position).getId());
                    }
                    context.getSharedPreferences("cimarronez",Context.MODE_PRIVATE).edit().putStringSet("idList", set).apply();

                    //update firebase child, set lieks = likes
                    notas.get(position).setLikes(likes);
                    myRef.child("noticias").child(notas.get(position).getId()).updateChildren(notas.get(position).toMap());

                    holder.textlikes.setText(String.format("%d", notas.get(position).getLikes()));
                }else{
                    iniciarSesion();
                }
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Toast.makeText(context,"comment "+position,Toast.LENGTH_SHORT).show();
            //AGREGAMOS comentario....ahora a afinar la logica...
            //abrir visra con comentarios...
            //ir por lista de comentarios y visualizarlos
            //la vista debe tener el recycler y un lugar para agregar comenario
            sesion = context.getSharedPreferences("cimarronez",Context.MODE_PRIVATE).getString("sesion","null");
            if(sesion.equals("1")){
                Intent ii = new Intent(context,ComentariosActivity.class);
                ii.putExtra("id", notas.get(position).getId());
                context.startActivity(ii);
            }else{
                iniciarSesion();
            }

            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void iniciarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atenciòn");
        builder.setMessage("Para continuar debes iniciar sesiòn...");
        builder.setPositiveButton("Iniciar sesiòn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent act = new Intent(context, LoginActivity.class);
                context.startActivity(act);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        public TextView title, categoria,autor,tiempo,textlikes,textcomentarios;
        public ImageView thumbnail,likeImage;
        public LinearLayout like,comment;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewTitulo);
            this.categoria = (TextView) view.findViewById(R.id.textViewCategoria);
            this.autor = (TextView) view.findViewById(R.id.textViewAutor);
            this.tiempo = (TextView) view.findViewById(R.id.textViewTime);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageView);
            this.likeImage = (ImageView) view.findViewById(R.id.imageViewLike);
            this.like = (LinearLayout) view.findViewById(R.id.likelayout);
            this.comment = (LinearLayout) view.findViewById(R.id.commentlayout);
            this.textlikes =  view.findViewById(R.id.textViewLikes);
            this.textcomentarios = view.findViewById(R.id.textViewComentarios);
            //view.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }*/
    }
}
