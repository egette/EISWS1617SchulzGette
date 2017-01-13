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
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.MeinProfilKandidatActivity;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.Models.PrognosenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Enrico on 13.01.2017.
 */

public class PrognosenAdapter extends ArrayAdapter<PrognosenModel> implements View.OnClickListener{

    private ArrayList<PrognosenModel> dataSet;
    Context mContext;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtvorname;
        TextView txtnachname;
        TextView txtpartei;
        TextView platz1;
        TextView platz2;
        TextView platz3;
        TextView platz4;
        TextView platz5;
        TextView platz6;
        TextView platz7;
        TextView platz8;
        TextView platz9;
        TextView platz10;
    }

    public PrognosenAdapter(ArrayList<PrognosenModel> data, Context context) {
        super(context, R.layout.row_item_kandidat_prognose, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final PrognosenModel prognosenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        PrognosenAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;

        if (convertView == null) {

            viewHolder = new PrognosenAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_kandidat_prognose, parent, false);
            viewHolder.txtvorname = (TextView) convertView.findViewById(R.id.vornamekandidat);
            viewHolder.txtnachname = (TextView) convertView.findViewById(R.id.nachnamekandidat);
            viewHolder.txtpartei = (TextView) convertView.findViewById(R.id.parteikandidat);
            viewHolder.platz1 = (TextView) convertView.findViewById(R.id.platz1);
            viewHolder.platz2 = (TextView) convertView.findViewById(R.id.platz2);
            viewHolder.platz3 = (TextView) convertView.findViewById(R.id.platz3);
            viewHolder.platz4 = (TextView) convertView.findViewById(R.id.platz4);
            viewHolder.platz5 = (TextView) convertView.findViewById(R.id.platz5);
            viewHolder.platz6 = (TextView) convertView.findViewById(R.id.platz6);
            viewHolder.platz7 = (TextView) convertView.findViewById(R.id.platz7);
            viewHolder.platz8 = (TextView) convertView.findViewById(R.id.platz8);
            viewHolder.platz9 = (TextView) convertView.findViewById(R.id.platz9);
            viewHolder.platz10 = (TextView) convertView.findViewById(R.id.platz10);


            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (PrognosenAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtvorname.setText(prognosenModel.getKandidatenModel().getVorname());
        viewHolder.txtnachname.setText(prognosenModel.getKandidatenModel().getNachname());
        viewHolder.txtpartei.setText(prognosenModel.getKandidatenModel().getPartei());
        String txtplatz1 = prognosenModel.getPlatz1().toString();
        String txtplatz2 = prognosenModel.getPlatz2().toString();
        String txtplatz3 = prognosenModel.getPlatz3().toString();
        String txtplatz4 = prognosenModel.getPlatz4().toString();
        String txtplatz5 = prognosenModel.getPlatz5().toString();
        String txtplatz6 = prognosenModel.getPlatz6().toString();
        String txtplatz7 = prognosenModel.getPlatz7().toString();
        String txtplatz8 = prognosenModel.getPlatz8().toString();
        String txtplatz9 = prognosenModel.getPlatz9().toString();
        String txtplatz10 = prognosenModel.getPlatz10().toString();
        viewHolder.platz1.setText(txtplatz1);
        viewHolder.platz2.setText(txtplatz2);
        viewHolder.platz3.setText(txtplatz3);
        viewHolder.platz4.setText(txtplatz4);
        viewHolder.platz5.setText(txtplatz5);
        viewHolder.platz6.setText(txtplatz6);
        viewHolder.platz7.setText(txtplatz7);
        viewHolder.platz8.setText(txtplatz8);
        viewHolder.platz9.setText(txtplatz9);
        viewHolder.platz10.setText(txtplatz10);


        // Return the completed view to render on screen
        return convertView;
    }
}
