package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BegruendungModel;
import de.schulzgette.thes_o_naise.Models.KommentarModel;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.services.EventBus;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 15.11.2016.
 */

public class ThesenTabFragment extends Fragment implements EventBus.ThesenAnsichtListner {
    View myView;
    static final String pos = "pro";
    String tid;
    ArrayList<BegruendungModel> begruendungen;
    ExpandableListView elv;
    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;
    String position;
    EditText begruendungtext;

    public ThesenTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.thesentabfragment, container, false);
        position = this.getArguments().getString("pos");
        tid = this.getArguments().getString("TID");

        begruendungtext = (EditText) myView.findViewById(R.id.editbegruendung);
        begruendungtext.setHint("Begründung " + position);
        begruendungtext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout begruendungslayout = (LinearLayout) myView.findViewById(R.id.begruendungslayout);
                LinearLayout.LayoutParams mLay = (LinearLayout.LayoutParams) begruendungslayout.getLayoutParams();

                if(hasFocus){
                    mLay.weight = (float) 0.2;
                    begruendungtext.setBackgroundColor(getResources().getColor(R.color.edittextBackgroundLeight));
                    begruendungslayout.setLayoutParams(mLay);
                }else{
                    mLay.weight = (float) 0.08;
                    begruendungtext.setBackgroundColor(getResources().getColor(R.color.edittextBackgroundDark));
                    begruendungslayout.setLayoutParams(mLay);
                }
            }
        });

        ImageButton send = (ImageButton) myView.findViewById(R.id.begruendungsbutton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String begruendung  =  begruendungtext.getText().toString().trim();
                if(begruendung.isEmpty()){
                    Toast.makeText(getContext(), "Bitte eine Begründung eingeben", Toast.LENGTH_SHORT).show();
                }else{
                    sendBegruendungToServer(begruendung);
                }
            }
        });

        elv = (ExpandableListView) myView.findViewById(R.id.expandablelistviewthesen);

        ArrayList<BegruendungModel> begruendungen2 = getBegruendung(tid, position);

        loadHosts(begruendungen2);
        //elv.setAdapter(new BegruendungsAdapter());

        return myView;
    }

    public static ThesenTabFragment newInstance(String position, String TID) {

        ThesenTabFragment f = new ThesenTabFragment();
        Bundle b = new Bundle();
        b.putString("pos", position);
        b.putString("TID", TID);
        f.setArguments(b);

        return f;
    }

    public ArrayList<BegruendungModel> getBegruendung (String TID, String position) {
        Database db = new Database(getActivity());
        return db.getBegruendungenWithTIDandPosition(TID, position);
    }

    private void loadHosts(final ArrayList<BegruendungModel> neueBegruendungen)
    {
        if (neueBegruendungen == null)
            return;

        begruendungen = neueBegruendungen;

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");
        for (int i= 0; i < begruendungen.size() ; i++){
            BegruendungModel test = begruendungen.get(i);
            if(test.getUID().equals(UID))  begruendungtext.setHint("Ihre Begründung bearbeiten");
        }

        // Check for ExpandableListAdapter object
        if (elv.getExpandableListAdapter() == null)
        {
            //Create ExpandableListAdapter Object
            final BegruendungsAdapter mAdapter = new BegruendungsAdapter();

            // Set Adapter to ExpandableList Adapter
            elv.setAdapter(mAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            BegruendungsAdapter mAdapter = (BegruendungsAdapter) elv.getExpandableListAdapter();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFirebaseUpdate() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                ArrayList<BegruendungModel> neu = getBegruendung(tid, position);
                loadHosts(neu);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.register(this);
        onFirebaseUpdate();
    }

    @Override
    public void onStop() {
        EventBus.unregister(this);
        super.onStop();
    }

    public class BegruendungsAdapter extends BaseExpandableListAdapter {
        private LayoutInflater inflater;


        public BegruendungsAdapter()
        {
            // Create Layout Inflator
            inflater = LayoutInflater.from(getContext());
        }



        // This Function used to inflate parent rows view
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentView) {
            final BegruendungModel begruendungModel = begruendungen.get(groupPosition);

            // Inflate row_item_begruendung.xml file for parent rows
            convertView = inflater.inflate(R.layout.row_item_begruendung, parentView, false);

            ImageView indicatorView = (ImageView)convertView.findViewById(R.id.group_indicator);
            indicatorView.setVisibility( View.VISIBLE );
            indicatorView.setImageResource(isExpanded?R.drawable.ic_arrow_drop_up_black_24dp:R.drawable.ic_arrow_drop_down_black_24dp);

            TextView usertyp = (TextView) convertView.findViewById(R.id.usertyp);
            if(begruendungModel.getTyp().equals("w")) usertyp.setText("Wähler: ");

            // Get row_item_begruendung.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.usernamebegruendung)).setText(begruendungModel.getUsername());
            ((TextView) convertView.findViewById(R.id.begruendungstext)).setText(begruendungModel.getBegruendungstext());

            //LikeButton
            final ImageButton likebutton = (ImageButton) convertView.findViewById(R.id.like);
            final Database db = new Database(getActivity());
            Integer likeInt = db.getLikeBegruendung(tid, begruendungModel.getUID(), position);
            if(likeInt==1)likebutton.setBackgroundColor(getResources().getColor(R.color.colorAccent));


            likebutton.setFocusable(false);
            likebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer likeInt = db.getLikeBegruendung(tid, begruendungModel.getUID(), position);
                    if(likeInt==1){
                        db.insertLikeBegruendung(tid, begruendungModel.getUID(), position, 0);
                        likebutton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        likeBegruendungToServer(begruendungModel.getUID(), begruendungModel.getTyp(), -1);

                    }
                    if(likeInt==0){
                        db.insertLikeBegruendung(tid, begruendungModel.getUID(), position, 1);
                        likebutton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        likeBegruendungToServer(begruendungModel.getUID(), begruendungModel.getTyp(), 1);
                    }
                }
            });

            String likes = begruendungModel.getLikes().toString();
            ((TextView) convertView.findViewById(R.id.likestext)).setText(likes);
            String anzahlkommentare = begruendungModel.getAnzahlKommentare().toString();
            ((TextView) convertView.findViewById(R.id.anzahlkommentare)).setText(anzahlkommentare);

            if(getChildrenCount(groupPosition)==0 && isExpanded){

                LinearLayout kommentarlayout = (LinearLayout) convertView.findViewById(R.id.kommentarlayout);
                kommentarlayout.setVisibility( View.VISIBLE);
                final EditText kommentaredit = (EditText)convertView.findViewById(R.id.editkommentar);


               /* kommentaredit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    Integer height = kommentaredit.getHeight();
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                           //kommentaredit.setHeight(200);

                        }else{
                           // kommentaredit.setHeight(height);
                        }
                    }
                });*/

                ImageButton kommenarsenden = (ImageButton) convertView.findViewById(R.id.kommentarbutton);
                kommenarsenden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String kommentar = kommentaredit.getText().toString().trim();
                        String beguid = begruendungModel.getUID();
                        String typ = begruendungModel.getTyp();
                        sendKommentarToServer(kommentar, beguid, typ);
                    }
                });
            }

            return convertView;
        }


        // This Function used to inflate child rows view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parentView)
        {
            final BegruendungModel begruendungModel = begruendungen.get(groupPosition);
            final KommentarModel kommentarModel = begruendungModel.getKommentare().get(childPosition);

            // Inflate row_item_kommentar.xml file for child rows
            convertView = inflater.inflate(R.layout.row_item_kommentar, parentView, false);

            // Get row_item_kommentar.xml file elements and set values
            ((TextView) convertView.findViewById(R.id.usernamekommentar)).setText(kommentarModel.getUsername());
            ((TextView) convertView.findViewById(R.id.kommentartext)).setText(kommentarModel.getKommentartext());
//            ImageView image=(ImageView)convertView.findViewById(R.id.image);
//            image.setImageResource(
//                    getResources().getIdentifier(
//                            "com.androidexample.customexpandablelist:drawable/setting"+parent.getName(),null,null));

            if (isLastChild){
                convertView = inflater.inflate(R.layout.row_item_kommentar_last, parentView, false);
                ((TextView) convertView.findViewById(R.id.usernamekommentar)).setText(kommentarModel.getUsername());
                ((TextView) convertView.findViewById(R.id.kommentartext)).setText(kommentarModel.getKommentartext());
                final EditText kommentaredit = (EditText)convertView.findViewById(R.id.editkommentar);
                kommentaredit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {


                    }
                });

                ImageButton kommenarsenden = (ImageButton) convertView.findViewById(R.id.kommentarbutton);
                kommenarsenden.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String kommentar = kommentaredit.getText().toString().trim();
                        String beguid = begruendungModel.getUID();
                        String typ = begruendungModel.getTyp();
                        sendKommentarToServer(kommentar, beguid, typ);
                    }
                });

            }

            return convertView;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
            return begruendungen.get(groupPosition).getKommentare().get(childPosition);
        }

        //Call when child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            /****** When Child row clicked then this function call *******/

            //Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
            if( ChildClickStatus!=childPosition)
            {
                ChildClickStatus = childPosition;

//                Toast.makeText(getContext(), "Parent :"+groupPosition + " Child :"+childPosition ,
//                        Toast.LENGTH_LONG).show();
            }
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size=0;
            if(begruendungen.get(groupPosition).getKommentare()!=null)
                size = begruendungen.get(groupPosition).getKommentare().size();
            return size;
        }


        @Override
        public Object getGroup(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroup ");

            return begruendungen.get(groupPosition);
        }

        @Override
        public int getGroupCount()
        {
            return begruendungen.size();
        }

        //Call when parent row clicked
        @Override
        public long getGroupId(int groupPosition)
        {
            Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

            if(groupPosition==2 && ParentClickStatus!=groupPosition){

                //Alert to user
//                Toast.makeText(getContext(), "Parent :"+groupPosition ,
//                        Toast.LENGTH_LONG).show();
            }

            ParentClickStatus=groupPosition;
            if(ParentClickStatus==0)
                ParentClickStatus=-1;

            return groupPosition;
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }

        @Override
        public boolean isEmpty()
        {
            return ((begruendungen == null) || begruendungen.isEmpty());
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }
    }

    public void sendBegruendungToServer(String text){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String Typ = sharedPreferences.getString("typ", "");
        String UID = sharedPreferences.getString("UID", "");
        String username = sharedPreferences.getString("username", "");
        String positionUpperCase = position.toUpperCase();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("username", username);
            jsonObject.accumulate("typ", Typ);
            jsonObject.accumulate("richtung", positionUpperCase);
            jsonObject.accumulate("tid", tid);
            jsonObject.accumulate("uid", UID);
            jsonObject.accumulate("textdata", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json_data = jsonObject.toString();

        try {
            HttpClient.PUT("thesen", json_data, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Keine Verbindung zum Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        Database db = new Database(getContext());
                        String jsonData = response.body().string();
                        TheseToLokalDatabase.saveTheseInLokalDatabase(jsonData, db);

                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihre Begründung wurde hinzugefügt", Toast.LENGTH_SHORT).show();
                                ArrayList<BegruendungModel> neu = getBegruendung(tid, position);
                                loadHosts(neu);
                            }
                        });
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihre Begründung konnte nicht hinzugefügt werden", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendKommentarToServer(String kommentartext, String begruendungsid, String Typ){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");
        String username = sharedPreferences.getString("username", "");
        String positionUpperCase = position.toUpperCase();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("username", username);
            jsonObject.accumulate("typ", Typ);
            jsonObject.accumulate("richtung", positionUpperCase);
            jsonObject.accumulate("tid", tid);
            jsonObject.accumulate("uid", UID);
            jsonObject.accumulate("kommentar",  kommentartext);
            jsonObject.accumulate("beguid", begruendungsid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json_data = jsonObject.toString();

        try {
            HttpClient.PUT("thesen/kommentar", json_data, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Keine Verbindung zum Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        Database db = new Database(getContext());
                        String jsonData = response.body().string();
                        TheseToLokalDatabase.saveTheseInLokalDatabase(jsonData, db);

                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihr Kommentar wurde hinzugefügt", Toast.LENGTH_SHORT).show();
                                ArrayList<BegruendungModel> neu = getBegruendung(tid, position);
                                loadHosts(neu);
                            }
                        });
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihr Kommentar konnte nicht hinzugefügt werden", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void likeBegruendungToServer(String BID, String Typ, Integer like){
        String positionUpperCase = position.toUpperCase();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("typ", Typ);
            jsonObject.accumulate("richtung", positionUpperCase);
            jsonObject.accumulate("tid", tid);
            jsonObject.accumulate("like",  like);
            jsonObject.accumulate("beguid", BID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json_data = jsonObject.toString();

        try {
            HttpClient.PUT("thesen/begruendungen/likes", json_data, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), "Keine Verbindung zum Server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("Response", response.toString());
                        Database db = new Database(getContext());
                        String jsonData = response.body().string();
                        TheseToLokalDatabase.saveTheseInLokalDatabase(jsonData, db);


                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihr Like wurde verarbeitet", Toast.LENGTH_SHORT).show();
                                ArrayList<BegruendungModel> neu = getBegruendung(tid, position);
                                loadHosts(neu);
                            }
                        });
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        response.body().close();
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(), "Ihr Like konnte nicht hinzugefügt werden", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
