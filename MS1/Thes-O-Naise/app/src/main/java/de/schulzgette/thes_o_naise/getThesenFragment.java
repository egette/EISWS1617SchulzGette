package de.schulzgette.thes_o_naise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static de.schulzgette.thes_o_naise.R.id.spinner_kategorie;

public class getThesenFragment extends Fragment{
    private static final String BASE_URL = "http://10.0.3.2:3000/";
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    ThesenItemAdapter listadapter;
    ArrayList<ThesenModel> thesenModels = new ArrayList<>();
    View myView;
    String kategorie;
    JSONArray jArray;
    ListView lv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_get_thesen, container, false);

        lv = (ListView) myView.findViewById(R.id.listviewthesen);
        listadapter =  new ThesenItemAdapter(thesenModels, this.getActivity()); //TODO
        lv.setAdapter(listadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ThesenModel thesenmodel= thesenModels.get(position);

                Snackbar.make(view, "TID :" + thesenmodel.getTID(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        spinner = (Spinner) myView.findViewById(spinner_kategorie);
        adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.kategorien, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //listadapter.notifyDataSetChanged();
                kategorie = (String) parent.getItemAtPosition(position);
                Log.d("ausgewählte Kategorie:", kategorie);
                thesenModels.clear();
                try {
                    makeThesenList(getThesen(kategorie));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listadapter.notifyDataSetChanged();

            }

        });

        return myView;
    }


   public void makeThesenList(JSONArray jArray) throws JSONException {


        JSONObject json_data;
        Log.d("MAKE THESEN LIST:  ", "?");
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
                thesenModels.add(new ThesenModel(TID, thesentext, proINT, neutralINT, contraINT, K_PRO, K_NEUTRAL, K_CONTRA ));
            }

        }

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