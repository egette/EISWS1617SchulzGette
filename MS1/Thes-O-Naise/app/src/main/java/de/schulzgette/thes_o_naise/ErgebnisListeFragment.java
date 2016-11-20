package de.schulzgette.thes_o_naise;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ErgebnisListeFragment extends Fragment {
    View myView;

    public ErgebnisListeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_ergebnis_liste, container, false);
        return myView;
    }

}
