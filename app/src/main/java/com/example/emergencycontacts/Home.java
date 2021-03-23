package com.example.emergencycontacts;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    InfoAdapter adapter;
    Button btLogin;
    Button btRegister;
    List<InfoModel> models;
    String username;
    ViewPager viewPager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.home);
        this.username = getSharedPreferences("credentials", 0).getString("username", null);
        if (this.username != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        btLogin = (Button) findViewById(R.id.loginPage);
        btRegister = (Button) findViewById(R.id.registerPage);
        models = new ArrayList();
        models.add(new InfoModel(R.drawable.flag, "Emergency contacts:", "The application contains nation wide contacts for emergency services like: \n\n -Ambulances\n - police\n - fire brigades and others..."));
        models.add(new InfoModel(R.drawable.contacts, "Contacts display:", "The contacts displayed keep changing depending on your location. They are displayed starting from the one closest to your current location. "));
        models.add(new InfoModel(R.drawable.rate1, "Reviews:", "Each service has a reviews page where users can give a feedback by commenting and giving a rating for the service received"));
        models.add(new InfoModel(R.drawable.place, "Maps:", "Each service has a Maps button. This button is linked to google maps whereby on clicking the button, google maps is opened with the location marked for easier navigation."));
        models.add(new InfoModel(R.drawable.call, "Calls:", "Each of the services contains a dropdown with their contacts. By clicking the dropdown, you can switch between the different contacts. Clicking on the call button launches the dial pad to make a call to the service."));
        adapter = new InfoAdapter(this.models, this);
        viewPager = (ViewPager) findViewById(R.id.infoViewPager);
        viewPager.setAdapter(this.adapter);
        viewPager.setPadding(90, 0, 90, 0);
        btLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        this.btRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}
