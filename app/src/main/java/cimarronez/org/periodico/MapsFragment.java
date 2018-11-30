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

public class MapsFragment extends Fragment {

    public int images[] = {
            R.drawable.amuzgo,
            R.drawable.awakateco,
            R.drawable.ayapaneco,
            R.drawable.ayuuk,
            R.drawable.chatino,
            R.drawable.chichimeco_jonaz,
            R.drawable.chinanteco,
            R.drawable.chocholteco_chocho,
            R.drawable.chol,
            R.drawable.chontal_de_oaxaca,
            R.drawable.chontal_de_tabasco,
            R.drawable.chontal_insuficientemente,
            R.drawable.chuj,
            R.drawable.cora,
            R.drawable.cucapa,
            R.drawable.cuicateco,
            R.drawable.guarijio,
            R.drawable.ikood,
            R.drawable.ixcateco,
            R.drawable.ixil,
            R.drawable.jakalteko,
            R.drawable.k_iche,
            R.drawable.kagchiquel,
            R.drawable.kickapoo,
            R.drawable.kilwa,
            R.drawable.kumiai,
            R.drawable.lacandon,
            R.drawable.mam,
            R.drawable.matlatzinca,
            R.drawable.maya,
            R.drawable.mayo,
            R.drawable.mazahua,
            R.drawable.mazateco,
            R.drawable.mixteco,
            R.drawable.mixteco2,
            R.drawable.nahuatl,
            R.drawable.no_especificado,
            R.drawable.oluteco,
            R.drawable.otomi,
            R.drawable.otras_lenguas_am_rica,
            R.drawable.paipai,
            R.drawable.pame,
            R.drawable.papago,
            R.drawable.popoloca,
            R.drawable.popoluca_de_la_sierra,
            R.drawable.popoluca_insuficientemente,
            R.drawable.purepecha,
            R.drawable.qanjobal,
            R.drawable.qatok_motozintleco,
            R.drawable.rar_muri,
            R.drawable.sayulteco,
            R.drawable.seri,
            R.drawable.teenek,
            R.drawable.teko,
            R.drawable.tepehua,
            R.drawable.tepehuano_del_norte,
            R.drawable.tepehuano_del_sur,
            R.drawable.tepehuano_insuficiente,
            R.drawable.texistepeque_o,
            R.drawable.tlahuica_ocuilteco,
            R.drawable.tlapaneco,
            R.drawable.tojolabal,
            R.drawable.totonaku,
            R.drawable.triqui,
            R.drawable.tseltal,
            R.drawable.tzotzil,
            R.drawable.wir_rika,
            R.drawable.yaqui,
            R.drawable.zapoteco,
            R.drawable.zoque
        };

    public String textos[] = {
            "Amuzgo,tzáñcuc (tzjon non),54 125",
            "Aguacateco,Qyool,15",
            "Ayapaneco,-,-",
            "Mixe,Ayuukjä’äy / ayuujk,130 717",
            "Chatino,Kitse cha’tnio o cha’cña / cha’ jna’a,55 864",
            "Chichimecajonaz,Uza,2 931",
            "Chinanteco,Tsa ju jmí,191 710",
            "Chocholteco,Ru nixa ngligua, ngiba,1 341",
            "Chol,Winik o lakty’añ,227 945",
            "Chontal de Oaxaca,Slijuala xanuc,8 532",
            "Chontal de Tabasco,Yokot’anob o yokot’an,79 694",
            "Chontal,-,-",
            "Chuj(y ChujKanjobal),Chuj y Chuj Kanjobal / Koti’,2 473",
            "Cora,Nayeri,19 665",
            "Cucapa,Es’pei o kuapá,280",
            "Cuicateco,Y’an yivacu o Nduudu yu / duaku,18 891",
            "Guarijio,Macurawe o Varolio / warihó,2 567",
            "Huave,Mero Ikooc / ombeayiüts,18 490",
            "Ixcateco,Xwja,177",
            "Ixil,Ixil,15",
            "Jacalteco,Abuxubal / JakaltekoPopti’,1 201",
            "K’iche’,K’iche’,636",
            "Kaqchikel,Kaqchikel,482",
            "Kickapoo,Kikapoa,135",
            "Kiliwa,Ko’lew,24",
            "Kumiai,Ti’pai m Kamia,190",
            "Maya Lacandon,Hach winik / jacht’aan,809",
            "Mame,Mam,22113",
            "Matlatzinca,Botuná / matlalzinka,1 553",
            "Maya Yuc, Maaya t’aan, 1 461 655",
            "Mayo,yoremnokki,102709",
            "Mazahua,Jñatio / jnatrjo,270 100",
            "Mazateco,Ha shuta enima / enna, 241 183",
            "Mixteco,Ñuu savi,663 864",
            "Mixteco 2,-,-",
            "Nahuatl,Macehuale / Nahua, 3 112 398",
            "No especificado,,",
            "Oluteco,-,-",
            "Otomí,Hña hñu o hñähñü,542 831",
            "Otras lenguas America,-,-",
            "Paipai,Kwa’ala, jaspuy pai,383",
            "Pima:3,O’oob o otam / tohono o’otham,1 084",
            "Pápago,Tohono / otham,822",
            "Popoloca,Runixa ngiigua / ngiwa,22 712",
            "Popoluca de la sierra,-,-",
            "Popoluca insuficiente,-,-",
            "Purhépecha,P’urhépecha,197 072",
            "Qanjobal,-,-",
            "Motozintleco,Qatok,67",
            "Tarahumara,Rarámuri / rarómari raicha,114 426",
            "Sayulteco,-,-",
            "Seri,Konkaak o cmiique iitom,666",
            "Huasteco,Teenek,205 972",
            "Jacalteco,Abuxubal / JakaltekoPopti’,1 201",
            "Tepehua,Hamasipini / lhichiwíin,11 319",
            "Tepehuán,Ódami u o’dam,36 369",
            "Tepehuán del sur,-,-",
            "Tepehuán insuficiente,-,-",
            "Texistepequeño,-,-",
            "Tlahuica,Tlahuia / pjiekakjoo,1 549",
            "Tlapaneco,Me’phaa,114 325",
            "Tojolabal,Tojolwinin’otik / tojolab’a,54 348",
            "Totonako,Tachihuiin,346 178",
            "Trique,Tinujei o driki,20 640",
            "Tzeltal,Winik a tel o k’op, 346 392",
            "Tzotzil,Batsil Inc.’otik o Batzil k’op, 429 024",
            "Huichol,Wirraritari o wirrárika,43 535",
            "Yaqui,Hiaknooki,27 887",
            "Zapoteco,Benni’za / Been’za / Bene xon,730 465",
            "Zoque,O’de püt,7 8662"
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

        adapter = new MapasAdapter(getActivity(), images,textos);
        lista.setAdapter(adapter);
    }
}
