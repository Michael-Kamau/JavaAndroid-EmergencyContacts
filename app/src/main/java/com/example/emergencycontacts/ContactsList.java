package com.example.emergencycontacts;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.p000v4.app.ActivityCompat;
//import android.support.p000v4.content.ContextCompat;
//import android.support.p003v7.app.AppCompatActivity;
//import android.support.p003v7.widget.LinearLayoutManager;
//import android.support.p003v7.widget.RecyclerView;
//import android.support.p003v7.widget.RecyclerView.Adapter;
//import android.support.p003v7.widget.RecyclerView.LayoutManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import  androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;


import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactsList extends AppCompatActivity implements LocationListener {
    public ProgressDialog dialog;
    public double latitude;
    private LocationManager locationManager;
    public double longitude;
    public Adapter mAdapter;
    public LayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;
    public int serviceValue;

    public class BackgroundWorker extends AsyncTask<String, Void, ArrayList<ContactsModel>> {
        public BackgroundWorker() {
        }

        public void onPreExecute() {
            ContactsList.this.dialog.show();
        }

        public ArrayList<ContactsModel> doInBackground(String... params) {
            ArrayList<ContactsModel> contactsModelList = new ArrayList<>();
            String str = params[0];
            Log.d("We're doing alright", "doInBackground:*********************** ");
            try {
                String host=getString(R.string.host);
                URL url = new URL(host+"/emergencyContacts/fetchJson.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("service_value", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(ContactsList.this.serviceValue)));
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                String message = "";
                Log.d("We're doing alright", "doInBackground 2:*********************** ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    message = message + line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("*******&&&&&&&", message);
                JSONArray jSONArray = new JSONArray(message);
                for (int i = 0; i < jSONArray.length(); i++) {
                    ContactsModel contactsModel = new ContactsModel();
                    JSONObject finalObject = jSONArray.getJSONObject(i);
                    String lat = finalObject.getString("latitude");
                    String lon = finalObject.getString("longitude");
                    contactsModel.setName(finalObject.getString("name"));
                    contactsModel.setTown(finalObject.getString("city") + " (" + finalObject.getString("town") + ")");
                    contactsModel.setDetails(finalObject.getString("details"));
                    contactsModel.setContact(finalObject.getString("contact"));
                    ArrayList<String> spinnerList = new ArrayList<>();
                    ArrayList arrayList = spinnerList;
                    arrayList.addAll(Arrays.asList(finalObject.getString("contact").split(",")));
                    contactsModel.setSpinnerList(spinnerList);
                    contactsModel.setLat(lat);
                    contactsModel.setLon(lon);
                    contactsModel.setId(finalObject.getString("id"));
                    contactsModel.setService(Integer.toString(ContactsList.this.serviceValue));
                    contactsModel.setHits(Integer.parseInt(finalObject.getString("hits")));
                    if (finalObject.getString("rating").equalsIgnoreCase("null")) {
                        contactsModel.setRating(0);
                    } else {
                        contactsModel.setRating(Float.parseFloat(finalObject.getString("rating")));
                    }
                    contactsModel.setDist(Double.valueOf(ContactsList.distance(Double.parseDouble(lat), ContactsList.this.latitude, Double.parseDouble(lon), ContactsList.this.longitude, 0.0d, 0.0d)).floatValue() / 1000.0f);
                    contactsModelList.add(contactsModel);
                    Collections.sort(contactsModelList, new Comparator<ContactsModel>() {
                        public int compare(ContactsModel first, ContactsModel second) {
                            return Float.valueOf(first.getDist()).compareTo(Float.valueOf(second.getDist()));
                        }
                    });
                }
                return contactsModelList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            } catch (JSONException e3) {
                e3.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(ArrayList<ContactsModel> contactsModelList) {
            if (contactsModelList != null) {
                ContactsList.this.mRecyclerView = (RecyclerView) ContactsList.this.findViewById(R.id.recyclerView);
                ContactsList.this.mLayoutManager = new LinearLayoutManager(ContactsList.this.getApplicationContext());
                ContactsList.this.mAdapter = new DetailsAdapter(contactsModelList);
                ContactsList.this.mRecyclerView.setLayoutManager(ContactsList.this.mLayoutManager);
                ContactsList.this.mRecyclerView.setAdapter(ContactsList.this.mAdapter);
            } else {
                Toast.makeText(ContactsList.this, "Check Your Internet Connection and try again", Toast.LENGTH_LONG).show();
            }
            ContactsList.this.dialog.dismiss();
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.recycler_view);
        this.dialog = new ProgressDialog(this);
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Please Wait...");
        this.serviceValue = getIntent().getExtras().getInt("service_value");
        ((FloatingActionButton) findViewById(R.id.fab2)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, (CharSequence) "Refreshing", Snackbar.LENGTH_LONG).setAction((CharSequence) "Action", (OnClickListener) null).show();
                ContactsList.this.checkLocationPermission();
            }
        });
        checkLocationPermission();
    }

    public void onLocationChanged(Location location) {
        this.longitude = 45.00;//location.getLongitude();
        this.latitude = 45.00;//location.getLatitude();
    }

    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    public void onProviderEnabled(String s) {
    }

    public void onProviderDisabled(String s) {
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = (Math.sin(latDistance / 2.0d) * Math.sin(latDistance / 2.0d)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2.0d) * Math.sin(lonDistance / 2.0d));
        return Math.sqrt(Math.pow(6371.0d * 2.0d * Math.atan2(Math.sqrt(a), Math.sqrt(1.0d - a)) * 1000.0d, 2.0d) + Math.pow(el1 - el2, 2.0d));
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION")) {
                new Builder(this).setTitle("Location Permission").setMessage("To give the location").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(ContactsList.this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
                    }
                }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 99);
            }
            return false;
        }
        getApplicationContext();
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (this.locationManager.isProviderEnabled("gps")) {
            LocationManager locationManager2 = this.locationManager;
            LocationManager locationManager3 = this.locationManager;
            onLocationChanged(locationManager2.getLastKnownLocation("network"));
            new BackgroundWorker().execute(new String[]{"login"});
        } else {
            showGPSDisabledAlertToUser();
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 99:
                if (grantResults.length > 0 && grantResults[0] == 0) {
                    if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    }
                    getApplicationContext();
                    this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (this.locationManager.isProviderEnabled("gps")) {
                        LocationManager locationManager2 = this.locationManager;
                        LocationManager locationManager3 = this.locationManager;
                        onLocationChanged(locationManager2.getLastKnownLocation("network"));
                        new BackgroundWorker().execute(new String[]{"login"});
                        return;
                    }
                    showGPSDisabledAlertToUser();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void showGPSDisabledAlertToUser() {
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?").setCancelable(false).setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ContactsList.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }
}
