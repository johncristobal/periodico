package cimarronez.org.periodico.Noticias.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cimarronez.org.periodico.Noticias.ShowMapDetail;
import cimarronez.org.periodico.Noticias.modelos.MapasModel;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.ShowImageActivity;

public class GaleriaAdapter extends RecyclerView.Adapter<GaleriaAdapter.MyViewHolder>{

    public Context context;
    public String[] imagenes;
    private FirebaseAuth mAuth;
    public String id;
    final SharedPreferences preferences;// = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);

    public GaleriaAdapter(Context c, String[] imagenes, String id){
        context = c;
        this.imagenes = imagenes;
        //this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
        this.id = id;
        preferences = c.getSharedPreferences("cimarronez", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galeria_item, parent, false);

        return new GaleriaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        //holder.autor.setText(notas.get(position).getComentario());
        //holder.title.setText(textos.get(position).getTitulo());
        //holder.fecha.setText(notas.get(position).getFecha());
        //holder.thumbnail.setImageResource(mapas[position]);
        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://cimarronez.appspot.com").child("noticias").child(id).child(imagenes[position]);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //ImageView imageView = holder.thumbnail;
                // Got the download URL for 'users/me/profile.png'
                /*Glide.with(context)
                        .load(uri.toString())
                        //.load(storageRef)
                        //.apply(new RequestOptions().override(150, 150).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                        .into(holder.thumbnail);
                */
                Glide.with(context)
                        .load(uri.toString())
                        .apply(new RequestOptions().centerInside().diskCacheStrategy(DiskCacheStrategy.ALL))//.override(150,200)
                        //.load(storageRef)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());

                                holder.thumbnail.setLayoutParams(params);
                                holder.thumbnail.setImageDrawable(resource);
                                //imageSelected = bitmap;
                            }
                        });
                //holder.thumbnail.setMinimumHeight(150);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("Failo","iamge not laoded"+exception.toString());
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(context, ShowImageActivity.class);
                ii.putExtra("imagename", imagenes[position]);
                ii.putExtra("id", id);
                context.startActivity(ii);
                //Intent i = new Intent(context,ShowMapDetail.class);
                //i.putExtra("imagen",mapas[position]);
                /*i.putExtra("titulo",textos.get(position).getTitulo());
                i.putExtra("desc",textos.get(position).getDescripcion());
                i.putExtra("pob",textos.get(position).getPoblacion());
                i.putExtra("img",textos.get(position).getImagen());*/
                //context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagenes.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        //public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            //this.title = (TextView) view.findViewById(R.id.textViewMapa);
            //this.autor = (TextView) view.findViewById(R.id.textViewAutor);
            //this.fecha = (TextView) view.findViewById(R.id.textViewFecha);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageViewGaleria);
            //view.setOnClickListener(this);
        }
    }
}
