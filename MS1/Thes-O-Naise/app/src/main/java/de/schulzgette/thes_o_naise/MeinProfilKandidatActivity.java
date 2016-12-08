package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.adapter.MeinProfilAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MeinProfilKandidatActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MeinProfilAdapter adapter;
    static final String ARG_KID = "KID";
    String kid;
    String MODE;
    KandidatenModel kandidat;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mein_profil_kandidat);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            kid = (String) bd.get("KID");
            MODE = (String) bd.get("MODE");
            updateKandidatData(kid);
            updateKandidatView(kid);
            adapter = new MeinProfilAdapter(getSupportFragmentManager(), kid, MODE, kandidat);
        }
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        final TabLayout.Tab biografie = tabLayout.newTab();
        final TabLayout.Tab positionen = tabLayout.newTab();
        final TabLayout.Tab begründungen = tabLayout.newTab();
        final TabLayout.Tab wahlprogramm = tabLayout.newTab();

        biografie.setText("Biografie");
        positionen.setText("Positionen");
        begründungen.setText("Begründungen");
        wahlprogramm.setText("Wahlprogramm");

        tabLayout.addTab(biografie, 0);
        tabLayout.addTab(positionen, 1);
        tabLayout.addTab(begründungen, 2);
        tabLayout.addTab(wahlprogramm, 3);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setSelectedTabIndicatorHeight(15);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final RelativeLayout layoutinfotext = (RelativeLayout) findViewById(R.id.layoutinfotext);
        final ImageButton hide = (ImageButton) findViewById(R.id.hideinfotext);
        hide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                layoutinfotext.setVisibility((layoutinfotext.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);

                float deg = hide.getRotation() + 180F;
                hide.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
            }
        });
    }

    public void updateKandidatView(String KID) {
        Database db = new Database(getBaseContext());
        kandidat = db.getKandidat(KID);
        final TextView vorname = (TextView) findViewById(R.id.vornameid);
        final TextView nachname = (TextView) findViewById(R.id.nachnameid);
        final TextView partei = (TextView) findViewById(R.id.parteiid);
        final TextView wahlkreis = (TextView) findViewById(R.id.wahlkreisid);
        final TextView email = (TextView) findViewById(R.id.emailid);


        if (kandidat != null) {
            vorname.setText(kandidat.getVorname());
            nachname.setText(kandidat.getNachname());
            partei.setText(kandidat.getPartei());
            wahlkreis.setText(kandidat.getWahlkreis());
            email.setText(kandidat.getEmail());
        }
    }

    public void updateKandidatData(String KID){
        try {
            HttpClient.GET("kandidaten?KID="+KID,  new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Integer statusCode = response.code();
                    if (response.isSuccessful()) {
                        Database db = new Database(getApplicationContext());
                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        JSONObject kandidaten_data = null;
                        try {
                            kandidaten_data = new JSONObject(jsonData);
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        response.body().close();
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        if(statusCode==400){

                        }
                        response.body().close();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
