package de.schulzgette.thes_o_naise.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import de.schulzgette.thes_o_naise.ThesenTabFragment;

/**
 * Created by Jessica on 15.11.2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public String TID2 = "TID_2";
    public ViewPagerAdapter(FragmentManager fm, String TID) {
        super(fm);
        TID2 = TID;
    }
    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0: return ThesenTabFragment.newInstance("Pro", TID2);
            case 1: return ThesenTabFragment.newInstance("Neutral", TID2);
            case 2: return ThesenTabFragment.newInstance("Contra", TID2);
            default: return ThesenTabFragment.newInstance("Pro", TID2);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
