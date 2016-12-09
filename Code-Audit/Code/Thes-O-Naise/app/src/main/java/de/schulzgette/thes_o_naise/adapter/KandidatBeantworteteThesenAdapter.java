package de.schulzgette.thes_o_naise.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.database.Database;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 20.11.2016.
 */

public class KandidatBeantworteteThesenAdapter extends ArrayAdapter<BeantworteteThesenKandidatenModel> implements View.OnClickListener{

    private ArrayList<BeantworteteThesenKandidatenModel> dataSet;
    Context mContext;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";
    String MODE;

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        TextView txtPositionKandidat;
        TextView txtPositionUser;
        Button mehrButton;
        RadioButton proButton;
        RadioButton neutralButton;
        RadioButton contraButton;
    }

    public KandidatBeantworteteThesenAdapter(ArrayList<BeantworteteThesenKandidatenModel> data, Context context, String MODE) {
        super(context, R.layout.row_item_beantwortete_these_kandidat, data);
        this.dataSet = data;
        this.mContext=context;
        this.MODE= MODE;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final BeantworteteThesenKandidatenModel BeantworteteThesenKandidatenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        KandidatBeantworteteThesenAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;
        Log.d("MODUS1235", MODE);
        if(MODE.equals("MEINPROFIL") || MODE.equals("WAEHLER")){
            if (convertView == null) {

                viewHolder = new KandidatBeantworteteThesenAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_item_positionen_kandidat, parent, false);
                viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.beantworteter_thesentext_item);
                viewHolder.txtPositionKandidat = (TextView) convertView.findViewById(R.id.positionkandidat);
                viewHolder.mehrButton = (Button) convertView.findViewById(R.id.zurthesebutton);
                viewHolder.proButton =(RadioButton) convertView.findViewById(R.id.pro_button);
                viewHolder.neutralButton =(RadioButton) convertView.findViewById(R.id.neutral_button);
                viewHolder.contraButton =(RadioButton) convertView.findViewById(R.id.contra_button);

                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (KandidatBeantworteteThesenAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtThesentext.setText(BeantworteteThesenKandidatenModel.getThesentext());
            viewHolder.txtPositionKandidat.setText(BeantworteteThesenKandidatenModel.getPositionkandidat());
            String positionkandidat = BeantworteteThesenKandidatenModel.getPositionkandidat();
            if (positionkandidat.equals("PRO")) {
                viewHolder.txtPositionKandidat.setTextColor(Color.GREEN);
                viewHolder.proButton.setChecked(true);
                viewHolder.neutralButton.setChecked(false);
                viewHolder.contraButton.setChecked(false);
            }
            if (positionkandidat.equals("NEUTRAL")) {
                viewHolder.txtPositionKandidat.setTextColor(Color.DKGRAY);
                viewHolder.proButton.setChecked(false);
                viewHolder.neutralButton.setChecked(true);
                viewHolder.contraButton.setChecked(false);
            }
            if (positionkandidat.equals("CONTRA")) {
                viewHolder.proButton.setChecked(false);
                viewHolder.neutralButton.setChecked(false);
                viewHolder.contraButton.setChecked(true);
                viewHolder.txtPositionKandidat.setTextColor(Color.RED);
            }

            viewHolder.proButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (typ.equals("kandidat")) kandidatPosToServer("PRO", BeantworteteThesenKandidatenModel.getTid());
                    db.insertposition("PRO", BeantworteteThesenKandidatenModel.getTid());
                    Toast.makeText(getContext(), "Pro", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (typ.equals("kandidat")) kandidatPosToServer("NEUTRAL", BeantworteteThesenKandidatenModel.getTid());
                    db.insertposition("NEUTRAL", BeantworteteThesenKandidatenModel.getTid());
                    Toast.makeText(getContext(), "Neutral", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.contraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (typ.equals("kandidat")) kandidatPosToServer("CONTRA", BeantworteteThesenKandidatenModel.getTid());
                    db.insertposition("CONTRA", BeantworteteThesenKandidatenModel.getTid());
                    Toast.makeText(getContext(), "Contra", Toast.LENGTH_SHORT).show();
                }
            });


            viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                    intent.putExtra("TID", BeantworteteThesenKandidatenModel.getTid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
            });
        }else {
            if (convertView == null) {

                viewHolder = new KandidatBeantworteteThesenAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_item_beantwortete_these_kandidat, parent, false);
                viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.beantworteter_thesentext_item);
                viewHolder.txtPositionKandidat = (TextView) convertView.findViewById(R.id.positionkandidat);
                viewHolder.txtPositionUser = (TextView) convertView.findViewById(R.id.positionuser);
                viewHolder.mehrButton = (Button) convertView.findViewById(R.id.zurthesebutton);


                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (KandidatBeantworteteThesenAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtThesentext.setText(BeantworteteThesenKandidatenModel.getThesentext());
            viewHolder.txtPositionKandidat.setText(BeantworteteThesenKandidatenModel.getPositionkandidat());
            viewHolder.txtPositionUser.setText(BeantworteteThesenKandidatenModel.getUserposition());
            String positionkandidat = BeantworteteThesenKandidatenModel.getPositionkandidat();
            String positionuser = BeantworteteThesenKandidatenModel.getUserposition();
            if (positionkandidat.equals("PRO"))
                viewHolder.txtPositionKandidat.setTextColor(Color.GREEN);
            if (positionkandidat.equals("NEUTRAL"))
                viewHolder.txtPositionKandidat.setTextColor(Color.DKGRAY);
            if (positionkandidat.equals("CONTRA"))
                viewHolder.txtPositionKandidat.setTextColor(Color.RED);

            if (positionuser.equals("PRO")) viewHolder.txtPositionUser.setTextColor(Color.GREEN);
            if (positionuser.equals("NEUTRAL"))
                viewHolder.txtPositionUser.setTextColor(Color.DKGRAY);
            if (positionuser.equals("CONTRA")) viewHolder.txtPositionUser.setTextColor(Color.RED);

            viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                    intent.putExtra("TID", BeantworteteThesenKandidatenModel.getTid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
            });
        }
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
