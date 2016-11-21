package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.services.EventBus;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AuthActivity extends AppCompatActivity {
    String username;
    String email;
    String passwort;
    JSONArray jArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        Button authbutton = (Button) findViewById(R.id.auth_button);

        authbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText user =  (EditText) findViewById(R.id.usernameid);
                username =  user.getText().toString();
                EditText usermail =  (EditText) findViewById(R.id.emailid);
                email =  usermail.getText().toString();
                EditText pw =  (EditText) findViewById(R.id.passwortid);
                passwort =  pw.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("username", username);
                    jsonObject.accumulate("password", passwort);
                    jsonObject.accumulate("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String jsondata =  jsonObject.toString();
                authUser(jsondata);
            }
        });
    }

    public void authUser(String authData) {

        try {
            HttpClient.POST("login", authData, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();
                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            String token = (String) Jobject.get("token");
                            String uid = (String) Jobject.get("userID");
                            String typ = uid.substring(0,1);
                            String wahlkreis = (String) Jobject.get("wahlkreis");
                            SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
                            sharedPreferences.edit().putString("token", token).apply();
                            sharedPreferences.edit().putString("UID", uid).apply();
                            sharedPreferences.edit().putString("wahlkreis", wahlkreis).apply();
                            if(typ.equals("W"))sharedPreferences.edit().putString("typ", "waehler").apply();
                            if(typ.equals("K"))sharedPreferences.edit().putString("typ", "kandidat").apply();
                            getAllThesenFromWahlkreis(wahlkreis);
                            getKandidaten(wahlkreis);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        response.body().close();
                        toNavigationActivity();
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
    private  void getAllThesenFromWahlkreis (String wahlkreis) {

        try {
            HttpClient.GET("thesen"+ "?wahlkreis=" + wahlkreis,  new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        String jsonData = response.body().string();

                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            jArray = Jobject.getJSONArray("Thesen");
                            //Log.d("THESEN ARRAY: ", jArray.toString());

                            JSONObject these_data;
                            Database db = new Database(getApplicationContext());
                            if (jArray != null) {

                                for (int i = 0; i < jArray.length(); i++) {
                                    these_data = new JSONObject((String) jArray.get(i));
                                    String TID = (String) these_data.get("TID");
                                    String thesentext = (String) these_data.get("thesentext");
                                    Integer proINT= (Integer) these_data.get("Anzahl_Zustimmung");
                                    Integer neutralINT = (Integer) these_data.get("Anzahl_Neutral");
                                    Integer contraINT = (Integer) these_data.get("Anzahl_Ablehnung");
                                    JSONArray K_PRO = (JSONArray) these_data.get("K_PRO");
                                    JSONArray K_NEUTRAL = (JSONArray) these_data.get("K_NEUTRAL");
                                    JSONArray K_CONTRA = (JSONArray) these_data.get("K_CONTRA");
                                    String kategorie = (String) these_data.get("kategorie");
                                    String wahlkreis = (String) these_data.get("wahlkreis");
                                    Integer likesINT = (Integer) these_data.get("Likes");
                                    db.insertThese(TID, thesentext, kategorie, wahlkreis, likesINT, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA);
                                }
                            }
                            Log.d("Thesen", "In der Datenbank");

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

    public  void getKandidaten(String wahlkreis) {
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
                        //Log.d("BODY", jsonData);
                        try {
                            JSONObject Jobject = new JSONObject(jsonData);
                            JSONArray jArray = Jobject.getJSONArray("Kandidaten");
                            JSONObject kandidaten_data;
                            Database db = new Database(getApplicationContext());
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
    }

    private void toNavigationActivity(){
        Intent i = new Intent (AuthActivity.this, NavigationActivity.class);
        startActivity(i);
    }
}
