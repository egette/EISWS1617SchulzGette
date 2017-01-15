package de.schulzgette.thes_o_naise;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import de.schulzgette.thes_o_naise.services.RegistrationIntentService;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    View myView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_settings, container, false);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("einstellungen", MODE_PRIVATE);
        String firebasecheck = sharedPreferences.getString("firebase", "");

        final ToggleButton firebase = (ToggleButton) myView.findViewById(R.id.firebaseid);
        if(firebasecheck.equals("true")){
            firebase.setChecked(true);
        }else{
            firebase.setChecked(false);
        }

        firebase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sharedPreferences.edit().putString("firebase", "true").apply();
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    intent.putExtra("register", "true");
                    getActivity().startService(intent);
                } else {
                    // The toggle is disabled
                    sharedPreferences.edit().putString("firebase", "false").apply();
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    intent.putExtra("register", "false");
                    getActivity().startService(intent);
                }

            }
        });

        return myView;
    }

}
