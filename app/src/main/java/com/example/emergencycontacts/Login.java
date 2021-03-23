package com.example.emergencycontacts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
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
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {
    Button btLogin;
    public ProgressDialog dialog;
    EditText etPassword;
    EditText etUsername;
    String password;
    TextView tvForgotPass;
    String username;

    public class BackgroundWorkerLogin extends AsyncTask<String, String, String> {
        public BackgroundWorkerLogin() {
        }

        public void onPreExecute() {
            Login.this.dialog.show();
        }

        public String doInBackground(String... params) {
            Log.d("We're doing alright", "doInBackground:*********************** ");
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts/loginReg.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode("yes", "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Login.this.username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(Login.this.password, "UTF-8"));
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
                Toast.makeText(Login.this, "Check your Internet connection and try again", Toast.LENGTH_LONG).show();
            } else if (s.equalsIgnoreCase("ok")) {
                Toast.makeText(Login.this, "Success", Toast.LENGTH_LONG).show();
                Editor editor = Login.this.getSharedPreferences("credentials", 0).edit();
                editor.putString("username", Login.this.username);
                editor.commit();
                Intent loginIntent = new Intent(Login.this, MainActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                Login.this.startActivity(loginIntent);
            } else {
                Toast.makeText(Login.this, "Invalid Username Or Password", Toast.LENGTH_LONG).show();
            }
            Login.this.dialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.login);
        this.dialog = new ProgressDialog(this);
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Please Wait...");
        this.btLogin = (Button) findViewById(R.id.login);
        this.etUsername = (EditText) findViewById(R.id.usernameLogin);
        this.etPassword = (EditText) findViewById(R.id.passwordLogin);
        this.tvForgotPass = (TextView) findViewById(R.id.tvForgotPassword);
        this.btLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Login.this.username = Login.this.etUsername.getText().toString();
                Login.this.password = Login.this.etPassword.getText().toString();
                if (Login.this.username.equalsIgnoreCase("") || Login.this.password.equalsIgnoreCase("")) {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_LONG).show();
                } else {
                    new BackgroundWorkerLogin().execute(new String[0]);
                }
            }
        });
        this.tvForgotPass.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Login.this.startActivity(new Intent(Login.this, RecoverPassword.class));
            }
        });
    }

    public void saveCredentialsLocaly(String fileName, String extractedText) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, 0);
            fileOutputStream.write(extractedText.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
