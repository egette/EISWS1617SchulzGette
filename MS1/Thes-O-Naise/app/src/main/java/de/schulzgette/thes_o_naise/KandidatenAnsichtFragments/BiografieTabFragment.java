package de.schulzgette.thes_o_naise.KandidatenAnsichtFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.schulzgette.thes_o_naise.Models.KandidatenModel;
import de.schulzgette.thes_o_naise.R;
import de.schulzgette.thes_o_naise.database.Database;


public class BiografieTabFragment extends Fragment {

    private static final String ARG_KID = "kid";
    private static final String ARG_MODE = "MODE";
    private String kid;
    private String MODE;
    static  KandidatenModel kandidat;


    public BiografieTabFragment() {
        // Required empty public constructor
    }



    public static BiografieTabFragment newInstance(String MODE, String kid, KandidatenModel kandidat2) {
        BiografieTabFragment fragment = new BiografieTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODE, MODE);
        args.putString(ARG_KID, kid);
        fragment.setArguments(args);
        kandidat = kandidat2;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MODE = getArguments().getString(ARG_MODE);
            kid = getArguments().getString(ARG_KID);
        }
        Database db = new Database(getContext());
        kandidat = db.getKandidat(kid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biografie_tab, container, false);
    }




}
