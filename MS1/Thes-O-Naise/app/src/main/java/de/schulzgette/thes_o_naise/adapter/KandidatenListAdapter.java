package de.schulzgette.thes_o_naise.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.KandidatAnsichtActivity;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 19.11.2016.
 */

public class KandidatenListAdapter extends ArrayAdapter<KandidatenModel> implements View.OnClickListener{

    private ArrayList<KandidatenModel> dataSet;
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
        Button mehrButton;
    }

    public KandidatenListAdapter(ArrayList<KandidatenModel> data, Context context) {
        super(context, R.layout.row_item_kandidat, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final KandidatenModel kandidatenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        KandidatenListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;

        if (convertView == null) {

            viewHolder = new KandidatenListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_kandidat, parent, false);
            viewHolder.txtvorname = (TextView) convertView.findViewById(R.id.vornamekandidat);
            viewHolder.txtnachname = (TextView) convertView.findViewById(R.id.nachnamekandidat);
            viewHolder.txtpartei = (TextView) convertView.findViewById(R.id.parteikandidat);
            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.einkandidatbutton);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (KandidatenListAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtvorname.setText(kandidatenModel.getVorname());
        viewHolder.txtnachname.setText(kandidatenModel.getNachname());
        viewHolder.txtpartei.setText(kandidatenModel.getPartei());

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
               Intent intent = new Intent(getContext(), KandidatAnsichtActivity.class);
               intent.putExtra("KID", kandidatenModel.getKid());
               getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}

