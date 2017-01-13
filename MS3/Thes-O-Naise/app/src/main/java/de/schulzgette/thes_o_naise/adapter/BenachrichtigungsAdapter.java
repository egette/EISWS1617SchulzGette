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

import de.schulzgette.thes_o_naise.Models.BenachrichtigungModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 28.12.2016.
 */

public class BenachrichtigungsAdapter extends ArrayAdapter<BenachrichtigungModel> implements View.OnClickListener{

    private ArrayList<BenachrichtigungModel> dataSet;
    Context mContext;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        Button mehrButton;
        TextView txtbenachrichtigung;
        TextView txtusername;
    }

    public BenachrichtigungsAdapter(ArrayList<BenachrichtigungModel> data, Context context) {
        super(context, R.layout.row_item_these, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final BenachrichtigungModel benachrichtigungModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_benachrichtigung, parent, false);
            viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
            viewHolder.txtbenachrichtigung = (TextView) convertView.findViewById(R.id.benachrichtigungid);
            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.einethesebutton);

            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtThesentext.setText(benachrichtigungModel.getThesentext());
        viewHolder.txtbenachrichtigung.setText(benachrichtigungModel.getBenachrichtungstext());

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                intent.putExtra("TID", benachrichtigungModel.getTID());
                getContext().startActivity(intent);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
