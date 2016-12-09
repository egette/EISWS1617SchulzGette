package de.schulzgette.thes_o_naise.WaehlerProfilFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.schulzgette.thes_o_naise.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AbonniertesTabFragment extends Fragment {


    public AbonniertesTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_abonniertes_tab, container, false);
    }

    public static AbonniertesTabFragment newInstance() {
        AbonniertesTabFragment fragment = new AbonniertesTabFragment();
        return fragment;
    }
}
