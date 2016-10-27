package de.schulzgette.thes_o_naise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://10.0.2.2:3000/";
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String thesentext;
    String kategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        spinner = (Spinner) findViewById(R.id.spinner2);
        adapter = ArrayAdapter.createFromResource(this, R.array.kategorien, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+ " wurde ausgewählt", Toast.LENGTH_SHORT).show();
                kategorie = (String) parent.getItemAtPosition(position);
                Log.d("ausgewählte Kategorie:", kategorie);
            }
        });
        EditText thesentextid =  (EditText) findViewById(R.id.thesentextid);
        thesentext = thesentextid.getText().toString();

        Button theseabschickenbutton = (Button) findViewById(R.id.theseabschickenbutton);
        theseabschickenbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button  clicked", "Mal sehen");

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("thesentext", thesentext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.accumulate("kategorie", kategorie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            String jsondata =  jsonObject.toString();

            }
        });


    }

    private void publishThese(String Thesendata) {

        try {
            HttpClient.POST(BASE_URL + "thesen", Thesendata, new Callback() {
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
