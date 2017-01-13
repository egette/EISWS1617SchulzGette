package de.schulzgette.thes_o_naise.WaehlerProfilFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.BenachrichtigungModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.adapter.BenachrichtigungsAdapter;
import de.schulzgette.thes_o_naise.database.Database;

/**
 * A simple {@link Fragment} subclass.
 */
public class AbonniertesTabFragment extends Fragment {
    View myView;
    ListView lv;
    BenachrichtigungsAdapter listadapter;

    public AbonniertesTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView =inflater.inflate(R.layout.fragment_meine_thesen, container, false);

        Database db = new Database(getContext());
        ArrayList<BenachrichtigungModel> benachrichtungsmodels = db.getArraylistAbonnierteThesen();

        lv = (ListView) myView.findViewById(R.id.meinethesenliste);
        listadapter = new BenachrichtigungsAdapter(benachrichtungsmodels, this.getActivity());
        lv.setAdapter(listadapter);

        return myView;
    }

    public static AbonniertesTabFragment newInstance() {
        AbonniertesTabFragment fragment = new AbonniertesTabFragment();
        return fragment;
    }
}
