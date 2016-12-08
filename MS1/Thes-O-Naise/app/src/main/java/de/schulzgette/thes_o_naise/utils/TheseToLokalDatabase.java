package de.schulzgette.thes_o_naise.utils;

import android.app.Application;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.schulzgette.thes_o_naise.database.Database;

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
            db.insertThese(TID, thesentext, kategorie, wahlkreis, likesINT, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA, W_PRO, W_NEUTRAL, W_CONTRA, K_POSITION);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
