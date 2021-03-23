package com.example.emergencycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends Activity implements OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    Button btnAmbulance;
    Button btnFire;
    Button btnPolice;
    CardView cvAmbulance;
    CardView cvBreakDown;
    CardView cvBrigade;
    CardView cvHospital;
    CardView cvMovers;
    CardView cvPolice;
    Editor editor;
    SharedPreferences sharedPreferences;
    TextView tvName;
    String username;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.sharedPreferences = getSharedPreferences("credentials", 0);
        this.editor = this.sharedPreferences.edit();
        this.username = this.sharedPreferences.getString("username", null);
        this.btnAmbulance = (Button) findViewById(R.id.btn_ambulance);
        this.btnFire = (Button) findViewById(R.id.btn_brigage);
        this.btnPolice = (Button) findViewById(R.id.btn_police);
        this.tvName = (TextView) findViewById(R.id.tvName);
        this.cvAmbulance = (CardView) findViewById(R.id.cardVidewAmbulance);
        this.cvBrigade = (CardView) findViewById(R.id.cardVidewFire);
        this.cvPolice = (CardView) findViewById(R.id.cardVidewPolice);
        this.cvHospital = (CardView) findViewById(R.id.cardVidewHospital);
        this.cvBreakDown = (CardView) findViewById(R.id.cardVidewBreakDown);
        this.cvMovers = (CardView) findViewById(R.id.cardVidewMovers);
        this.tvName.setText(this.username);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.editor.putString("username", null);
                MainActivity.this.editor.commit();
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                MainActivity.this.startActivity(homeIntent);
            }
        });
        this.btnAmbulance.setOnClickListener(this);
        this.btnFire.setOnClickListener(this);
        this.btnPolice.setOnClickListener(this);
        this.cvAmbulance.setOnClickListener(this);
        this.cvBrigade.setOnClickListener(this);
        this.cvPolice.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardVidewAmbulance /*2131230776*/:
                start_service(Integer.valueOf(0));
                return;
            case R.id.cardVidewFire /*2131230778*/:
                start_service(Integer.valueOf(1));
                return;
            case R.id.cardVidewPolice /*2131230781*/:
                start_service(Integer.valueOf(2));
                return;
            default:
                return;
        }
    }

    public void start_service(Integer service_value) {
        Intent serviceIntent = new Intent(this, ContactsList.class);
        serviceIntent.putExtra("service_value", service_value);
        startActivity(serviceIntent);
    }
}
