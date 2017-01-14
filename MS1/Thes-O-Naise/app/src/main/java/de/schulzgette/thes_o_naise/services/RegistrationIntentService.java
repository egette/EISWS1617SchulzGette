package de.schulzgette.thes_o_naise.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 */
public class RegistrationIntentService extends IntentService{

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String uid = sharedPreferences.getString("UID", "");
        String wahlkreis = sharedPreferences.getString("wahlkreis", "");
        String register = intent.getStringExtra("register");
        FirebaseInstanceId  instanceID = FirebaseInstanceId.getInstance();
        String registrationId = instanceID.getToken();
        if(registrationId != null) Log.d("registrationId", registrationId);
        if(register.equals("true")) registerDeviceProcess(uid,wahlkreis,registrationId, token);
        if(register.equals("false")) deleteDevice(wahlkreis, registrationId, token);
    }

    private void registerDeviceProcess(String uid, String wahlkreis, String registrationId, String token){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("registrationId", registrationId);
            jsonObject.accumulate("uid", uid);
            jsonObject.accumulate("wahlkreis", wahlkreis);
            jsonObject.accumulate("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsondata = jsonObject.toString();
        try {
            HttpClient.POST("devices", jsondata, getApplicationContext(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Integer statusCode = response.code();
                    if (response.isSuccessful()) {
                        Log.d("Response", response.toString());

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

    private void deleteDevice(String wahlkreis, String registrationId, String token){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("registrationId", registrationId);
            jsonObject.accumulate("wahlkreis", wahlkreis);
            jsonObject.accumulate("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsondata = jsonObject.toString();
        try {
            HttpClient.DELETE("devices/"+registrationId, jsondata, getApplicationContext(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Integer statusCode = response.code();
                    if (response.isSuccessful()) {
                        Log.d("Response", response.toString());

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