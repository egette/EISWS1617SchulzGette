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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class WahlprogrammTabFragment extends Fragment {

    private static final String ARG_KID = "kid";
    private static final String ARG_MODE = "MODE";
    private String kid;
    private String MODE;
    static  KandidatenModel kandidat;
    View myView;


    public WahlprogrammTabFragment() {
        // Required empty public constructor
    }



    public static WahlprogrammTabFragment newInstance(String MODE, String kid) {
        WahlprogrammTabFragment fragment = new WahlprogrammTabFragment();
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
        updateKandidat(kid);
    }

    public void updateKandidat(String kid){
        Database db = new Database(getContext());
        kandidat = db.getKandidat(kid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_wahlprogramm_tab, container, false);
        final Button veröffentlichen = (Button) myView.findViewById(R.id.veröffentlichenwahlprogrammid);
        final Button bearbeiten = (Button) myView.findViewById(R.id.bearbeitenbutton2id);
        final EditText webseiteedit =  (EditText) myView.findViewById(R.id.webseiteeditid);
        final EditText beschreibungedit =  (EditText) myView.findViewById(R.id.beschreibungeditid);
        final EditText linkedit =  (EditText) myView.findViewById(R.id.linkeditid);
        final TextView webseitetext = (TextView) myView.findViewById(R.id.webseitetextid);
        final TextView beschreibungtext = (TextView) myView.findViewById(R.id.beschreibungtextid);
        final TextView linktext = (TextView) myView.findViewById(R.id.linktextid);
        final EditText passwortedit = (EditText) myView.findViewById(R.id.passwortid);

        try {
            beschreibungtext.setText(kandidat.getBeschreibung());
            linktext.setText(kandidat.getLink());
            webseitetext.setText(kandidat.getWebseite());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(MODE.equals("NORMAL")) bearbeiten.setVisibility(View.GONE);
        bearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webseitetext.setVisibility(View.GONE);
                beschreibungtext.setVisibility(View.GONE);
                linktext.setVisibility(View.GONE);
                webseiteedit.setVisibility(View.VISIBLE);
                beschreibungedit.setVisibility(View.VISIBLE);
                linkedit.setVisibility(View.VISIBLE);
                webseiteedit.setText(webseitetext.getText().toString());
                beschreibungedit.setText(beschreibungtext.getText().toString());
                linkedit.setText(linktext.getText().toString());
                passwortedit.setVisibility(View.VISIBLE);
                veröffentlichen.setVisibility(View.VISIBLE);
                bearbeiten.setVisibility(View.GONE);

            }
        });
        veröffentlichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webseitetext2 = webseiteedit.getText().toString();
                String beschreibungtext2 = beschreibungedit.getText().toString();
                String linktext2 = linkedit.getText().toString();
                String passwort = passwortedit.getText().toString();
                if(passwort.isEmpty()){
                 Toast.makeText(getContext(), "Bitte Passwort eingeben", Toast.LENGTH_SHORT).show();
                }else {
                    sendWahlprogrammToServer(webseitetext2, beschreibungtext2, linktext2, passwort);
                    webseiteedit.setVisibility(View.GONE);
                    beschreibungedit.setVisibility(View.GONE);
                    linkedit.setVisibility(View.GONE);
                    veröffentlichen.setVisibility(View.GONE);
                    bearbeiten.setVisibility(View.VISIBLE);
                    webseitetext.setText(webseitetext2);
                    beschreibungtext.setText(beschreibungtext2);
                    linktext.setText(linktext2);
                    webseitetext.setVisibility(View.VISIBLE);
                    beschreibungtext.setVisibility(View.VISIBLE);
                    linktext.setVisibility(View.VISIBLE);
                    passwortedit.setVisibility(View.GONE);
                }
            }
        });
        return myView;
    }

    public void sendWahlprogrammToServer(String webseitetext, String beschreibungtext, String linktext, String passwort){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String uid = sharedPreferences.getString("UID", "");
        JSONObject jsonObject = new JSONObject();
        JSONObject wahlprogramm = new JSONObject();
        try {
            wahlprogramm.accumulate("Webseite", webseitetext);
            wahlprogramm.accumulate("Text", beschreibungtext);
            wahlprogramm.accumulate("Link", linktext);
            jsonObject.accumulate("Wahlprogramm", wahlprogramm);
            jsonObject.accumulate("token", token);
            jsonObject.accumulate("passwort", passwort);
            jsonObject.accumulate("typ", "kandidat");
            jsonObject.accumulate("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsondata = jsonObject.toString();
        try {
            HttpClient.PUT("user", jsondata, getContext(), new Callback() {
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
                                Toast.makeText(getActivity(), "Ihr Wahlprogramm wurde veröffentlicht", Toast.LENGTH_SHORT).show();
                            }
                        });

                        TheseToLokalDatabase.updateKandidatData(kid, db, getContext());
                        updateKandidat(kid);
                        response.body().close();
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihr Wahlprogramm konnte nicht veröffentlicht werden", Toast.LENGTH_SHORT).show();
                            }
                        });
                        response.body().close();
                    }
                }
            });
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }


}