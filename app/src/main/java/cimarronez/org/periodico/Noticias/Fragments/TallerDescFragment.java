package cimarronez.org.periodico.Noticias.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cimarronez.org.periodico.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallerDescFragment extends Fragment {


    public TallerDescFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taller_desc, container, false);
    }

}
