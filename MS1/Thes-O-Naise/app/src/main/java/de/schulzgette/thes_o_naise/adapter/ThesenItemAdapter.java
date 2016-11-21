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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
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
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        TextView txtPro;
        TextView txtNeutral;
        TextView txtContra;
        RadioButton proButton;
        RadioButton neutralButton;
        RadioButton contraButton;
        Button mehrButton;
    }

    public ThesenItemAdapter(ArrayList<ThesenModel> data, Context context) {
        super(context, R.layout.row_item_these, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ThesenModel thesenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_these, parent, false);
            viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
            viewHolder.proButton = (RadioButton) convertView.findViewById(R.id.pro_button);
            viewHolder.neutralButton = (RadioButton) convertView.findViewById(R.id.neutral_button);
            viewHolder.contraButton = (RadioButton) convertView.findViewById(R.id.contra_button);

            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.einethesebutton);
//            viewHolder.txtPro = (TextView) convertView.findViewById(R.id.pro_id);
//            viewHolder.txtNeutral = (TextView) convertView.findViewById(R.id.neutral_id);
//            viewHolder.txtContra = (TextView) convertView.findViewById(R.id.contra_id);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtThesentext.setText(thesenModel.getThesentext());
//        viewHolder.txtPro.setText(thesenModel.getPro().toString()); //TODO
//        viewHolder.txtNeutral.setText(thesenModel.getNeutral().toString());
//        viewHolder.txtContra.setText(thesenModel.getContra().toString());

        //User Position aus der Datenbank holen und den richtigen Radiobutton checken
        String userposition = db.getUserPositionWithTID(thesenModel.getTID());
        if(!userposition.isEmpty()) {
            if (userposition.equals("PRO")) {
                viewHolder.proButton.setChecked(true);
                viewHolder.neutralButton.setChecked(false);
                viewHolder.contraButton.setChecked(false);
            }
            if (userposition.equals("NEUTRAL")) {
                viewHolder.proButton.setChecked(false);
                viewHolder.neutralButton.setChecked(true);
                viewHolder.contraButton.setChecked(false);
            }
            if (userposition.equals("CONTRA")) {
                viewHolder.proButton.setChecked(false);
                viewHolder.neutralButton.setChecked(false);
                viewHolder.contraButton.setChecked(true);
            }
        }else{
            viewHolder.proButton.setChecked(false);
            viewHolder.neutralButton.setChecked(false);
            viewHolder.contraButton.setChecked(false);
        }
        typ =  sharedPreferences.getString("typ", "");

        viewHolder.proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("PRO", thesenModel.getTID());
                db.insertposition("PRO", thesenModel.getTID());
                Toast.makeText(getContext(), "Pro Button von These " +thesenModel.getTID(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("NEUTRAL", thesenModel.getTID());
                db.insertposition("NEUTRAL", thesenModel.getTID());
                Toast.makeText(getContext(), "Neutral", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.contraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typ.equals("kandidat")) kandidatPosToServer("CONTRA", thesenModel.getTID());
                db.insertposition("CONTRA", thesenModel.getTID());
                Toast.makeText(getContext(), "Contra", Toast.LENGTH_SHORT).show();
                db.getallPositions();
            }
        });

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                intent.putExtra("TID", thesenModel.getTID());
                getContext().startActivity(intent);

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
            HttpClient.PUT("thesen", positionData, new Callback() {
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

