package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.schulzgette.thes_o_naise.database.Database;

public class ThesenAnsichtActivity extends FragmentActivity {
    static final String ARG_TID = "TID";
    String tid;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    ThesenModel these;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thesen_ansicht);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if(bd != null)
        {
            tid = (String) bd.get("TID");
            updateThesenView(tid);
        }

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), tid);
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

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateThesenView(String TID) {
        Database db = new Database(getBaseContext());
        these = db.getTheseWithTID(TID);
        final TextView tidtext = (TextView) findViewById(R.id.einetid);
        if(these != null)  tidtext.setText(these.getThesentext());

        ImageButton hide = (ImageButton) findViewById(R.id.hidethesentext);
        hide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             tidtext.setVisibility((tidtext.getVisibility() == View.VISIBLE)
                     ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putString(ARG_TID, tid);
    }
}
