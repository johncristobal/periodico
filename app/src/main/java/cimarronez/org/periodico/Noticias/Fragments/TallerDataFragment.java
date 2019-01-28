package cimarronez.org.periodico.Noticias.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cimarronez.org.periodico.Noticias.Adapters.tallerAdapter;
import cimarronez.org.periodico.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallerDataFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public TallerDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_taller_data, container, false);
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // preparing list data
        prepareListData();

        listAdapter = new tallerAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        expListView.expandGroup(2);
        expListView.expandGroup(3);
        expListView.expandGroup(4);
        expListView.expandGroup(5);
        expListView.expandGroup(6);
        expListView.expandGroup(7);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Promoción de la salud");
        listDataHeader.add("Pedagogía para la organización autónoma");
        listDataHeader.add("Corporalidad");
        listDataHeader.add("Educación para la salud");
        listDataHeader.add("Nutrición y salud");
        listDataHeader.add("Manejo saludable");
        listDataHeader.add("Habilidades para la Vida");
        listDataHeader.add("Medicina alternativa");

        // Adding child data
        List<String> salud = new ArrayList<String>();
        salud.add("¿Qué es salud?");
        salud.add("Derecho a la salud");
        salud.add("Construcción cotidiana de la salud");
        salud.add("Construcción de espacios saludables y calidad de vida");
        salud.add("Promoción de la salud");
        salud.add("Pedagogía de la Promoción de la Salud");
        salud.add("Conversatorio salud y autonomía");
        salud.add("Género y otredades");

        List<String> pedagogia = new ArrayList<String>();
        pedagogia.add("Filosofía del cuidado");
        pedagogia.add("Reapropiación del cuerpo-territorio");
        pedagogia.add("Salud Colectiva");
        pedagogia.add("Salud comunitaria");
        pedagogia.add("Identidad");

        List<String> corporalidad = new ArrayList<String>();
        corporalidad.add("Reconocimiento de los sentimientos y emociones que habitan en nuestro cuerpo");
        corporalidad.add("Reapropiación del cuerpo");
        corporalidad.add("Memoria corporal y salud");
        corporalidad.add("Identidad, cuerpo y salud");
        corporalidad.add("Espacio, comunidad y salud");

        List<String> edcacuin = new ArrayList<String>();
        edcacuin.add("Formación y desarrollo en la infancia");
        edcacuin.add("Cuidado personal y de los cercanos");
        edcacuin.add("Relaciones de convivencia");
        edcacuin.add("Espacios saludables");
        edcacuin.add("Promoción de la Salud en el ámbito escolar");

        List<String> nutricion = new ArrayList<String>();
        nutricion.add("Alimentación");
        nutricion.add("Nutrición en el ciclo de vida:" +
                "\n - Embarazo, lactancia, primer año de vida" +
                "\n - Preescolar" +
                "\n - Adolescencia" +
                "\n - Adulto" +
                "\n - Anciano" +
                "");
        nutricion.add("Antropometría nutricional"+
                "\n - Evaluación del estado nutricional" +
                "\n - Evaluación del Estado Nutricional en una Comunidad" +
                "");
        nutricion.add("Lectura de etiquetas en alimentos");

        List<String> manejo = new ArrayList<String>();
        manejo.add("Diabetes");
        manejo.add("Sobrepeso y Obesidad");
        manejo.add("Síndrome Metabólico");
        manejo.add("Desnutrición");

        List<String> vida = new ArrayList<String>();
        vida.add("Autoconocimiento");
        vida.add("Manejo de problemas y conflictos");
        vida.add("Pensamiento creativo");
        vida.add("Pensamiento crítico");
        vida.add("Manejo de emociones y sentimientos");
        vida.add("Empatía");
        vida.add("Comunicación asertiva");
        vida.add("Relaciones interpersonales");
        vida.add("Toma de decisiones");
        vida.add("Manejo de tensiones y estrés");

        List<String> medicina = new ArrayList<String>();
        medicina.add("Elaboración de tinturas y micro dosis");
        medicina.add("Conocimiento y uso de plantes para la salud sexual y reproductiva de la mujer");
        medicina.add("Principios de la medicina oriental y adendas a la autonomía de la salud");

        listDataChild.put(listDataHeader.get(0), salud);
        listDataChild.put(listDataHeader.get(1), pedagogia);
        listDataChild.put(listDataHeader.get(2), corporalidad);
        listDataChild.put(listDataHeader.get(3), edcacuin);
        listDataChild.put(listDataHeader.get(4), nutricion);
        listDataChild.put(listDataHeader.get(5), manejo);
        listDataChild.put(listDataHeader.get(6), vida);
        listDataChild.put(listDataHeader.get(7), medicina);
    }
}
