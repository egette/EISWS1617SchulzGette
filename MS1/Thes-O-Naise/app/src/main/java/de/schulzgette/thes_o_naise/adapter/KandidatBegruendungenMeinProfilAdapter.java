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

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.Models.BegrundungenMeinProfilModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Enrico on 09.12.2016.
 */

public class KandidatBegruendungenMeinProfilAdapter extends ArrayAdapter<BegrundungenMeinProfilModel> {

    private ArrayList<BegrundungenMeinProfilModel> dataSet;
    Context mContext;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";
    String MODE;


    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        TextView txtBegruendungKandidat;
        TextView txtBegrundunganfang;
        TextView txtLikes;
        Button mehrButton;
    }

    public KandidatBegruendungenMeinProfilAdapter(ArrayList<BegrundungenMeinProfilModel> data, Context context, String MODE) {
        super(context, R.layout.row_item_begruendungen_kandidat, data);
        this.dataSet = data;
        this.mContext=context;
        this.MODE= MODE;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final BegrundungenMeinProfilModel begrundungenMeinProfilModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        KandidatBegruendungenMeinProfilAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if(MODE.equals("MEINPROFIL")){
            if (convertView == null) {

                viewHolder = new KandidatBegruendungenMeinProfilAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_item_begruendungen_kandidat, parent, false);
                viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
                viewHolder.txtBegruendungKandidat = (TextView) convertView.findViewById(R.id.begruendungkandidat);
                viewHolder.txtBegrundunganfang = (TextView) convertView.findViewById(R.id.begruendunganfangtext);
                viewHolder.txtLikes = (TextView) convertView.findViewById(R.id.likesbegruendung);
                viewHolder.mehrButton = (Button) convertView.findViewById(R.id.zurthesebutton);


                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (KandidatBegruendungenMeinProfilAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtThesentext.setText(begrundungenMeinProfilModel.getThesentext());
            viewHolder.txtBegruendungKandidat.setText(begrundungenMeinProfilModel.getBegruendungstext());
            String likes = begrundungenMeinProfilModel.getLikes().toString();
            viewHolder.txtLikes.setText(likes);
            String position2 = begrundungenMeinProfilModel.getRichtung();
            if(position2.equals("PRO"))viewHolder.txtBegrundunganfang.setText("Ihre Begründung PRO: ");
            if(position2.equals("NEUTRAL"))viewHolder.txtBegrundunganfang.setText("Ihre Begründung NEUTRAL: ");
            if(position2.equals("CONTRA"))viewHolder.txtBegrundunganfang.setText("Ihre Begründung CONTRA: ");

            viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                    intent.putExtra("TID", begrundungenMeinProfilModel.getTID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
            });

        }else {
            if (convertView == null) {

                viewHolder = new KandidatBegruendungenMeinProfilAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.row_item_begruendungen_kandidat, parent, false);
                viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
                viewHolder.txtBegruendungKandidat = (TextView) convertView.findViewById(R.id.begruendungkandidat);
                viewHolder.txtBegrundunganfang = (TextView) convertView.findViewById(R.id.begruendunganfangtext);
                viewHolder.txtLikes = (TextView) convertView.findViewById(R.id.likesbegruendung);
                viewHolder.mehrButton = (Button) convertView.findViewById(R.id.zurthesebutton);

                result = convertView;
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (KandidatBegruendungenMeinProfilAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtThesentext.setText(begrundungenMeinProfilModel.getThesentext());
            viewHolder.txtBegruendungKandidat.setText(begrundungenMeinProfilModel.getBegruendungstext());
            String likes = begrundungenMeinProfilModel.getLikes().toString();
            viewHolder.txtLikes.setText(likes);
            String position2 = begrundungenMeinProfilModel.getRichtung();
            if(position2.equals("PRO"))viewHolder.txtBegrundunganfang.setText("Begründung PRO: ");
            if(position2.equals("NEUTRAL"))viewHolder.txtBegrundunganfang.setText("Begründung NEUTRAL: ");
            if(position2.equals("CONTRA"))viewHolder.txtBegrundunganfang.setText("Begründung CONTRA: ");

            viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                    intent.putExtra("TID", begrundungenMeinProfilModel.getTID());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                }
            });
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
