package de.schulzgette.thes_o_naise.WaehlerProfilFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.schulzgette.thes_o_naise.Models.ThesenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.adapter.ThesenItemAdapter;
import de.schulzgette.thes_o_naise.database.Database;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeineThesenTabFragment extends Fragment {
    View myView;
    ListView lv;
    ThesenItemAdapter listadapter;

    public MeineThesenTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView =inflater.inflate(R.layout.fragment_meine_thesen, container, false);

        Database db = new Database(getContext());
        ArrayList<ThesenModel> thesenModels = db.getArraylistMeineThesen();

        lv = (ListView) myView.findViewById(R.id.meinethesenliste);
        listadapter = new ThesenItemAdapter(thesenModels, this.getActivity(), "Benachrichtigung");
        lv.setAdapter(listadapter);

        return myView;
    }

    public static MeineThesenTabFragment newInstance() {
        MeineThesenTabFragment fragment = new MeineThesenTabFragment();
        return fragment;
    }
}
