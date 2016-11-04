package de.schulzgette.thes_o_naise;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jessica on 02.11.2016.
 */

public class ThesenItemAdapter extends ArrayAdapter<ThesenModel> implements View.OnClickListener{

    private ArrayList<ThesenModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtThesentext;
        TextView txtPro;
        TextView txtNeutral;
        TextView txtContra;
    }

    public ThesenItemAdapter(ArrayList<ThesenModel> data, Context context) {
        super(context, R.layout.row_item_these, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ThesenModel thesenModel =(ThesenModel)object;
        Log.d("Thesen Item clicked", thesenModel.getTID());
        switch (v.getId())
        {
           case R.id.thesentext_item:
                Snackbar.make(v, "TID " +thesenModel.getTID(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
               break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ThesenModel thesenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_these, parent, false);
            viewHolder.txtThesentext = (TextView) convertView.findViewById(R.id.thesentext_item);
            viewHolder.txtPro = (TextView) convertView.findViewById(R.id.pro_id);
            viewHolder.txtNeutral = (TextView) convertView.findViewById(R.id.neutral_id);
            viewHolder.txtContra = (TextView) convertView.findViewById(R.id.contra_id);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtThesentext.setText(thesenModel.getThesentext());
        viewHolder.txtPro.setText(thesenModel.getPro().toString()); //TODO
        viewHolder.txtNeutral.setText(thesenModel.getNeutral().toString());
        viewHolder.txtContra.setText(thesenModel.getContra().toString());


        // Return the completed view to render on screen
        return convertView;
    }
}

