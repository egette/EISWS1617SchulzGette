package de.schulzgette.thes_o_naise.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetThesenFromAPI extends Service {
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
        getThesen(kategorie);
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



    private  void getThesen (String kategorie) {

        try {
            HttpClient.GET("thesen"+ "?kategorie=" + kategorie,  new Callback() {

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
                                    JSONArray W_PRO = (JSONArray) these_data.get("W_PRO");
                                    JSONArray W_NEUTRAL = (JSONArray) these_data.get("W_NEUTRAL");
                                    JSONArray W_CONTRA = (JSONArray) these_data.get("W_CONTRA");
                                    JSONArray K_POSITION = (JSONArray) these_data.get("K_POSITION");
                                    String kategorie = (String) these_data.get("kategorie");
                                    String wahlkreis = (String) these_data.get("wahlkreis");
                                    Integer likesINT = (Integer) these_data.get("Likes");
                                    db.insertThese(TID, thesentext, kategorie, wahlkreis, likesINT, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA, W_PRO, W_NEUTRAL, W_CONTRA, K_POSITION);
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
        EventBus.fireThesenUpdate();
        stopSelf();
    }
}
