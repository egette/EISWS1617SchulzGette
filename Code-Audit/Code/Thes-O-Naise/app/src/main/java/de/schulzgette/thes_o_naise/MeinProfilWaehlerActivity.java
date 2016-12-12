package de.schulzgette.thes_o_naise;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.schulzgette.thes_o_naise.adapter.MeinProfilWaehlerAdapter;

public class MeinProfilWaehlerActivity extends FragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MeinProfilWaehlerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mein_profil_waehler);
        SharedPreferences sharedPreferences = getSharedPreferences("einstellungen", MODE_PRIVATE);
        String UID = sharedPreferences.getString("UID", "");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new MeinProfilWaehlerAdapter(getSupportFragmentManager(), UID);
        viewPager.setAdapter(adapter);

        final TabLayout.Tab meineThesen = tabLayout.newTab();
        final TabLayout.Tab positionen = tabLayout.newTab();
        final TabLayout.Tab abonniert = tabLayout.newTab();

        meineThesen.setText("Meine Thesen");
        positionen.setText("Positionen");
        abonniert.setText("Abonniertes");

        tabLayout.addTab(meineThesen, 0);
        tabLayout.addTab(positionen, 1);
        tabLayout.addTab(abonniert, 2);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setSelectedTabIndicatorHeight(15);
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
        final RelativeLayout layoutinfotext = (RelativeLayout) findViewById(R.id.layoutinfotext);
        final ImageButton hide = (ImageButton) findViewById(R.id.hideinfotext);
        hide.setOnClickListener(new View.OnClickListener() {
            Boolean isExpanded = false;
            public void onClick(View v) {
                layoutinfotext.setVisibility((layoutinfotext.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);

                hide.setImageResource(isExpanded?R.drawable.ic_expand_less_black_24dp:R.drawable.ic_expand_more_black_24dp);
                if(isExpanded){
                    isExpanded = false;
                }else{
                    isExpanded = true;
                }
            }
        });

        String username = sharedPreferences.getString("username", "");
        TextView usernametxt = (TextView) findViewById(R.id.usernametext);
        usernametxt.setText(username);
        String wahlkreis = sharedPreferences.getString("wahlkreis", "");
        TextView wahlkreistxt = (TextView) findViewById(R.id.wahlkreisid);
        wahlkreistxt.setText(wahlkreis);

    }
}
