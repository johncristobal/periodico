package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class MapasAdapter extends RecyclerView.Adapter<MapasAdapter.MyViewHolder>{

    public Context context;
    public int[] mapas;
    private FirebaseAuth mAuth;
    final SharedPreferences preferences;// = getSharedPreferences("cimarronez", Context.MODE_PRIVATE);

    public MapasAdapter(Context c, int[] notas){
        context = c;
        this.mapas = notas;
        //this.listener = listener;
        mAuth = FirebaseAuth.getInstance();
        preferences = c.getSharedPreferences("cimarronez", Context.MODE_PRIVATE);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mapa_item, parent, false);

        return new MapasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        //holder.autor.setText(notas.get(position).getComentario());
        holder.title.setText("Mapa: "+position);
        //holder.fecha.setText(notas.get(position).getFecha());
        holder.thumbnail.setImageResource(mapas[position]);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,ShowMapDetail.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapas.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textViewMapa);
            //this.autor = (TextView) view.findViewById(R.id.textViewAutor);
            //this.fecha = (TextView) view.findViewById(R.id.textViewFecha);
            this.thumbnail = (ImageView) view.findViewById(R.id.imageViewMapa);
            //view.setOnClickListener(this);
        }
    }
}
