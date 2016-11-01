package de.schulzgette.thes_o_naise;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:3000/";
    String username;
    String mail;
    String password;
    String wahlkreis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText user =  (EditText) findViewById(R.id.username);
        username =  user.getText().toString();
        EditText usermail =  (EditText) findViewById(R.id.usermail);
        mail =  usermail.getText().toString();
        EditText pw =  (EditText) findViewById(R.id.userpw);
        password =  pw.getText().toString();
        EditText userwahlkreis =  (EditText) findViewById(R.id.userwahlkreis);
        wahlkreis =  userwahlkreis.getText().toString();

        Button registerbutton = (Button) findViewById(R.id.registerbutton);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button  clicked", "Mal sehen");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("username", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.accumulate("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.accumulate("email", mail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.accumulate("wahlkreis", wahlkreis);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.accumulate("typ", "waehler");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String jsondata =  jsonObject.toString();

                registerUser(jsondata);

            }
        });
    }

    private void registerUser(String registerData) {

        try {
            HttpClient.POST(BASE_URL + "register", registerData, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    int statusCode = response.code();

                    if (response.isSuccessful()) {
                        //statusCode is 201
//                        switch (statusCode) {
//                            case 201:
                        Log.d("Response", response.toString());
                       // String token = response.header("set-cookie").toString();
                        //System.out.println(token);
                        //Authentication.setToken(token);
                        //showToast(getString(R.string.toast_registersuccessful));
                        //switchToProfile();
//                                break;
//                        }
                        response.body().close();
                        Intent i = new Intent (RegisterActivity.this, NavigationActivity.class);
                        startActivity(i);
                    } else {
                        //statusCode is 409
//                        switch (statusCode) {
//                            case 409:
                        Log.d("Statuscode", String.valueOf(response.code()));
                        //showToast(getString(R.string.toast_alreadyregistered));
                        // switchToLogin();
//                                break;
//                        }
                        response.body().close();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
