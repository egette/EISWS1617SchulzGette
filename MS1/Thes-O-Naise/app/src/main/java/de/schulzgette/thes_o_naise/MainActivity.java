package de.schulzgette.thes_o_naise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerbutton = (Button) findViewById(R.id.registeractivity);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        Button navbutton = (Button) findViewById(R.id.navbutton);
        navbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, NavigationActivity.class);
                startActivity(i);
            }
        });

        Button authbutton = (Button) findViewById(R.id.authentifizierenbutton);
        authbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });
    }
}
