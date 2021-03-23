package com.example.emergencycontacts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.p003v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class RecoverPassword extends AppCompatActivity implements OnClickListener {
    Button btReset;
    Button btSend;
    public ProgressDialog dialog;
    EditText etCodeReset;
    EditText etConfPassReset;
    EditText etEmailSend;
    EditText etPasswordReset;
    EditText etUsernameReset;
    EditText etUsernameSend;

    public class BackgroundTask extends AsyncTask<String, String, String> {
        public BackgroundTask() {
        }


        public void onPreExecute() {
            super.onPreExecute();
            RecoverPassword.this.dialog.show();
        }

        public String doInBackground(String... strings) {
            String username = strings[0];
            String email = strings[1];
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts/index.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("submit", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                String message = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        message = message + line;
                    } else {
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        Log.d("CLICK%%%%%%%", "onClick:" + message);
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

        public void onPostExecute(String result) {
            RecoverPassword.this.dialog.dismiss();
            if (result == null) {
                Toast.makeText(RecoverPassword.this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase(" ok")) {
                Toast.makeText(RecoverPassword.this, "Check Your email for the reset code", Toast.LENGTH_LONG).show();
                RecoverPassword.this.finish();
                RecoverPassword.this.startActivity(RecoverPassword.this.getIntent());
            } else if (result.equalsIgnoreCase(" wrong")) {
                Toast.makeText(RecoverPassword.this, "Invalid Username or email", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RecoverPassword.this, "Invalid email", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class BackgroundTask2 extends AsyncTask<String, String, String> {
        public BackgroundTask2() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            RecoverPassword.this.dialog.show();
        }

        public String doInBackground(String... strings) {
            String username = strings[0];
            String code = strings[1];
            String password = strings[2];
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts /index.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("reset", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" + URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8"));
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputstream = httpURLConnection.getInputStream();
                String message = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        message = message + line;
                    } else {
                        bufferedReader.close();
                        inputstream.close();
                        httpURLConnection.disconnect();
                        return message;
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (ProtocolException e2) {
                e2.printStackTrace();
                return null;
            } catch (IOException e3) {
                e3.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String result) {
            RecoverPassword.this.dialog.dismiss();
            Log.d("CLICK%%%%%%%", "onClick:" + result);
            if (result == null) {
                Toast.makeText(RecoverPassword.this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase(" ok")) {
                Toast.makeText(RecoverPassword.this, "Successful. Login with your new Password", Toast.LENGTH_LONG).show();
                RecoverPassword.this.finish();
                RecoverPassword.this.startActivity(RecoverPassword.this.getIntent());
            } else if (result.equalsIgnoreCase(" wrong")) {
                Toast.makeText(RecoverPassword.this, "Invalid Username or reset code", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RecoverPassword.this, "Unexpected error. Sorry", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.recover_password);
        this.dialog = new ProgressDialog(this);
        this.dialog.setCancelable(false);
        this.dialog.setIndeterminate(false);
        this.dialog.setTitle("Please Wait");
        this.etUsernameSend = (EditText) findViewById(R.id.etUsernameSend);
        this.etEmailSend = (EditText) findViewById(R.id.etEmailSend);
        this.etUsernameReset = (EditText) findViewById(R.id.etUsernameReset);
        this.etCodeReset = (EditText) findViewById(R.id.etCodeReset);
        this.etPasswordReset = (EditText) findViewById(R.id.passwordReset);
        this.etConfPassReset = (EditText) findViewById(R.id.confirmPasswordReset);
        this.btSend = (Button) findViewById(R.id.btSendCode);
        this.btReset = (Button) findViewById(R.id.btReset);
        this.btSend.setOnClickListener(this);
        this.btReset.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btReset /*2131230761*/:
                if (this.etUsernameReset.getText().toString().equalsIgnoreCase("") || this.etCodeReset.getText().toString().equalsIgnoreCase("") || this.etPasswordReset.getText().toString().equalsIgnoreCase("") || this.etConfPassReset.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "all the fields are required", Toast.LENGTH_LONG).show();
                    return;
                } else if (!this.etPasswordReset.getText().toString().equalsIgnoreCase(this.etConfPassReset.getText().toString())) {
                    Toast.makeText(this, "The Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    new BackgroundTask2().execute(new String[]{this.etUsernameReset.getText().toString(), this.etCodeReset.getText().toString(), this.etPasswordReset.getText().toString()});
                    return;
                }
            case R.id.btSendCode /*2131230763*/:
                if (this.etUsernameSend.getText().toString().equalsIgnoreCase("") || this.etEmailSend.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Fill in your username and account email to receive the reset code ", Toast.LENGTH_LONG).show();
                    return;
                }
                new BackgroundTask().execute(new String[]{this.etUsernameSend.getText().toString(), this.etEmailSend.getText().toString()});
                return;
            default:
                return;
        }
    }
}
