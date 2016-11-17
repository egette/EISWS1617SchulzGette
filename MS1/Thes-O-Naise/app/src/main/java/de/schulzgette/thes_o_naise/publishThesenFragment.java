package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.MODE_PRIVATE;
import static de.schulzgette.thes_o_naise.R.id.publishthesebutton;
import static de.schulzgette.thes_o_naise.R.id.spinner2;
import static de.schulzgette.thes_o_naise.R.id.thesentext;

public class publishThesenFragment extends Fragment{
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String thesentext2;
    String kategorie;
    View myView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_publish_thesen, container, false);

        spinner = (Spinner) myView.findViewById(spinner2);
        adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.kategorien, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategorie = (String) parent.getItemAtPosition(position);
                Log.d("ausgewählte Kategorie:", kategorie);
            }

        });

        Button theseabschickenbutton = (Button) myView.findViewById(publishthesebutton);
        theseabschickenbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Button  clicked", "Mal sehen");

                EditText thesentextid =  (EditText) myView.findViewById(thesentext);
                thesentext2 = thesentextid.getText().toString();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                String wahlkreis = sharedPreferences.getString("wahlkreis", "");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("thesentext", thesentext2);
                    jsonObject.accumulate("kategorie", kategorie);
                    jsonObject.accumulate("wahlkreis", wahlkreis);
                    jsonObject.accumulate("token", token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsondata =  jsonObject.toString();
                publishThese(jsondata);
                Toast.makeText(getContext(), "Ihre These wurde veröffentlicht", Toast.LENGTH_SHORT).show();

            }
        });

        return myView;
    }

    private void publishThese(String Thesendata) {

        try {
            HttpClient.POST("thesen", Thesendata, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


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
        }
    }
}