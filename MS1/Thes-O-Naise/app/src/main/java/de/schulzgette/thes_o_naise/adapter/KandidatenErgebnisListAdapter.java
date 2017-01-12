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

import de.schulzgette.thes_o_naise.MeinProfilKandidatActivity;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Enrico on 25.11.2016.
 */

public class KandidatenErgebnisListAdapter extends ArrayAdapter<KandidatenModel> implements View.OnClickListener{

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
        TextView txtscore;
        TextView txtanzahlthesenpositionen;
        TextView txtanzahlPOSinsgesammt;
        Button mehrButton;
        TextView txtdurchschnitt;
    }

    public KandidatenErgebnisListAdapter(ArrayList<KandidatenModel> data, Context context) {
        super(context, R.layout.row_item_kandidat_ergebnis, data);
        this.dataSet = data;
        this.mContext=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final KandidatenModel kandidatenModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        KandidatenErgebnisListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        final Database db = new Database(getContext());
        final View result;

        if (convertView == null) {

            viewHolder = new KandidatenErgebnisListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_kandidat_ergebnis, parent, false);
            viewHolder.txtvorname = (TextView) convertView.findViewById(R.id.vornamekandidatergebnis);
            viewHolder.txtnachname = (TextView) convertView.findViewById(R.id.nachnamekandidatergebnis);
            viewHolder.txtpartei = (TextView) convertView.findViewById(R.id.parteikandidatergebnis);
            viewHolder.mehrButton = (Button) convertView.findViewById(R.id.einkandidatbutton);
            viewHolder.txtscore = (TextView) convertView.findViewById(R.id.score);
            viewHolder.txtanzahlthesenpositionen = (TextView) convertView.findViewById(R.id.anzahlpositionen);
            viewHolder.txtdurchschnitt = (TextView) convertView.findViewById(R.id.durchschnittpunte);
            viewHolder.txtanzahlPOSinsgesammt = (TextView) convertView.findViewById(R.id.anzahlpositioneninsgesammt);
            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (KandidatenErgebnisListAdapter.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtvorname.setText(kandidatenModel.getVorname());
        viewHolder.txtnachname.setText(kandidatenModel.getNachname());
        viewHolder.txtpartei.setText(kandidatenModel.getPartei());
        String ergebnisKategorie = sharedPreferences.getString("ergebniskategorie", "punkte_insgesamt");
        String Score = "000";
        String anzahlpositionen = "000";
        String durchschnittpunkte = "000";
        String anzahlinsgesammt = "000";
        //TODO MEHR KATEGORIEN ?
        if(ergebnisKategorie.equals("punkte_insgesamt")){
            Score = kandidatenModel.getPunkte_Insgesamt().toString() + " Punkte";
            anzahlinsgesammt = kandidatenModel.getAnzahlThesenPositionen().toString();
            anzahlpositionen = kandidatenModel.getVerarbeitePositionen(getContext(), Database.KandidatenTable.COLUMN_NAME_VERARBEITE_POS).toString();
            durchschnittpunkte = kandidatenModel.durchschnittlichePunkteProThesen(ergebnisKategorie, getContext()).toString();
        }else if(ergebnisKategorie.equals(Database.KandidatenTable.COLUMN_NAME_PUNKTE_LOKAL)){
            Score = kandidatenModel.getPunkte_Lokal().toString() + " Punkte";
            anzahlinsgesammt = kandidatenModel.getAnzahlPositionenZuThesenMitKategorie("Lokal").toString();
            anzahlpositionen = kandidatenModel.getVerarbeitePositionen(getContext(), Database.KandidatenTable.COLUMN_NAME_ANZAHLLOKAL_POS).toString();
            durchschnittpunkte = kandidatenModel.durchschnittlichePunkteProThesen("Lokal",  getContext()).toString();
        }else if(ergebnisKategorie.equals(Database.KandidatenTable.COLUMN_NAME_PUNKTE_UMWELT)){
            Score = kandidatenModel.getPunkte_Umwelt().toString() + " Punkte";
            anzahlinsgesammt = kandidatenModel.getAnzahlPositionenZuThesenMitKategorie("Umwelt").toString();
            anzahlpositionen = kandidatenModel.getVerarbeitePositionen(getContext(), Database.KandidatenTable.COLUMN_NAME_ANZAHLUMWELT_POS).toString();
            durchschnittpunkte = kandidatenModel.durchschnittlichePunkteProThesen("Umwelt", getContext()).toString();
        }else if(ergebnisKategorie.equals(Database.KandidatenTable.COLUMN_NAME_PUNKTE_AP)){
            Score = kandidatenModel.getPunkte_AP().toString() + " Punkte";
            anzahlinsgesammt = kandidatenModel.getAnzahlPositionenZuThesenMitKategorie("Aussenpolitik").toString();
            anzahlpositionen = kandidatenModel.getVerarbeitePositionen(getContext(), Database.KandidatenTable.COLUMN_NAME_ANZAHLAP_POS).toString();
            durchschnittpunkte = kandidatenModel.durchschnittlichePunkteProThesen("Aussenpolitik", getContext()).toString();
        }else if(ergebnisKategorie.equals(Database.KandidatenTable.COLUMN_NAME_PUNKTE_SATIRE)){
            Score = kandidatenModel.getPunkte_Satire().toString() + " Punkte";
            anzahlinsgesammt = kandidatenModel.getAnzahlPositionenZuThesenMitKategorie("Satire").toString();
            anzahlpositionen = kandidatenModel.getVerarbeitePositionen(getContext(), Database.KandidatenTable.COLUMN_NAME_ANZAHLSATIRE_POS).toString();
            durchschnittpunkte = kandidatenModel.durchschnittlichePunkteProThesen("Satire", getContext()).toString();
        }

        viewHolder.txtscore.setText(Score);
        viewHolder.txtanzahlthesenpositionen.setText(anzahlpositionen);
        viewHolder.txtdurchschnitt.setText(durchschnittpunkte);
        viewHolder.txtanzahlPOSinsgesammt.setText(anzahlinsgesammt);

        viewHolder.mehrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(getContext(), MeinProfilKandidatActivity.class);
                intent.putExtra("KID", kandidatenModel.getKid());
                intent.putExtra("MODE", "NORMAL");
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}

