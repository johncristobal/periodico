package cimarronez.org.periodico;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cimarronez.org.periodico.Noticias.ComentariosActivity;
import cimarronez.org.periodico.Noticias.ComentariosAdapter;
import cimarronez.org.periodico.Noticias.MapasAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    public int images[] = {
            R.drawable.amuzgo,
            R.drawable.awakateco,
            R.drawable.ayapaneco,
            R.drawable.ayuuk,
    };
    public RecyclerView lista;
    public MapasAdapter adapter;

    public MapsFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StartActivity activity = (StartActivity) getActivity();
        activity.updateView("Mapas");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        lista = view.findViewById(R.id.listaMapas);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        lista.setLayoutManager(gridLayoutManager);
        lista.setItemAnimator(new DefaultItemAnimator());

        adapter = new MapasAdapter(getActivity(), images);/*, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                //validar si tiene imagen solamente o si tiene tmb texto

                if(notas.get(position).getDescripcion().equals("")){
                    Intent ii = new Intent(context, ShowImageActivity.class);
                    ii.putExtra("id",notas.get(position).getId());
                    startActivity(ii);
                }
                else {
                    Intent i = new Intent(context, DetallesActivity.class);
                    modelostatisco = notas.get(position);
                    startActivity(i);
                }
            }
        });*/
        lista.setAdapter(adapter);

    }
}
