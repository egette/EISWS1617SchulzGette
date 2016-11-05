package de.schulzgette.thes_o_naise.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.ThesenModel;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetThesenFromAPI extends Service {
    private static final String BASE_URL = "http://10.0.3.2:3000/";
    JSONArray jArray;


    public GetThesenFromAPI() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String kategorie = intent.getStringExtra("kategorie");
        try {
            makeThesenList(getThesen(kategorie));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void makeThesenList(JSONArray jArray) throws JSONException {
        ArrayList<ThesenModel> thesenModels = new ArrayList<>();
        JSONObject json_data;
        Database db = new Database(getApplicationContext());
        int done = 0;
        if (jArray != null) {

            for (int i = 0; i < jArray.length(); i++) {
                json_data = new JSONObject((String) jArray.get(i));
                String TID = (String) json_data.get("TID");
                String thesentext = (String) json_data.get("thesentext");
                String pro = (String) json_data.get("Anzahl_Zustimmung");
                Integer proINT = Integer.parseInt(pro);
                String neutral = (String) json_data.get("Anzahl_Neutral");
                Integer neutralINT = Integer.parseInt(neutral);
                String contra = (String) json_data.get("Anzahl_Ablehnung");
                Integer contraINT = Integer.parseInt(contra);
                JSONArray K_PRO = (JSONArray) json_data.get("K_PRO");
                JSONArray K_NEUTRAL = (JSONArray) json_data.get("K_NEUTRAL");
                JSONArray K_CONTRA = (JSONArray) json_data.get("K_CONTRA");
                String kategorie = (String) json_data.get("kategorie");
                String wahlkreis = (String) json_data.get("wahlkreis");
                String likes = (String) json_data.get("Likes");
                Integer likesINT = Integer.parseInt(likes);
                thesenModels.add(new ThesenModel(TID, thesentext, kategorie, wahlkreis, likesINT, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA ));
            }
             done = db.insertArrayListThesen(thesenModels);
        }


        Log.d("ThesenModels", "In der Datenbank");
        EventBus.fireThesenUpdate();
        stopSelf();
    }


    private  JSONArray getThesen (String kategorie) {

        try {
            HttpClient.GET(BASE_URL + "thesen"+ "?kategorie=" + kategorie,  new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());

                        String jsonData = response.body().string();
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            jArray = Jobject.getJSONArray("Thesen");
                            Log.d("THESEN ARRAY: ", jArray.toString());
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
        return jArray;
    }
}
