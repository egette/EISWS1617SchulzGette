package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.adapter.KandidatBeantworteteThesenAdapter;
import de.schulzgette.thes_o_naise.database.Database;

public class KandidatAnsichtActivity extends AppCompatActivity {

    static final String ARG_KID = "KID";
    String kid;
    KandidatenModel kandidat;
    ListView lv;
    KandidatBeantworteteThesenAdapter listadapter;
    ArrayList<BeantworteteThesenKandidatenModel> beantworteteThesenKandidatenModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kandidat_ansicht);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        lv = (ListView) findViewById(R.id.kandidatthesenliste);
        listadapter = new KandidatBeantworteteThesenAdapter(beantworteteThesenKandidatenModels, getBaseContext());

        if(bd != null)
        {
            kid = (String) bd.get("KID");
            updateKandidatView(kid);
        }

    }

    public void updateKandidatView(String KID) {
        Database db = new Database(getBaseContext());
        kandidat = db.getKandidat(KID);
        final TextView vorname = (TextView) findViewById(R.id.vornamekandidatansicht);
        final TextView nachname = (TextView) findViewById(R.id.nachnamekandidatansicht);
        final TextView partei = (TextView) findViewById(R.id.parteikandidatansicht);


        if(kandidat != null){
            vorname.setText(kandidat.getVorname());
            nachname.setText(kandidat.getNachname());
            partei.setText(kandidat.getPartei());
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
}
