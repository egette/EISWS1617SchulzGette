package de.schulzgette.thes_o_naise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.json.JSONObject.wrap;


public class MatchingFragment extends Fragment {
    View myView;

    public MatchingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_matching, container, false);

        Button matching = (Button) myView.findViewById(R.id.matching_button);

        matching.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    positionWaehlerToServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return myView;
    }
    
    public void positionWaehlerToServer() throws JSONException {
        Database db = new Database(getContext());
        JSONObject mainObj = db.getallPositions();
        JSONArray Jarray = mainObj.getJSONArray("Positionen");
        for(int i = 0; i<Jarray.length(); i++){
            JSONObject object= Jarray.getJSONObject(i);
            String tid = object.getString("TID");
            String position = object.getString("POSITION");
            db.updatepositionwithServerdata(position, tid);
        }
        String positiondata = mainObj.toString();
        Log.d("Jobject:", positiondata);
        try {
            HttpClient.POST("matching", positiondata, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        Log.d("body:", response.body().toString());
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
