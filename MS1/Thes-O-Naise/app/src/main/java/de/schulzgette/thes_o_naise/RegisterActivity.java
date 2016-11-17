package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.schulzgette.thes_o_naise.adapter.RegisterViewPagerAdapter;
import de.schulzgette.thes_o_naise.utils.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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


    }


}
