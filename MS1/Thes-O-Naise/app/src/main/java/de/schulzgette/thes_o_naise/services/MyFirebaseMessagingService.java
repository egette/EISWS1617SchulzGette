package de.schulzgette.thes_o_naise.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
    }

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().toString());
        Map<String, String> remoteMessageData = remoteMessage.getData();
        String message = remoteMessageData.get("message");
        Log.d(TAG, "Message: " + message);
        String typ = message.substring(0,1);
        Log.d("TYP Message ", typ);
        if(typ.equals("T")) updateThese(message);
        if(typ.equals("K")) updateKandidat(message);
    }

    public void updateKandidat(String kid){
        Database db = new Database(getBaseContext());
        TheseToLokalDatabase.updateKandidatData(kid, db, getApplicationContext());
    }

    public void updateThese(String tid){
        try {
            HttpClient.GET("thesen/"+ tid, getApplicationContext(), new Callback() {

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
                        TheseToLokalDatabase.saveTheseInLokalDatabase(jsonData, db);
                        SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
                        String ansicht = sharedPreferences.getString("ansicht", "");
                        // if(ansicht.equals(tid))
                        EventBus.fireFirebaseUpdate();
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
