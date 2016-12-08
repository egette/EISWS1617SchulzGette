package de.schulzgette.thes_o_naise.KandidatenAnsichtFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static de.schulzgette.thes_o_naise.R.id.bearbeitenbuttonid;
import static de.schulzgette.thes_o_naise.R.id.veröffentlichenbiografieid;


public class BiografieTabFragment extends Fragment {

    private static final String ARG_KID = "kid";
    private static final String ARG_MODE = "MODE";
    private String kid;
    private String MODE;
    static  KandidatenModel kandidat;
    View myView;


    public BiografieTabFragment() {
        // Required empty public constructor
    }



    public static BiografieTabFragment newInstance(String MODE, String kid, KandidatenModel kandidat2) {
        BiografieTabFragment fragment = new BiografieTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, MODE);
        args.putString(ARG_KID, kid);
        fragment.setArguments(args);
        kandidat = kandidat2;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MODE = getArguments().getString(ARG_MODE);
            kid = getArguments().getString(ARG_KID);
        }
        Database db = new Database(getContext());
        kandidat = db.getKandidat(kid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       myView = inflater.inflate(R.layout.fragment_biografie_tab, container, false);
        final Button veröffentlichen = (Button) myView.findViewById(veröffentlichenbiografieid);
        final Button bearbeiten = (Button) myView.findViewById(bearbeitenbuttonid);
        final EditText geburtsdatumedit =  (EditText) myView.findViewById(R.id.geburtsdatumeditid);
        final EditText bildungswegedit =  (EditText) myView.findViewById(R.id.bildungswegeditid);
        final EditText berufeedit =  (EditText) myView.findViewById(R.id.berufeeditid);
        final EditText mitgliedschaftenedit =  (EditText) myView.findViewById(R.id.mitgliedschafteneditid);
        final TextView geburtsdatumtext = (TextView) myView.findViewById(R.id.geburtsdatumtextid);
        final TextView bildungswegtext = (TextView) myView.findViewById(R.id.bildungswegtextid);
        final TextView berufetext = (TextView) myView.findViewById(R.id.berufetextid);
        final TextView mitgliedschaftentext = (TextView) myView.findViewById(R.id.mitgliedschaftentextid);

        bearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geburtsdatumtext.setVisibility(View.INVISIBLE);
                bildungswegtext.setVisibility(View.INVISIBLE);
                berufetext.setVisibility(View.INVISIBLE);
                mitgliedschaftentext.setVisibility(View.INVISIBLE);
                geburtsdatumedit.setVisibility(View.VISIBLE);
                bildungswegedit.setVisibility(View.VISIBLE);
                berufeedit.setVisibility(View.VISIBLE);
                mitgliedschaftenedit.setVisibility(View.VISIBLE);
                geburtsdatumedit.setText(geburtsdatumtext.getText().toString());
                bildungswegedit.setText(bildungswegtext.getText().toString());
                berufeedit.setText(berufetext.getText().toString());
                mitgliedschaftenedit.setText(mitgliedschaftentext.getText().toString());

                veröffentlichen.setVisibility(View.VISIBLE);
                bearbeiten.setVisibility(View.INVISIBLE);

            }
        });
        veröffentlichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geburtsdatumtext2 = geburtsdatumedit.getText().toString();
                String bildungeswegtext2 = bildungswegedit.getText().toString();
                String berufetext2 = berufeedit.getText().toString();
                String mitgliedschaftentext2 = mitgliedschaftenedit.getText().toString();
                geburtsdatumtext.setText(geburtsdatumtext2);
                bildungswegtext.setText(bildungeswegtext2);
                berufetext.setText(berufetext2);
                mitgliedschaftentext.setText(mitgliedschaftentext2);
                geburtsdatumtext.setVisibility(View.VISIBLE);
                bildungswegtext.setVisibility(View.VISIBLE);
                berufetext.setVisibility(View.VISIBLE);
                mitgliedschaftentext.setVisibility(View.VISIBLE);
                sendBiografieToServer(geburtsdatumtext2, bildungeswegtext2, berufetext2, mitgliedschaftentext2);
                geburtsdatumedit.setVisibility(View.INVISIBLE);
                bildungswegedit.setVisibility(View.INVISIBLE);
                berufeedit.setVisibility(View.INVISIBLE);
                mitgliedschaftenedit.setVisibility(View.INVISIBLE);
                veröffentlichen.setVisibility(View.INVISIBLE);
                bearbeiten.setVisibility(View.VISIBLE);
            }
        });
        return myView;
    }

        public void sendBiografieToServer(String geburtsdatum, String bildungeswegtext, String berufetext, String mitgliedschaftentext){

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            String uid = sharedPreferences.getString("UID", "");
            JSONObject jsonObject = new JSONObject();
            JSONObject biografie = new JSONObject();
            try {
                biografie.accumulate("Geburtsdatum", geburtsdatum);
                biografie.accumulate("Bildungsweg", bildungeswegtext);
                biografie.accumulate("Berufe", berufetext);
                biografie.accumulate("Mitgliedschaften", mitgliedschaftentext);
                jsonObject.accumulate("Biografie", biografie);
                jsonObject.accumulate("token", token);
                jsonObject.accumulate("typ", "kandidat");
                jsonObject.accumulate("uid", uid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsondata = jsonObject.toString();
            try {
                HttpClient.PUT("user", jsondata, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Integer statusCode = response.code();
                        if (response.isSuccessful()) {
                            Database db = new Database(getContext());
                            Log.d("Response", response.toString());
                            String jsonData = response.body().string();

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Ihre Biografie wurde veröffentlicht", Toast.LENGTH_SHORT).show();
                                }
                            });

                            response.body().close();
                        } else {
                            Log.d("Statuscode", String.valueOf(response.code()));
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getActivity(), "Ihre Biografie konnten nicht veröffentlicht werden", Toast.LENGTH_SHORT).show();
                                }
                            });
                            response.body().close();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
