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
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.adapter.ThesenItemAdapter;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.services.EventBus;
import de.schulzgette.thes_o_naise.services.GetThesenFromAPI;

import static de.schulzgette.thes_o_naise.R.id.spinner_kategorie;

public class getThesenFragment extends Fragment implements EventBus.IEventListner{
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    ThesenItemAdapter listadapter;
    View myView;
    String kategorie;
    ListView lv;
    Database db;
    ArrayList<ThesenModel> thesenModels = new ArrayList<>();
    Boolean neueThesen;
    Boolean beliebteThesen;
    Boolean unbeantworteteThesen;
    TextView keinethesen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_get_thesen, container, false);

        lv = (ListView) myView.findViewById(R.id.listviewthesen);
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

        ToggleButton neuethesenbutton = (ToggleButton) myView.findViewById(R.id.neuethesenbutton);
        ToggleButton beliebtethesenbutton = (ToggleButton) myView.findViewById(R.id.beliebtethesenbutton);
        ToggleButton unbeantwortetethesenbutton = (ToggleButton) myView.findViewById(R.id.unbeantwortetethesenbutton);
        neueThesen = false;
        unbeantworteteThesen = false;
        beliebteThesen = false;
        neuethesenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(neueThesen){
                    neueThesen = false;
                    sortiereThesen();
                }
                else{
                    neueThesen = true;
                    sortiereThesen();
                }
            }
        });
        beliebtethesenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(beliebteThesen){
                    beliebteThesen = false;
                    sortiereThesen();
                }else{
                    beliebteThesen = true;
                    sortiereThesen();
                }
            }
        });
        unbeantwortetethesenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unbeantworteteThesen){
                    unbeantworteteThesen = false;
                    sortiereThesen();
                }else{
                    unbeantworteteThesen = true;
                    sortiereThesen();
                }
            }
        });

        keinethesen = (TextView) myView.findViewById(R.id.keinethesentext);


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
        thesenModels = db.getArraylistThesen(kategorie, "time ASC", false);
        if(thesenModels != null) {

            listadapter = new ThesenItemAdapter(thesenModels, this.getActivity(), "NORMAL");
            lv.setAdapter(listadapter);


            Log.d("Thesenmodels aus Db", "nicht null");
            listadapter.notifyDataSetChanged();
            if(thesenModels.isEmpty()){
                keinethesen.setVisibility(View.VISIBLE);
            }else{
                keinethesen.setVisibility(View.GONE);
            }
        }
    }

    private void sortiereThesen(){
        db = new Database(getContext());
        if(unbeantworteteThesen && beliebteThesen && neueThesen){
            String order = "likes DESC, time DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, true);
            loadHosts(neuethesenModels);
        }else if(unbeantworteteThesen && beliebteThesen){
            String order = "likes DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, true);
            loadHosts(neuethesenModels);
        }else if(unbeantworteteThesen && neueThesen){
            String order = "time DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, true);
            loadHosts(neuethesenModels);
        }else if(beliebteThesen && neueThesen){
            String order = "likes DESC, time DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, false);
            loadHosts(neuethesenModels);
        }else if(unbeantworteteThesen){
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, "time ASC", true);
            loadHosts(neuethesenModels);
        }else if(beliebteThesen){
            String order = "likes DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, false);
            loadHosts(neuethesenModels);
        }else if(neueThesen){
            String order = "time DESC";
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, order, false);
            loadHosts(neuethesenModels);
        }else{
            ArrayList<ThesenModel> neuethesenModels = db.getArraylistThesen(kategorie, "time ASC", false);
            loadHosts(neuethesenModels);
        }
    }

    private void loadHosts(ArrayList<ThesenModel> neuethesenModels) {
        if(neuethesenModels != null) {
            thesenModels = neuethesenModels;
            Log.d("listadapter", "notidyDATASETCHANGED");
            listadapter = new ThesenItemAdapter(thesenModels, this.getActivity(), "NORMAL");
            lv.setAdapter(listadapter);
            listadapter.notifyDataSetChanged();
            listadapter.notifyDataSetChanged();
        }
    }

}