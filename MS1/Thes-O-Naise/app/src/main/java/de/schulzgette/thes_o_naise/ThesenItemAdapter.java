package de.schulzgette.thes_o_naise;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import de.schulzgette.thes_o_naise.database.Database;

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
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ThesenModel thesenModel =(ThesenModel)object;
        Log.d("Thesen Item clicked", thesenModel.getTID());
//        switch (v.getId())
//        {
//           case R.id.thesentext_item:
//                Snackbar.make(v, "TID " +thesenModel.getTID(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//               break;
//        }

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ThesenModel thesenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
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

        viewHolder.proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Pro Button clicked von These " +thesenModel.getTID(), Toast.LENGTH_SHORT).show();
                db.insertposition("Pro", thesenModel.getTID());
            }
        });

        viewHolder.neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Neutral Butten clicked", Toast.LENGTH_SHORT).show();
                db.insertposition("Neutral", thesenModel.getTID());
            }
        });

        viewHolder.contraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Contra Butten clicked", Toast.LENGTH_SHORT).show();
                db.insertposition("Contra", thesenModel.getTID());
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
}

