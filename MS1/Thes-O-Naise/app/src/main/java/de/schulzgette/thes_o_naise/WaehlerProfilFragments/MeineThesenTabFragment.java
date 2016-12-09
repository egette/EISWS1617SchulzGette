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
public class MeineThesenTabFragment extends Fragment {


    public MeineThesenTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meine_thesen, container, false);
    }

    public static MeineThesenTabFragment newInstance() {
        MeineThesenTabFragment fragment = new MeineThesenTabFragment();
        return fragment;
    }
}
