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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");
        final Database db = new Database(getContext(), UID);
        JSONObject mainObj = db.getallPositions();
        JSONArray Jarray = mainObj.getJSONArray("Positionen");
        for(int i = 0; i<Jarray.length(); i++){
            JSONObject object= Jarray.getJSONObject(i);
            String tid = object.getString("TID");
            String position = object.getString("POSITION");
            db.updatepositionwithServerdata(position, tid);
        }
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
                                    db.updateKandidatScore(KID,Punkte_Ingesamt,Punkte_Lokal,Punkte_Umwelt,Punkte_AP,Punkte_Satire);
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
