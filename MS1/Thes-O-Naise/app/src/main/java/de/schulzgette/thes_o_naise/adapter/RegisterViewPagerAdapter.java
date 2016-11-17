package de.schulzgette.thes_o_naise.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.schulzgette.thes_o_naise.RegisterKandidatFragment;
import de.schulzgette.thes_o_naise.RegisterWeahlerFragment;

/**
 * Created by Enrico on 17.11.2016.
 */

public class RegisterViewPagerAdapter extends FragmentStatePagerAdapter {
    public RegisterViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return RegisterWeahlerFragment.newInstance("waehler");
            case 1: return RegisterKandidatFragment.newInstance("kandidat");
            default: return RegisterWeahlerFragment.newInstance("waehler");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
