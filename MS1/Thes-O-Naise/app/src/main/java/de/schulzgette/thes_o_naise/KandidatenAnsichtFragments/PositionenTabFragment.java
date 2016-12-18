package de.schulzgette.thes_o_naise.KandidatenAnsichtFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.adapter.KandidatBeantworteteThesenAdapter;
import de.schulzgette.thes_o_naise.database.Database;


public class PositionenTabFragment extends Fragment {

    private static final String ARG_KID = "kid";
    private static final String ARG_MODE = "MODE";
    private String kid;
    private String MODE;
    static KandidatenModel kandidat;
    View view;
    ListView lv;
    KandidatBeantworteteThesenAdapter listadapter;
    ArrayList<BeantworteteThesenKandidatenModel> beantworteteThesenKandidatenModels = new ArrayList<>();


    public PositionenTabFragment() {
        // Required empty public constructor
    }

    public static PositionenTabFragment newInstance(String MODE, String kid) {
        PositionenTabFragment fragment = new PositionenTabFragment();
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
        view = inflater.inflate(R.layout.fragment_positionen_tab, container, false);
        Log.d("MODUS333",MODE);

        lv = (ListView) view.findViewById(R.id.kandidatpositionenliste);
        listadapter = new KandidatBeantworteteThesenAdapter(beantworteteThesenKandidatenModels, getContext(), MODE);
        lv.setAdapter(listadapter);
        if(MODE.equals("MEINPROFIL"))updatePositionenKandidatView(kid);
        if(MODE.equals("WAEHLER")) updatePositionenWaehlerView();
        if(MODE.equals("NORMAL")) updatePositionenNormalView(kid);
        return view;
    }

    public void updatePositionenNormalView(String KID) {
        Database db = new Database(getContext());
        kandidat = db.getKandidat(KID);
        if(kandidat != null){
            JSONArray kandidatthesen = kandidat.getBeantworteteThesen();
            Log.d("JSONARRAY", kandidatthesen.toString());

            for(int i = 0; i< kandidatthesen.length(); i++){
                JSONObject jsonObject;
                try {
                    jsonObject = (JSONObject) kandidatthesen.get(i);
                    String tid = jsonObject.getString("TID");
                    String position = jsonObject.getString("POS");
                    String thesentext = db.getThesentextWithTID(tid);
                    String userposition = db.getUserPositionWithTID(tid);
                    beantworteteThesenKandidatenModels.add(new BeantworteteThesenKandidatenModel(thesentext, tid, position, userposition));
                    lv.setAdapter(listadapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updatePositionenWaehlerView(){
        Database db = new Database(getContext());
        JSONObject object = db.getallPositions();
        try {
            JSONArray  positionen = object.getJSONArray("Positionen");
            for (int i = 0; i < positionen.length() ; i++){
                JSONObject position = (JSONObject) positionen.get(i);
                String TID = position.getString("TID");
                String positiontxt = position.getString("POSITION");
                String thesentext = db.getThesentextWithTID(TID);
                beantworteteThesenKandidatenModels.add(new BeantworteteThesenKandidatenModel(thesentext, TID, positiontxt, ""));
                listadapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updatePositionenKandidatView(String kid){
        Database db = new Database(getContext());
        kandidat = db.getKandidat(kid);
        JSONArray kandidatthesen = kandidat.getBeantworteteThesen();
        Log.d("JSONARRAY", kandidatthesen.toString());

        for(int i = 0; i< kandidatthesen.length(); i++){
            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject) kandidatthesen.get(i);
                String tid = jsonObject.getString("TID");
                String position = jsonObject.getString("POS");
                String thesentext = db.getThesentextWithTID(tid);
                beantworteteThesenKandidatenModels.add(new BeantworteteThesenKandidatenModel(thesentext, tid, position, ""));
                listadapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
