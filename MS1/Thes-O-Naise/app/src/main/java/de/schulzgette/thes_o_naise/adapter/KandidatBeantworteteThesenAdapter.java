package de.schulzgette.thes_o_naise.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BeantworteteThesenKandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.ThesenAnsichtActivity;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jessica on 20.11.2016.
 */

public class KandidatBeantworteteThesenAdapter extends ArrayAdapter<BeantworteteThesenKandidatenModel> implements View.OnClickListener{

    private ArrayList<BeantworteteThesenKandidatenModel> dataSet;
    Context mContext;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("einstellungen", MODE_PRIVATE);
    String typ = "";

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        TextView txtPositionKandidat;
        Button mehrButton;
    }

    public KandidatBeantworteteThesenAdapter(ArrayList<BeantworteteThesenKandidatenModel> data, Context context) {
        super(context, R.layout.row_item_beantwortete_these_kandidat, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final BeantworteteThesenKandidatenModel BeantworteteThesenKandidatenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        KandidatBeantworteteThesenAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;

        if (convertView == null) {

            viewHolder = new KandidatBeantworteteThesenAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_beantwortete_these_kandidat, parent, false);
            viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.beantworteter_thesentext_item);
            viewHolder.txtPositionKandidat = (TextView) convertView.findViewById(R.id.positionkandidat);
            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.zurthesebutton);


            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (KandidatBeantworteteThesenAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtThesentext.setText(BeantworteteThesenKandidatenModel.getThesentext());
        viewHolder.txtPositionKandidat.setText(BeantworteteThesenKandidatenModel.getPositionkandidat());
        String position2 = BeantworteteThesenKandidatenModel.getPositionkandidat();
        if(position2.equals("PRO")) viewHolder.txtPositionKandidat.setTextColor(Color.GREEN);
        if(position2.equals("NEUTRAL")) viewHolder.txtPositionKandidat.setTextColor(Color.DKGRAY);
        if(position2.equals("CONTRA")) viewHolder.txtPositionKandidat.setTextColor(Color.RED);

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(getContext(), ThesenAnsichtActivity.class);
                intent.putExtra("TID", BeantworteteThesenKandidatenModel.getTid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}
