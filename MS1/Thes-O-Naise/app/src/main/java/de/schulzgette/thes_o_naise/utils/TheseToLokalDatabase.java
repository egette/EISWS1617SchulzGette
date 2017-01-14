package de.schulzgette.thes_o_naise.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.schulzgette.thes_o_naise.database.Database;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Enrico on 07.12.2016.
 */

public class TheseToLokalDatabase extends Application {

    public static void saveTheseInLokalDatabase(String jsonData, Database db){
        try {
            JSONObject these_data = new JSONObject(jsonData);
            String TID = (String) these_data.get("TID");
            String thesentext = (String) these_data.get("thesentext");
            Integer proINT = (Integer) these_data.get("Anzahl_Zustimmung");
            Integer neutralINT = (Integer) these_data.get("Anzahl_Neutral");
            Integer contraINT = (Integer) these_data.get("Anzahl_Ablehnung");
            JSONArray K_PRO = (JSONArray) these_data.get("K_PRO");
            JSONArray K_NEUTRAL = (JSONArray) these_data.get("K_NEUTRAL");
            JSONArray K_CONTRA = (JSONArray) these_data.get("K_CONTRA");
            JSONArray W_PRO = (JSONArray) these_data.get("W_PRO");
            JSONArray W_NEUTRAL = (JSONArray) these_data.get("W_NEUTRAL");
            JSONArray W_CONTRA = (JSONArray) these_data.get("W_CONTRA");
            JSONArray K_POSITION = (JSONArray) these_data.get("K_POSITION");
            String kategorie = (String) these_data.get("kategorie");
            String wahlkreis = (String) these_data.get("wahlkreis");
            Integer likesINT = (Integer) these_data.get("Likes");
            Long time = (Long) these_data.get("time");
            db.insertThese(TID, thesentext, kategorie, wahlkreis, likesINT, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA, W_PRO, W_NEUTRAL, W_CONTRA, K_POSITION, time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void updateKandidatData(String KID, final Database db, Context context){
        try {
            HttpClient.GET("kandidaten?KID="+KID, context, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Integer statusCode = response.code();
                    if (response.isSuccessful()) {
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
