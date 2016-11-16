package de.schulzgette.thes_o_naise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.schulzgette.thes_o_naise.database.Database;

/**
 * Created by Jessica on 15.11.2016.
 */

public class ThesenTabFragment extends Fragment {
    View myView;
    static final String pos = "pro";
    String tid;

    public ThesenTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.thesentabfragment, container, false);
        String position = this.getArguments().getString("pos");
        tid = this.getArguments().getString("TID");
        EditText begruendungtext = (EditText) myView.findViewById(R.id.editbegruendung);
        begruendungtext.setHint("Begründung " + position);
        String begruendung  =  begruendungtext.getText().toString();
        return myView;
    }

    public static ThesenTabFragment newInstance(String position, String TID) {

        ThesenTabFragment f = new ThesenTabFragment();
        Bundle b = new Bundle();
        b.putString("pos", position);
        b.putString("TID", TID);
        f.setArguments(b);

        return f;
    }

    public void getBegruendung (String TID, String position) {
        Database db = new Database(getActivity());

    }
}
