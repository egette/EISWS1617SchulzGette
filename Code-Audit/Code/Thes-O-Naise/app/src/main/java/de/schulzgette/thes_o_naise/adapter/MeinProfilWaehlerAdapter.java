package de.schulzgette.thes_o_naise.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.BegruendungenTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.BiografieTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.PositionenTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.WahlprogrammTabFragment;
import de.schulzgette.thes_o_naise.WaehlerProfilFragments.AbonniertesTabFragment;
import de.schulzgette.thes_o_naise.WaehlerProfilFragments.MeineThesenTabFragment;

/**
 * Created by Jessica on 6.12.2016.
 */

public class MeinProfilWaehlerAdapter extends FragmentStatePagerAdapter {
    public String UID2 = "KID_2";
    public MeinProfilWaehlerAdapter(FragmentManager fm, String UID) {
        super(fm);
       UID2 = UID;
    }
    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return MeineThesenTabFragment.newInstance();
            case 1: return PositionenTabFragment.newInstance("WAEHLER", UID2);
            case 2: return AbonniertesTabFragment.newInstance();
            default: return MeineThesenTabFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
