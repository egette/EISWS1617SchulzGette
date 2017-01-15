package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import de.schulzgette.thes_o_naise.adapter.MeinProfilKandidatAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MeinProfilKandidatActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MeinProfilKandidatAdapter adapter;
    static final String ARG_KID = "KID";
    String kid;
    String MODE;
    KandidatenModel kandidat;
    Database db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mein_profil_kandidat);
        db = new Database(getBaseContext());
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            kid = (String) bd.get("KID");
            MODE = (String) bd.get("MODE");
            TheseToLokalDatabase.updateKandidatData(kid, db, getApplicationContext());
            updateKandidatView(kid);
            adapter = new MeinProfilKandidatAdapter(getSupportFragmentManager(), kid, MODE);
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
            Boolean isExpanded = false;
            public void onClick(View v) {

                layoutinfotext.setVisibility((layoutinfotext.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);

                float deg = hide.getRotation() + 180F;
                hide.setImageResource(isExpanded?R.drawable.ic_expand_less_black_24dp:R.drawable.ic_expand_more_black_24dp);
                if(isExpanded){
                    isExpanded = false;
                }else{
                    isExpanded = true;
                }
            }
        });
    }

    public void updateKandidatView(String KID) {

        kandidat = db.getKandidat(KID);
        final Button veröffentlichen = (Button) findViewById(R.id.veröffentlichenbiografieid3);
        final Button bearbeiten = (Button) findViewById(R.id.bearbeitenbuttonid3);
        final TextView vorname = (TextView) findViewById(R.id.vornameid);
        final TextView nachname = (TextView) findViewById(R.id.nachnameid);
        final TextView partei = (TextView) findViewById(R.id.parteiid);
        final TextView wahlkreis = (TextView) findViewById(R.id.wahlkreisid);
        final TextView email = (TextView) findViewById(R.id.emailid);
        final EditText parteiedit = (EditText) findViewById(R.id.parteieditid);
        final EditText wahlkreisedit = (EditText) findViewById(R.id.wahlkreiseditid);
        final EditText emailedit = (EditText) findViewById(R.id.emaileditid);
        final EditText pw = (EditText) findViewById(R.id.passwortid);

        if (kandidat != null) {
            vorname.setText(kandidat.getVorname());
            nachname.setText(kandidat.getNachname());
            partei.setText(kandidat.getPartei());
            wahlkreis.setText(kandidat.getWahlkreis());
            email.setText(kandidat.getEmail());
        }
        if (MODE.equals("NORMAL")) bearbeiten.setVisibility(View.GONE);
        bearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partei.setVisibility(View.GONE);
                wahlkreis.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                parteiedit.setVisibility(View.VISIBLE);
                wahlkreisedit.setVisibility(View.VISIBLE);
                emailedit.setVisibility(View.VISIBLE);
                parteiedit.setText(partei.getText().toString());
                wahlkreisedit.setText(wahlkreis.getText().toString());
                emailedit.setText(email.getText().toString());

                veröffentlichen.setVisibility(View.VISIBLE);
                pw.setVisibility(View.VISIBLE);
                bearbeiten.setVisibility(View.GONE);
            }
        });
        veröffentlichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parteitext2 = parteiedit.getText().toString();
                String wahlkreistext2 = wahlkreisedit.getText().toString();
                String emailtext2 = emailedit.getText().toString();
                String passwort = pw.getText().toString();
                if(passwort.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Bitte Passwort eingeben", Toast.LENGTH_SHORT).show();
                }else {
                    partei.setText(parteitext2);
                    wahlkreis.setText(wahlkreistext2);
                    email.setText(emailtext2);
                    partei.setVisibility(View.VISIBLE);
                    wahlkreis.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    sendKandidatenProfilToServer(parteitext2, wahlkreistext2, emailtext2, passwort);
                    parteiedit.setVisibility(View.GONE);
                    wahlkreisedit.setVisibility(View.GONE);
                    emailedit.setVisibility(View.GONE);

                    pw.setVisibility(View.GONE);
                    veröffentlichen.setVisibility(View.GONE);
                    bearbeiten.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void updateKandidat(String kid){
        kandidat = db.getKandidat(kid);
    }
    public void sendKandidatenProfilToServer(String partei, String wahlkreis, String email, String passwort){

        SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String uid = sharedPreferences.getString("UID", "");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("Partei", partei);
            jsonObject.accumulate("wahlkreis", wahlkreis);
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("token", token);
            jsonObject.accumulate("typ", "kandidat");
            jsonObject.accumulate("uid", uid);
            jsonObject.accumulate("passwort", passwort);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsondata = jsonObject.toString();
        try {
            HttpClient.PUT("user", jsondata, getApplicationContext(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Integer statusCode = response.code();
                    if (response.isSuccessful()) {
                        Database db = new Database(getBaseContext());
                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Ihr Profil wurde überarbeitet", Toast.LENGTH_SHORT).show();
                            }
                        });
                        TheseToLokalDatabase.updateKandidatData(kid, db, getApplicationContext());
                        updateKandidat(kid);
                        response.body().close();
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Ihr Profil konnte nicht überarbeitet werden", Toast.LENGTH_SHORT).show();
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
