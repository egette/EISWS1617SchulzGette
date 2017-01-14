package de.schulzgette.thes_o_naise.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.NavigationActivity;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import de.schulzgette.thes_o_naise.utils.TheseToLokalDatabase;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 02.11.2016.
 */

public class ThesenItemAdapter extends ArrayAdapter<ThesenModel> implements View.OnClickListener{

    private ArrayList<ThesenModel> dataSet;
    Context mContext;
    String MODE;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        RadioButton proButton;
        RadioButton neutralButton;
        RadioButton contraButton;
        Button mehrButton;
        ToggleButton abonnieren;
        ImageButton likeButton;
        TextView likesTxt;
    }

    public ThesenItemAdapter(ArrayList<ThesenModel> data, Context context, String MODE) {
        super(context, R.layout.row_item_these, data);
        this.dataSet = data;
        this.mContext=context;
        this.MODE = MODE;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ThesenModel thesenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_these, parent, false);
            viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
            viewHolder.proButton = (RadioButton) convertView.findViewById(R.id.pro_button);
            viewHolder.neutralButton = (RadioButton) convertView.findViewById(R.id.neutral_button);
            viewHolder.contraButton = (RadioButton) convertView.findViewById(R.id.contra_button);

            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.einethesebutton);
            viewHolder.abonnieren = (ToggleButton) convertView.findViewById(R.id.abonnierenbutton);
            viewHolder.likeButton = (ImageButton) convertView.findViewById(R.id.like);
            viewHolder.likesTxt = (TextView) convertView.findViewById(R.id.likestext);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            if(db.getLikeThese(thesenModel.getTID())==1){
                viewHolder.likeButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            }else{
                viewHolder.likeButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            }
        }


//        String test = thesenModel.getThesentext()+ "    " +thesenModel.getTID();
        viewHolder.txtThesentext.setText(thesenModel.getThesentext());
        typ = sharedPreferences.getString("typ", "");
//        Log.d("Thesen", thesenModel.getTID() + " und POS:" + thesenModel.getPosition());
        if (thesenModel.getPosition().equals("PRO")) {
            viewHolder.proButton.setChecked(true);
            viewHolder.neutralButton.setChecked(false);
            viewHolder.contraButton.setChecked(false);
        }else if (thesenModel.getPosition().equals("NEUTRAL")) {
            viewHolder.proButton.setChecked(false);
            viewHolder.neutralButton.setChecked(true);
            viewHolder.contraButton.setChecked(false);
        }else if (thesenModel.getPosition().equals("CONTRA")) {
            viewHolder.proButton.setChecked(false);
            viewHolder.neutralButton.setChecked(false);
            viewHolder.contraButton.setChecked(true);
        }else if (thesenModel.getPosition().equals("keine")) {
            viewHolder.proButton.setChecked(false);
            viewHolder.neutralButton.setChecked(false);
            viewHolder.contraButton.setChecked(false);
        }

        viewHolder.proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("PRO", thesenModel.getTID());
                db.insertposition("PRO", thesenModel.getTID());
                thesenModel.setPosition("PRO");
                Toast.makeText(getContext(), "Pro Button von These " + thesenModel.getTID(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("NEUTRAL", thesenModel.getTID());
                db.insertposition("NEUTRAL", thesenModel.getTID());
                thesenModel.setPosition("NEUTRAL");
                Toast.makeText(getContext(), "Neutral", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.contraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("CONTRA", thesenModel.getTID());
                db.insertposition("CONTRA", thesenModel.getTID());
                thesenModel.setPosition("CONTRA");
                Toast.makeText(getContext(), "Contra", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                intent.putExtra("TID", thesenModel.getTID());
                getContext().startActivity(intent);

            }
        });

        viewHolder.abonnieren.setChecked(thesenModel.getAbo());
        viewHolder.abonnieren.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(thesenModel.getAbo()){
                    db.thesenDeAbonnieren(thesenModel.getTID());
                    thesenModel.setAbo(false);
                }else {
                    db.thesenAbonnieren(thesenModel.getTID());
                    thesenModel.setAbo(true);
                }
            }
        });

        String likes = thesenModel.getLikes().toString();
        viewHolder.likesTxt.setText(likes);


        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer likeInt = db.getLikeThese(thesenModel.getTID());
                if(likeInt==1){
                    db.insertLikeThese(thesenModel.getTID(), 0);
                    viewHolder.likeButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));

                    thesenModel.setLikes(thesenModel.getLikes()-1);
                    String likes2 = thesenModel.getLikes().toString();
                    viewHolder.likesTxt.setText(likes2);

                    likeTheseToServer(thesenModel.getTID(), -1);
                }
                if(likeInt==0){
                    db.insertLikeThese(thesenModel.getTID(), 1);
                    viewHolder.likeButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));

                    thesenModel.setLikes(thesenModel.getLikes()+1);
                    String likes2 = thesenModel.getLikes().toString();
                    viewHolder.likesTxt.setText(likes2);

                    likeTheseToServer(thesenModel.getTID(), 1);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }



    public void kandidatPosToServer (String position, String tid){

        String positionData = "";
        String uid = sharedPreferences.getString("UID", "");
        String token = sharedPreferences.getString("token", "");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("uid", uid);
            jsonObject.accumulate("tid", tid);
            jsonObject.accumulate("typ", "kandidat");
            jsonObject.accumulate("richtung", position);
            jsonObject.accumulate("token", token);
            positionData =  jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            HttpClient.PUT("thesen", positionData, getContext(), new Callback() {
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
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void likeTheseToServer(String tid, Integer like){
        String jsonData = "";
        String token = sharedPreferences.getString("token", "");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("tid", tid);
            jsonObject.accumulate("like", like);
            jsonObject.accumulate("token", token);
            jsonData =  jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            HttpClient.PUT("thesen/likes", jsonData, getContext(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
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
                        ((NavigationActivity) mContext).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getContext(), "Ihr Like wurde verarbeitet", Toast.LENGTH_SHORT).show();

                            }
                        });
                        response.body().close();
                    } else {
                        Log.d("Statuscode", String.valueOf(response.code()));
                        response.body().close();
                        ((NavigationActivity) mContext).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getContext(), "Ihr Like konnte nicht hinzugefügt werden", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}

