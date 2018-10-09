package cimarronez.org.periodico.Noticias.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cimarronez.org.periodico.R;
import cimarronez.org.periodico.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvisosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvisosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView mRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AvisosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisosFragment newInstance(String param1, String param2) {
        AvisosFragment fragment = new AvisosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avisos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //recycler view con dos columnas...
        //adapter => imagen y texto abajo...al dar clic abrir imagen para poder verla tamaño completo
        //mRecyclerView = (RecyclerView) view.findViewById(R.id.listanoticias);

        TextView content = view.findViewById(R.id.textViewContent);
        content.setText(Html.fromHtml("<p>\n" +
                "En tierras donde las palabras se mezclan en el aire turbio, donde las ideas y los trabajos caminan en mares turbulentos, tierras de aguas originarias y \n" +
                "contemporáneas, pantanos para navegar y escarpadas zonas de conflicto, tierras de tragedias pero también de luchas dignas, montañas que se labran y remueven con paciencia.\n" +
                "</p>\n" +
                "<p>\n" +
                "En tiempos donde la información fluye y confluye a ritmos vertiginosos, donde es necesario recuperar la letra y la estrategia, la voz, el sentido, el pensamiento entre naciones\n" +
                " que no terminan de acomodarse, sembrarse, florecer.\n" +
                "</p>\n" +
                "<p>\n" +
                "En cuerpos que enfrentan crisis políticas y cotidianas luchas, en permanente voluntad, en pasos que sencillos van sembrando, cosechando, transformando nuestros mundos, \n" +
                "conjuntando esfuerzos y mirando horizontes comunes, es necesario un espacio-red de enlace y posición política de izquierda, de abajo, de movimientos y territorios, \n" +
                "de sectores y clases, de dignidad.\n" +
                "</p>\n" +
                "<p>\n" +
                "En pensamientos y movimientos necesitamos un medio de enlace y comunicación con sentido que recupere las historias, una herramienta para muchos de los colectivos, \n" +
                "organizaciones, grupos, movimientos y pueblos con procesos de rebeldía y resistencia frente a las ofensivas del sistema capitalista, así, resulta fundamental contar\n" +
                " con un espacio de información, correspondencia, opinión, enlace, registro y comunicación de propuestas, denuncias, publicaciones,… que se necesiten comunicar a otros y otras.\n" +
                "</p>\n" +
                "<p>\n" +
                "Entonces es posible abrir un canal de información y propuestas, autónomo, libre, participativo, que va desde la publicación de artículos, links, páginas, libros, revistas, \n" +
                "videos, documentales, audios, fotografía,… que se retroalimenta, se despliega y esparce, se unifica.\n" +
                "</p>\n" +
                "<p>\n" +
                "Este flujo de información, de conocimientos, denuncias y reflexiones, de ida y vuelta, de vuelta y retorno, de camino sinuoso, de embarque y galope, de viento y fogata, \n" +
                "de bits, papel, sonido,  imagen, texto, cuerpos, tierras, luchas con dignidad, sólo un espacio más de los múltiples tambores, caracoles, páginas, humos y banderas de las \n" +
                "historias, que intenta establecer redes para el acceso a la información entre pueblos, entre organizaciones, entre movimientos,…\n" +
                "</p>\n" +
                "<p>\n" +
                "Una aplicación, un canal, para el que retomamos el nombre del correo americano del sur, intentando recuperar la memoria de aquella publicación creada por José María Morelos \n" +
                "al tomar con las fuerzas insurgentes la ciudad de Oaxaca en 1813, creada con el objetivo de dar a conocer las causas, el pensamiento, la posición política y los informes de \n" +
                "las campañas que se libraban en distintos territorios por la libertad de los pueblos de las Américas durante la guerra de revolución de independencia, durante casi 30 ediciones.\n" +
                "</p>\n" +
                "<p> \n" +
                "Se cuenta con las secciones: <br>\n" +
                "•\tEditorial<br>\n" +
                "•\tEnlaces<br>\n" +
                "•\tNoticias<br>\n" +
                "•\tForo abierto “el correo del correo”<br>\n" +
                "</p>\n" +
                "<p>\n" +
                "Los temas son libres y abiertos, sujetos en su publicación a la aprobación del comité editorial.\n" +
                "</p>\n" +
                "<p>\n" +
                "Los temas van de la promoción de la salud a la energía autónoma y comunitaria, de la defensa a la creación de territorios, de los reportes de urgencia a los artículos \n" +
                "que se aporten, de publicación de saberes y conocimientos al uso de lenguas originarias, de la publicación de materiales pedagógicos al baúl de talleres temáticos, de \n" +
                "la defensa de la tierra, la educación pública y gratuita, la invitación a eventos o las denuncias y noticias, entre muchos otros…\n" +
                "</p>"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StartActivity activity = (StartActivity) getActivity();
        activity.updateView("Editorial");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
