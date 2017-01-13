package de.schulzgette.thes_o_naise;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BegruendungModel;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.Models.PrognosenModel;
import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.adapter.PrognosenAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class StatistikFragment extends Fragment {
    View myView;
    ListView lv;
    ArrayList<PrognosenModel> prognosenModels = new ArrayList<>();
    PrognosenAdapter listadapter;
    Integer anzahlwaehler;
    Integer anzahlkandidaten;

    public StatistikFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String wahlkreis = sharedPreferences.getString("wahlkreis", "");
        getPrognosenModels(wahlkreis);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_statistik, container, false);
        lv = (ListView) myView.findViewById(R.id.listviewprognose);



        return myView;
    }


    public void getPrognosenModels(String wahlkreis){
        try {
            HttpClient.GET("statistik/"+ wahlkreis,  new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        Database db = new Database(getContext());

                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            JSONArray jArray = Jobject.getJSONArray("Prognose");
                            anzahlwaehler = Jobject.getInt("AnzahlWaehler");
                            anzahlkandidaten = Jobject.getInt("AnzahlKandidaten");
                            JSONObject prognose_data;
                            ArrayList<PrognosenModel> neueprognosenModels = new ArrayList<>();
                            if (jArray != null) {

                                for (int i = jArray.length()-1; i > -1 ; i--) {
                                    prognose_data = (JSONObject) jArray.get(i);
                                    String KID = prognose_data.getString("KID");
                                    KandidatenModel kandidatenModel = db.getKandidat(KID);
                                    JSONArray Platzierung = prognose_data.getJSONArray("Platz");
                                    Integer platz1 = Platzierung.getInt(1);
                                    Integer platz2 = Platzierung.getInt(2);
                                    Integer platz3 = Platzierung.getInt(3);
                                    Integer platz4 = Platzierung.getInt(4);
                                    Integer platz5 = Platzierung.getInt(5);
                                    Integer platz6 = Platzierung.getInt(6);
                                    Integer platz7 = Platzierung.getInt(7);
                                    Integer platz8 = Platzierung.getInt(8);
                                    Integer platz9 = Platzierung.getInt(9);
                                    Integer platz10 = Platzierung.getInt(10);
                                    Log.d("STATISTIK: ", platz1.toString());
                                    neueprognosenModels.add(new PrognosenModel(KID,platz1,platz2,platz3,platz4,platz5,platz6,platz7,platz8,platz9,platz10,kandidatenModel));
                                }
                                prognosenModels = neueprognosenModels;
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        loadHosts();
                                    }
                                });

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        response.body().close();
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        response.body().close();
                    }
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHosts(){
        if(prognosenModels != null) {
            listadapter = new PrognosenAdapter(prognosenModels, getContext());
            lv.setAdapter(listadapter);
            listadapter.notifyDataSetChanged();
        }
    }
}
