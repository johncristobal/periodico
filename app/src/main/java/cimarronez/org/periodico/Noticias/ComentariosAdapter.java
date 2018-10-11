package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cimarronez.org.periodico.R;

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.MyViewHolder>{

    public Context context;
    public ArrayList<ComentariosModel> notas;

    public ComentariosAdapter(Context c, ArrayList<ComentariosModel> notas){
        context = c;
        this.notas = notas;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comentario_item, parent, false);

        return new ComentariosAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(notas.get(position).getComentario());
        holder.fecha.setText(notas.get(position).getFecha());
        //holder.thumbnail
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
            this.thumbnail = (ImageView) view.findViewById(R.id.imageView8);
            //view.setOnClickListener(this);
        }
    }
}
