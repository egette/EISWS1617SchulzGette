package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.adapter.ThesenItemAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.services.EventBus;
import de.schulzgette.thes_o_naise.services.GetThesenFromAPI;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_get_thesen, container, false);

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
                if(kategorie.equals("Lokale Thesen")) kategorie = "Lokal";
                Intent intent = new Intent(getActivity(), GetThesenFromAPI.class);
                intent.putExtra("kategorie", kategorie);
                getActivity().startService(intent);

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
        db = new Database(getContext());
        thesenModels = db.getArraylistThesen(kategorie);

        if(thesenModels != null) {
            lv = (ListView) myView.findViewById(R.id.listviewthesen);
            listadapter = new ThesenItemAdapter(thesenModels, this.getActivity(), "NORMAL");
            lv.setAdapter(listadapter);


            Log.d("Thesenmodels aus Db", "nicht null");
            listadapter.notifyDataSetChanged();
        }

    }

}