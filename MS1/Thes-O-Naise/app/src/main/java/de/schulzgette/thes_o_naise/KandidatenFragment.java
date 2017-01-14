package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.adapter.KandidatenErgebnisListAdapter;
import de.schulzgette.thes_o_naise.adapter.KandidatenListAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static de.schulzgette.thes_o_naise.R.id.spinner_kategorie_ergebnis;


public class KandidatenFragment extends Fragment {

    private static final String MODE = "NORMAL";
    //private static final String MODE = "MATCHING";
    private String modus = "NORMAL";
    View myView;
    ListView lv;
    KandidatenListAdapter listadapter;
    KandidatenErgebnisListAdapter listAdapterErgebnis;
    SharedPreferences sharedPreferences ;
    String wahlkreis ;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String kategorie;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if(modus.equals("NORMAL")) {
           myView = inflater.inflate(R.layout.fragment_kandidaten, container, false);
           getKandidaten(wahlkreis);
           Database db = new Database(getContext());
           kandidatenModels = db.getAllKandidaten(wahlkreis);
           if (kandidatenModels != null) {
               lv = (ListView) myView.findViewById(R.id.listviewkandidaten);
               listadapter = new KandidatenListAdapter(kandidatenModels, this.getActivity());
               lv.setAdapter(listadapter);
               listadapter.notifyDataSetChanged();
           }
       }else{
           myView = inflater.inflate(R.layout.fragment_kandidaten_ergebnis, container, false);

           lv = (ListView) myView.findViewById(R.id.listviewkandidatenergebnis);

           spinner = (Spinner) myView.findViewById(spinner_kategorie_ergebnis);
           adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.kategorien_ergebnis, android.R.layout.simple_spinner_item);
           adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);
           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onNothingSelected(AdapterView<?> parent) {
               }

               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   kategorie = (String) parent.getItemAtPosition(position);
                   Log.d("ausgewählte Kategorie:", kategorie);
                   if(kategorie.equals("Insgesamt")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_INSGESAMT;
                   if(kategorie.equals("Lokale Thesen")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL;
                   if(kategorie.equals("Aussenpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_AP;
                   if(kategorie.equals("Umwelt")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT;
                   if(kategorie.equals("Satire")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE;
                   if(kategorie.equals("Drogenpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_DrogenP;
                   if(kategorie.equals("Bildungspolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_BildungsP;
                   if(kategorie.equals("Innenpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_InnenP;
                   if(kategorie.equals("Wirtschaftspolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_WirtschaftP;
                   if(kategorie.equals("Energiepolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_EnergieP;
                   if(kategorie.equals("Demokratie")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_Demokratie;
                   if(kategorie.equals("Justiz")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_Justiz;
                   if(kategorie.equals("Sozialpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_SozialP;
                   if(kategorie.equals("Landwirtschaftspolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_LandwirtschaftP;
                   if(kategorie.equals("Familienpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_FamilienP;
                   if(kategorie.equals("Rentenpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_RentenP;
                   if(kategorie.equals("Gesundheitspolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_GesundheitP;
                   if(kategorie.equals("Verkehrspolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_VerkehrP;
                   if(kategorie.equals("Digitalpolitik")) kategorie =  Database.KandidatenTable.COLUMN_NAME_PUNKTE_DigitalP;

                   sharedPreferences =  getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
                   sharedPreferences.edit().putString("ergebniskategorie", kategorie).apply();
                   Database db = new Database(getContext());
                   kandidatenModels = db.sortKandidatenScore(kategorie, wahlkreis);
                   if (kandidatenModels != null) {

                       listAdapterErgebnis = new KandidatenErgebnisListAdapter(kandidatenModels, getActivity());
                       lv.setAdapter(listAdapterErgebnis);
                       listAdapterErgebnis.notifyDataSetChanged();
                   }
               }

           });


       }
        return myView;
    }

    public  void getKandidaten(String wahlkreis) {
        try {
            HttpClient.GET("kandidaten"+ "?wahlkreis=" + wahlkreis, getContext(),  new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Keine Verbindung zum Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        //Log.d("BODY", jsonData);
                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            JSONArray jArray = Jobject.getJSONArray("Kandidaten");
                            JSONObject kandidaten_data;
                            Database db = new Database(getContext());
                            if (jArray != null) {

                                for (int i = 0; i < jArray.length(); i++) {
                                    kandidaten_data = (JSONObject) jArray.get(i);
                                    //Log.d("KANDIDAT", kandidaten_data.toString());
                                    String KID = (String)  kandidaten_data.get("KID");
                                    String vorname = (String) kandidaten_data.get("vorname");
                                    String nachname = (String) kandidaten_data.get("nachname");
                                    String partei = (String) kandidaten_data.get("Partei");
                                    String email = (String) kandidaten_data.get("email");
                                    String wahlkreis = (String) kandidaten_data.get("wahlkreis");
                                    JSONArray beantworteteThesen = (JSONArray) kandidaten_data.get("Thesen_beantwortet");
                                    JSONArray begruendungen = (JSONArray) kandidaten_data.get("Begruendungen");
                                    JSONObject biografie = (JSONObject) kandidaten_data.get("Biografie");
                                    JSONObject wahlprogramm = (JSONObject) kandidaten_data.get("Wahlprogramm");
                                    db.insertKandidat( KID, vorname, nachname, partei, email, wahlkreis, beantworteteThesen, begruendungen, biografie, wahlprogramm);
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
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
