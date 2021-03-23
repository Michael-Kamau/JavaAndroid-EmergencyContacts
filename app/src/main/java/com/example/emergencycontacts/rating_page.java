package com.example.emergencycontacts;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.p003v7.app.AppCompatActivity;
//import android.support.p003v7.widget.LinearLayoutManager;
//import android.support.p003v7.widget.RecyclerView;
//import android.support.p003v7.widget.RecyclerView.Adapter;
//import android.support.p003v7.widget.RecyclerView.LayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import  androidx.recyclerview.widget.RecyclerView;
import  androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class rating_page extends AppCompatActivity {
    Button btCancel_dialog;
    Button btSend_dialog;
    /* access modifiers changed from: private */
    public ProgressDialog dialog;
    Dialog dialog_rate;
    EditText etComment_dialog;
    /* access modifiers changed from: private */

    /* renamed from: id */
    public String f47id;
    /* access modifiers changed from: private */
    public Adapter mAdapter;
    /* access modifiers changed from: private */
    public LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    RatingBar rate_dialog;
    /* access modifiers changed from: private */
    public String serviceValue;
    private TextView tvHeader;
    /* access modifiers changed from: private */
    public String username;

    public class BackgroundWorker extends AsyncTask<String, Void, ArrayList<DataModel>> {
        public BackgroundWorker() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            rating_page.this.dialog.show();
        }

        /* access modifiers changed from: protected */
        public ArrayList<DataModel> doInBackground(String... params) {
            ArrayList<DataModel> dataModelList = new ArrayList<>();
            Log.d("We're doing alright", "doInBackground:*********************** ");
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts/fetchComments.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(URLEncoder.encode("service_value", "UTF-8") + "=" + URLEncoder.encode(rating_page.this.serviceValue, "UTF-8") + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(rating_page.this.f47id, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                String message = "";
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
                Log.d("++++++++&&&&&&&", message);
                JSONArray parentArray = new JSONArray(message);
                for (int i = 0; i < parentArray.length(); i++) {
                    DataModel dataModel = new DataModel();
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    dataModel.setName(finalObject.getString("firstName") + " " + finalObject.getString("lastName"));
                    dataModel.setComment(finalObject.getString("comment"));
                    dataModel.setRating(finalObject.getString("rating"));
                    dataModelList.add(dataModel);
                }
                return dataModelList;
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
        public void onPostExecute(ArrayList<DataModel> dataModelList) {
            if (dataModelList != null) {
                rating_page.this.mRecyclerView = (RecyclerView) rating_page.this.findViewById(R.id.recyclerView);
                rating_page.this.mLayoutManager = new LinearLayoutManager(rating_page.this.getApplicationContext());
                rating_page.this.mAdapter = new DataAdapter(dataModelList);
                rating_page.this.mRecyclerView.setLayoutManager(rating_page.this.mLayoutManager);
                rating_page.this.mRecyclerView.setAdapter(rating_page.this.mAdapter);
            } else {
                Toast.makeText(rating_page.this.getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
            }
            rating_page.this.dialog.dismiss();
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class BackgroundWorker2 extends AsyncTask<String, String, String> {
        public BackgroundWorker2() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            rating_page.this.dialog.show();
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... params) {
            rating_page.this.username = rating_page.this.getSharedPreferences("credentials", 0).getString("username", null);
            Log.d("We're doing alright", "doInBackground:*********************** ");
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts/postComment.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(rating_page.this.f47id, "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(rating_page.this.username, "UTF-8") + "&" + URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode("" + params[0], "UTF-8") + "&" + URLEncoder.encode("service_value", "UTF-8") + "=" + URLEncoder.encode(rating_page.this.serviceValue, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                String message = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        message = message + line;
                    } else {
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        Log.d("*******&&&&&&&", message);
                        return message;
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String s) {
            if (s == null) {
                Toast.makeText(rating_page.this, "Check your Internet connection and try again", Toast.LENGTH_LONG).show();
            } else if (s.equalsIgnoreCase("ok")) {
                Toast.makeText(rating_page.this, "Data posted successfully", Toast.LENGTH_LONG).show();
                BackgroundWorker bw = new BackgroundWorker();
                rating_page.this.dialog_rate.dismiss();
                bw.execute(new String[]{"login"});
                Log.d("{}{}}}}}}}}}", "onPostExecute: " + rating_page.this.serviceValue);
            } else {
                Log.d("{}{}}}}}}}}}", "onPostExecute: " + rating_page.this.serviceValue);
                Toast.makeText(rating_page.this, "Unexpected Error", Toast.LENGTH_LONG).show();
            }
            rating_page.this.dialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.recycler_view2);
        Bundle data = getIntent().getExtras();
        this.f47id = data.getString("id");
        this.serviceValue = data.getString("service_value");
        this.tvHeader = (TextView) findViewById(R.id.tvHeader);
        this.dialog = new ProgressDialog(this);
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Please Wait...");
        this.dialog_rate = new Dialog(this);
        this.dialog_rate.setTitle("Post a Review");
        this.dialog_rate.setContentView(R.layout.dialog_rate);
        this.rate_dialog = (RatingBar) this.dialog_rate.findViewById(R.id.rate);
        this.etComment_dialog = (EditText) this.dialog_rate.findViewById(R.id.etComment);
        this.btCancel_dialog = (Button) this.dialog_rate.findViewById(R.id.btCancel);
        this.btSend_dialog = (Button) this.dialog_rate.findViewById(R.id.btSend);
        ((FloatingActionButton) findViewById(R.id.fab2)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, (CharSequence) "Refreshing", Snackbar.LENGTH_LONG).setAction((CharSequence) "Action", (OnClickListener) null).show();
                rating_page.this.dialog_rate.show();
                rating_page.this.btCancel_dialog.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        rating_page.this.dialog_rate.dismiss();
                    }
                });
                rating_page.this.btSend_dialog.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (rating_page.this.rate_dialog.getRating() == 0.0f || rating_page.this.etComment_dialog.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(rating_page.this, "Please fill in the form", Toast.LENGTH_LONG).show();
                            return;
                        }
                        new BackgroundWorker2().execute(new String[]{rating_page.this.rate_dialog.getRating() + "", rating_page.this.etComment_dialog.getText().toString()});
                    }
                });
            }
        });
        this.tvHeader.setText("All Reviews");
        new BackgroundWorker().execute(new String[0]);
    }
}
