package de.schulzgette.thes_o_naise;

import android.content.Intent;
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

import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.services.EventBus;
import de.schulzgette.thes_o_naise.services.GetThesenFromAPI;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static de.schulzgette.thes_o_naise.R.id.spinner_kategorie;

public class getThesenFragment extends Fragment implements EventBus.IEventListner{
    private static final String BASE_URL = "http://10.0.3.2:3000/";
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    ThesenItemAdapter listadapter;
    View myView;
    String kategorie;
    ListView lv;
    Database db;
    ArrayList<ThesenModel> thesenModels = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_get_thesen, container, false);

        lv = (ListView) myView.findViewById(R.id.listviewthesen);
        listadapter =  new ThesenItemAdapter(thesenModels, this.getActivity()); //TODO thesenModels holen
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
                kategorie = (String) parent.getItemAtPosition(position);
                Log.d("ausgewählte Kategorie:", kategorie);

                thesenModels.clear();

                Intent intent = new Intent(getActivity(), GetThesenFromAPI.class);
                intent.putExtra("kategorie", kategorie);
                getActivity().startService(intent);
                thesenModels = db.getArraylistThesen(kategorie);
            }

        });

        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.register(this);
        onThesenUpdate();
    }

    @Override
    public void onStop() {
        EventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onThesenUpdate(){
        Log.d("listadapterupdate", "kommt");
        listadapter.notifyDataSetChanged();
    }




}