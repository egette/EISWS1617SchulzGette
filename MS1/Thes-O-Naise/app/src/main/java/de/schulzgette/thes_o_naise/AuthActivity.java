package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AuthActivity extends AppCompatActivity {
    String username;
    String email;
    String passwort;

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


    private void toNavigationActivity(){
        Intent i = new Intent (AuthActivity.this, NavigationActivity.class);
        startActivity(i);
    }
}
