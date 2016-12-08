package de.schulzgette.thes_o_naise.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.BegruendungenTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.BiografieTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.PositionenTabFragment;
import de.schulzgette.thes_o_naise.KandidatenAnsichtFragments.WahlprogrammTabFragment;
import de.schulzgette.thes_o_naise.Models.KandidatenModel;

/**
 * Created by Jessica on 6.12.2016.
 */

public class MeinProfilAdapter extends FragmentStatePagerAdapter {
    public String KID2 = "KID_2";
    public String MODE1 = "NORMAL";
    KandidatenModel kandidat2;
    public MeinProfilAdapter(FragmentManager fm, String KID, String MODE, KandidatenModel kandidat) {
        super(fm);
        KID2 = KID;
        MODE1 = MODE;
        kandidat2= kandidat;
    }
    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return BiografieTabFragment.newInstance(MODE1, KID2, kandidat2);
            case 1: return PositionenTabFragment.newInstance(MODE1, KID2);
            case 2: return BegruendungenTabFragment.newInstance(MODE1, KID2);
            case 3: return WahlprogrammTabFragment.newInstance(MODE1, KID2);
            default: return BiografieTabFragment.newInstance(MODE1, KID2, kandidat2);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
