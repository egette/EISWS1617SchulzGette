package de.schulzgette.thes_o_naise.KandidatenAnsichtFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.Models.BegrundungenMeinProfilModel;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.adapter.KandidatBeantworteteThesenAdapter;
import de.schulzgette.thes_o_naise.adapter.KandidatBegruendungenMeinProfilAdapter;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;


public class BegruendungenTabFragment extends Fragment {
    private static final String ARG_KID = "kid";
    private static final String ARG_MODE = "MODE";
    private String kid;
    private String MODE;
    static KandidatenModel kandidat;
    View myView;
    ListView lv;
    KandidatBegruendungenMeinProfilAdapter listadapter;
    ArrayList<BegrundungenMeinProfilModel> begrundungenMeinProfilModels = new ArrayList<>();

    public BegruendungenTabFragment() {
        // Required empty public constructor
    }

    public static BegruendungenTabFragment newInstance(String MODE, String kid) {
        BegruendungenTabFragment fragment = new BegruendungenTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, MODE);
        args.putString(ARG_KID, kid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MODE = getArguments().getString(ARG_MODE);
            kid = getArguments().getString(ARG_KID);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_begruendungen_tab, container, false);
        lv = (ListView) myView.findViewById(R.id.kandidatbegruendungenliste);
        listadapter = new KandidatBegruendungenMeinProfilAdapter(begrundungenMeinProfilModels, getContext(), MODE);
        lv.setAdapter(listadapter);
        updateKandiatBegruendungen(kid);
        return myView;
    }

    public void updateKandiatBegruendungen(String kid){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");
        Database db = new Database(getActivity(), UID);
        kandidat = db.getKandidat(kid);
        if(kandidat != null){
            JSONArray begruendungen = kandidat.getBegruendungen();
            for (int i = 0; i < begruendungen.length(); i++){
                try {
                    JSONObject object = (JSONObject) begruendungen.get(i);
                    String begruendungstext = object.getString("Text");
                    String TID = object.getString("TID");
                    String thesentext = db.getThesentextWithTID(TID);
                    Integer likes = object.getInt("likes");
                    String Richtung = object.getString("Richtung");
                    begrundungenMeinProfilModels.add(new BegrundungenMeinProfilModel(begruendungstext,TID, thesentext, likes, Richtung));
                    listadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
