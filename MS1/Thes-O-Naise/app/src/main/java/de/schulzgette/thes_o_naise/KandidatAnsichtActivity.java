package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.database.Database;

public class KandidatAnsichtActivity extends AppCompatActivity {

    static final String ARG_KID = "KID";
    String kid;
    KandidatenModel kandidat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kandidat_ansicht);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
        {
            kid = (String) bd.get("KID");
            updateKandidatView(kid);
        }

    }

    public void updateKandidatView(String KID) {
        Database db = new Database(getBaseContext());
        kandidat = db.getKandidat(KID);
        final TextView vorname = (TextView) findViewById(R.id.vornamekandidatansicht);
        if(kandidat != null){
            vorname.setText(kandidat.getVorname());
        }

    }

}
