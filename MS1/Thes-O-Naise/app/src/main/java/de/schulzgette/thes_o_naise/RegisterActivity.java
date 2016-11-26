package de.schulzgette.thes_o_naise;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import de.schulzgette.thes_o_naise.adapter.RegisterViewPagerAdapter;

public class RegisterActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RegisterViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_register);
        viewPager = (ViewPager) findViewById(R.id.viewpager_register);
        adapter = new RegisterViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout.Tab wähler = tabLayout.newTab();
        final TabLayout.Tab kandidat = tabLayout.newTab();

        wähler.setText("Wähler");
        kandidat.setText("Kandidat");


        tabLayout.addTab(wähler, 0);
        tabLayout.addTab(kandidat, 1);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setSelectedTabIndicatorHeight(20);
    }


}
