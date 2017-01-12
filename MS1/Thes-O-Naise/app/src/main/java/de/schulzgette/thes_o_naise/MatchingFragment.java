package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class MatchingFragment extends Fragment {
    View myView;
    Boolean prognose2= false;

    public MatchingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_matching, container, false);

        Button matching = (Button) myView.findViewById(R.id.matching_button);
        ToggleButton prognose = (ToggleButton) myView.findViewById(R.id.prognosebutton);
        prognose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    prognose2 = true;
                }else{
                    prognose2 = false;
                }
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    positionWaehlerToServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return myView;
    }
    
    public void positionWaehlerToServer() throws JSONException {
        final Database db = new Database(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
        JSONObject mainObj = db.getallPositions();
        JSONArray Jarray = mainObj.getJSONArray("Positionen");
        for(int i = 0; i<Jarray.length(); i++){
            JSONObject object= Jarray.getJSONObject(i);
            String tid = object.getString("TID");
            String position = object.getString("POSITION");
            db.updatepositionwithServerdata(position, tid);
        }
        if(prognose2){
            String UID = sharedPreferences.getString("UID", "");
            mainObj.accumulate("UID", UID);
        }else{
            mainObj.accumulate("UID", "null");
        }
        String wahlkreis = sharedPreferences.getString("wahlkreis", "");
        mainObj.accumulate("wahlkreis", wahlkreis);
        String positiondata = mainObj.toString();
        Log.d("Jobject:", positiondata);
        try {
            HttpClient.POST("matching", positiondata, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        Log.d("body:", jsonData);
                        try {
                            JSONObject result = new JSONObject(jsonData);
                            JSONArray jArray = result.getJSONArray("Kandidaten");
                            JSONObject kandidat_result;
                            if (jArray != null) {
                                for (int i = 0; i < jArray.length(); i++) {
                                    kandidat_result = (JSONObject) jArray.get(i);
                                    String KID = (String) kandidat_result.get("KID");
                                    //TODO MEHR KATEGORIEN ?
                                    Integer Punkte_Ingesamt = (Integer) kandidat_result.get("Zaehler");
                                    Integer Punkte_Lokal = (Integer) kandidat_result.get("Lokal");
                                    Integer Punkte_Umwelt = (Integer) kandidat_result.get("Umwelt");
                                    Integer Punkte_AP = (Integer) kandidat_result.get("Aussenpolitik");
                                    Integer Punkte_Satire = (Integer) kandidat_result.get("Satire");
                                    Integer verarbeitete_pos = (Integer) kandidat_result.get("AnzahlPOS");
                                    Integer anzahlLokal = (Integer) kandidat_result.get("AnzahlLokal");
                                    Integer anzahlUmwelt = (Integer) kandidat_result.get("AnzahlUmwelt");
                                    Integer anzahlAP = (Integer) kandidat_result.get("AnzahlAussenpolitik");
                                    Integer anzahlSatire = (Integer) kandidat_result.get("AnzahlSatire");

                                    db.updateKandidatScore(KID,Punkte_Ingesamt,Punkte_Lokal,Punkte_Umwelt,Punkte_AP,Punkte_Satire, verarbeitete_pos, anzahlLokal, anzahlUmwelt, anzahlAP, anzahlSatire);
                                }
                            }
                            toErgebnisFragment();
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
    public void toErgebnisFragment(){
        FragmentManager fm = getFragmentManager();
        KandidatenFragment kandidatenFragment = new KandidatenFragment().newInstance("MATCHING");

        fm.beginTransaction()
                .replace(R.id.content_frame_nav, kandidatenFragment )
                .commit();

    }
}
