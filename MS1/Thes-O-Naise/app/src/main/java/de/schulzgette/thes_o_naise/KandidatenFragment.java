package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.adapter.KandidatenListAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class KandidatenFragment extends Fragment {

    private static final String MODE = "NORMAL";
    //private static final String MODE = "MATCHING";
    private String modus;
    View myView;
    ListView lv;
    KandidatenListAdapter listadapter;
    SharedPreferences sharedPreferences;
    String wahlkreis ;

    ArrayList<KandidatenModel> kandidatenModels;

    public KandidatenFragment() {
        // Required empty public constructor
    }

    public static KandidatenFragment newInstance(String param1) {
        KandidatenFragment fragment = new KandidatenFragment();
        Bundle args = new Bundle();
        args.putString(MODE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modus = getArguments().getString(MODE);
        }
        sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
        wahlkreis = sharedPreferences.getString("wahlkreis", "");
        getKandidaten(wahlkreis);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_kandidaten, container, false);



        if(kandidatenModels != null) {
            lv = (ListView) myView.findViewById(R.id.listviewkandidaten);
            listadapter = new KandidatenListAdapter(kandidatenModels, this.getActivity());
            lv.setAdapter(listadapter);
            listadapter.notifyDataSetChanged();
        }

        return myView;
    }

    private  void getKandidaten(String wahlkreis) {
        Database db = new Database(getContext());
        try {
            HttpClient.GET("kandidaten"+ "?wahlkreis=" + wahlkreis,  new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        Log.d("BODd", jsonData);
                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            JSONArray jArray = Jobject.getJSONArray("Kandidaten");
                            JSONObject kandidaten_data;
                            Database db = new Database(getContext());
                            if (jArray != null) {

                                for (int i = 0; i < jArray.length(); i++) {
                                    kandidaten_data = (JSONObject) jArray.get(i);
                                    Log.d("KANDIDAT", kandidaten_data.toString());
                                    String KID = (String)  kandidaten_data.get("KID");
                                    String vorname = (String) kandidaten_data.get("vorname");
                                    String nachname = (String) kandidaten_data.get("nachname");
                                    String partei = (String) kandidaten_data.get("Partei");
                                    String email = (String) kandidaten_data.get("email");
                                    String wahlkreis = (String) kandidaten_data.get("wahlkreis");
                                    JSONArray beantworteteThesen = (JSONArray) kandidaten_data.get("Thesen_beantwortet");
                                    db.insertKandidat( KID, vorname, nachname, partei, email, wahlkreis, beantworteteThesen);
                                }
                            }
                            Log.d("Kandidaten", "In der Datenbank");

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
        kandidatenModels = db.getAllKandidaten(wahlkreis);
    }
}
