package cimarronez.org.periodico.Noticias.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cimarronez.org.periodico.Noticias.Adapters.tallerAdapter;
import cimarronez.org.periodico.R;
import cimarronez.org.periodico.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TalleresFragment extends Fragment {

    public boolean flagBack = false;

    public TalleresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_talleres, container, false);
        // get the listview

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TallerDataFragment data = new TallerDataFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.containtaller,data).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StartActivity activity = (StartActivity) getActivity();
        activity.updateView("Talleres");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.taller, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.taller){
            if(!flagBack) {
                flagBack = true;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containtaller, new TallerDescFragment()).commit();
                item.setIcon(R.mipmap.ic_closedata);
            }else{
                flagBack = false;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containtaller, new TallerDataFragment()).commit();
                item.setIcon(R.mipmap.ic_moredata);
            }

            /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.taller_web_detail, null);
            dialogBuilder.setView(dialogView);

            dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();*/
        }

        return super.onOptionsItemSelected(item);
    }


}
