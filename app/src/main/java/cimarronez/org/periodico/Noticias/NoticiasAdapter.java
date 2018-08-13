package cimarronez.org.periodico.Noticias;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cimarronez.org.periodico.R;

import static cimarronez.org.periodico.Noticias.NoticiasActivity.categorias;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticiasModel modelo = notas.get(position);
        holder.title.setText(modelo.getTitulo());
        holder.autor.setText(modelo.getAutor());
        //holder.categoria.setText(categorias.get(modelo.getCategoria()));//String.format("%d", modelo.getCategoria()));
        holder.categoria.setText("Categoria "+position);//String.format("%d", modelo.getCategoria()));
        holder.tiempo.setText(modelo.getFecha());
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
