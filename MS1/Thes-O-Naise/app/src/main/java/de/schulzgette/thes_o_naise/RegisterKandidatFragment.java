package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Enrico on 17.11.2016.
 */

public class RegisterKandidatFragment extends Fragment {
    View myView;
    static final String typ = "kandidat";
    String username;
    String mail;
    String password;
    String wahlkreis;
    String partei;

    public RegisterKandidatFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_register_kandidat, container, false);

        Button registerbutton = (Button) myView.findViewById(R.id.registerbutton);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button  clicked", "Mal sehen");

                EditText user =  (EditText) myView.findViewById(R.id.username);
                username =  user.getText().toString();
                EditText usermail =  (EditText) myView.findViewById(R.id.usermail);
                mail =  usermail.getText().toString();
                EditText pw =  (EditText) myView.findViewById(R.id.userpw);
                password =  pw.getText().toString();
                EditText userwahlkreis =  (EditText) myView.findViewById(R.id.userwahlkreis);
                wahlkreis =  userwahlkreis.getText().toString();
                EditText partei2 =  (EditText) myView.findViewById(R.id.parteitext);
                partei =  partei2.getText().toString();


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("username", username);
                    jsonObject.accumulate("password", password);
                    jsonObject.accumulate("email", mail);
                    jsonObject.accumulate("wahlkreis", wahlkreis);
                    jsonObject.accumulate("typ", typ);
                    jsonObject.accumulate("partei", partei);

                    String jsondata =  jsonObject.toString();

                    registerUser(jsondata);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return myView;
    }

    public static RegisterKandidatFragment newInstance(String typ) {

        RegisterKandidatFragment f = new RegisterKandidatFragment();
        Bundle b = new Bundle();
        b.putString("typ", typ);
        f.setArguments(b);

        return f;
    }

    public void registerUser(String registerData) {

        try {
            HttpClient.POST("register", registerData, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        response.body().close();
                        toAuthActivity();
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


    private void toAuthActivity(){
        Intent i = new Intent (getActivity(), AuthActivity.class);
        startActivity(i);
    }
}

