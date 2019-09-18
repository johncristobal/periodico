package cimarronez.org.periodico;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cimarronez.org.periodico.R;

/**
 * Created by Next University.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView textView, textView1;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.txt_titulo);
        textView1 = (TextView) itemView.findViewById(R.id.txt_sub);
    }
}
