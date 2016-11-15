package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ThesenAnsichtActivity extends AppCompatActivity {
    static final String ARG_TID = "TID";
    String tid;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thesen_ansicht);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout.Tab pro = tabLayout.newTab();
        final TabLayout.Tab neutral = tabLayout.newTab();
        final TabLayout.Tab contra = tabLayout.newTab();

        pro.setText("Pro");
        neutral.setText("Neutral");
        contra.setText("Contra");

        tabLayout.addTab(pro, 0);
        tabLayout.addTab(neutral, 1);
        tabLayout.addTab(contra, 2);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(bd != null)
        {
            tid = (String) bd.get("TID");
            updateThesenView(tid);
        }

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void updateThesenView(String TID) {
        TextView tidtext = (TextView) findViewById(R.id.einetid);
        tidtext.setText(TID);
        Log.d("TID_:", TID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putString(ARG_TID, tid);
    }
}
