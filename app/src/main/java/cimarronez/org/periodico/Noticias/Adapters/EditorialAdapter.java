package cimarronez.org.periodico.Noticias.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import cimarronez.org.periodico.Noticias.DetallesEditorialActivity;
import cimarronez.org.periodico.Noticias.EditorialModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;
import cimarronez.org.periodico.usuario.LoginActivity;

import static cimarronez.org.periodico.Noticias.Fragments.AvisosFragment.modelostatiscoedit;

public class EditorialAdapter extends RecyclerView.Adapter<EditorialAdapter.MyViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Bitmap imageSelected;

    public Context context;
    public ArrayList<EditorialModel> notas;
    public String sesion;
    //private final RecyclerViewOnItemClickListener listener;
    public Set<String> set;

    public EditorialAdapter(Context c, ArrayList<EditorialModel> notas){
        context = c;
        this.notas = notas;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editorial_item, parent, false);

        return new EditorialAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        EditorialModel modelo = notas.get(position);
        holder.title.setText(modelo.getTitulo());
        holder.autor.setText(modelo.getAutor());
        //holder.categoria.setText(categorias.get(modelo.getCategoria()));//String.format("%d", modelo.getCategoria()));
        //holder.categoria.setText("Categoria "+position);//String.format("%d", modelo.getCategoria()));
        //holder.tiempo.setText(modelo.getFecha());

        /*Glide.with(context)
                //.load(uri.toString())
                .load("https://firebasestorage.googleapis.com/v0/b/cimarronez.appspot.com/o/noticias%2F-LNznTU9l9Hp2OMHDFH-%2Ffoto0.jpg?alt=media&token=e35da883-3b8a-4a7c-bdd4-f6e77ea15624")
                .apply(new RequestOptions().override(100, 100).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                .into(holder.thumbnail);*/

        //Blurry.with(context).capture(holder).into(holder.thumbnail);

        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("editoriales").child(modelo.getId()+"/foto0.jpg");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //ImageView imageView = holder.thumbnail;
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context)
                    .load(uri.toString())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                    //.load(storageRef)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
                            holder.thumbnail.setImageDrawable(resource);
                            //imageSelected = bitmap;
                            /*Blurry.with(context)
                                    .radius(5)
                                    .sampling(2)
                                    //color(Color.argb(66, 255, 255, 0))
                                    .async()
                                    .animate(500)
                                    .from(bitmap)
                                    .into(holder.thumbnail);*/
                        }
                    });

                /*Glide.with(context)
                        .load(uri.toString())
                        //.load(storageRef)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
//                        .apply(new RequestOptions().override(100, 100).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                        .into(holder.thumbnail);*/

                //Blurry.with(context).capture(view).into(holder.thumbnail);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });

        //events each element
        /*holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    context.startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesEditorialActivity.class);
                    modelostatiscoedit = notas.get(position);
                    context.startActivity(i);
                }
            }
        });*/

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Blurry.delete((ViewGroup)holder.thumbnail.getParent());
                Bitmap bitmap = ((BitmapDrawable)holder.thumbnail.getDrawable()).getBitmap();

                File f3=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/inpaint/");
                if(!f3.exists())
                    f3.mkdirs();
                OutputStream outStream = null;
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/inpaint/"+"seconds"+".png");
                if(file.exists())
                    file.delete();

                try {
                    outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outStream);
                    outStream.close();
                    //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    context.startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesEditorialActivity.class);
                    modelostatiscoedit = notas.get(position);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context, holder.thumbnail, ViewCompat.getTransitionName(holder.thumbnail));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        context.startActivity(i, options.toBundle());
                    }else{
                        context.startActivity(i);
                    }

                    //context.startActivity(i);
                }
            }
        });

        /*holder.textlikes.setText(String.format("%d", notas.get(position).getLikes()));
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
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //estyo es mas facil
                //Toast.makeText(context,"Like "+position,Toast.LENGTH_SHORT).show();

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
        });*/
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
            this.title = (TextView) view.findViewById(R.id.textViewTituloEdit);
            this.categoria = (TextView) view.findViewById(R.id.textViewCategoriaEdit);
            this.autor = (TextView) view.findViewById(R.id.textViewAutorEdit);
            //this.tiempo = (TextView) view.findViewById(R.id.textViewTime);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageViewEdit);
            //this.likeImage = (ImageView) view.findViewById(R.id.imageViewBlur);
            //this.like = (LinearLayout) view.findViewById(R.id.likelayout);
            //this.comment = (LinearLayout) view.findViewById(R.id.commentlayout);
           // this.textlikes =  view.findViewById(R.id.textViewLikes);
           // this.textcomentarios = view.findViewById(R.id.textViewComentarios);
            //view.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }*/
    }
}
