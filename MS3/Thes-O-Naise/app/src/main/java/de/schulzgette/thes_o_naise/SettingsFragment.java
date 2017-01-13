package de.schulzgette.thes_o_naise;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import de.schulzgette.thes_o_naise.services.RegistrationIntentService;


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
        final ToggleButton firebase = (ToggleButton) myView.findViewById(R.id.firebaseid);
        firebase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    intent.putExtra("register", "true");
                    getActivity().startService(intent);
                } else {
                    // The toggle is disabled
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    intent.putExtra("register", "false");
                    getActivity().startService(intent);
                }

            }
        });

        return myView;
    }

}
