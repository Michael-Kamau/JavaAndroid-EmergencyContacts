package com.example.emergencycontacts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Patterns;
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
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
    Button btRegister;
    String confPassword;
    String contact;
    public ProgressDialog dialog;
    String email;
    EditText etConfPassword;
    EditText etContact;
    EditText etEmail;
    EditText etFirstName;
    EditText etLastName;
    EditText etPassword;
    EditText etUsername;
    String firstName;
    String lastName;
    String password;
    String username;

    public class BackgroundWorker2 extends AsyncTask<String, String, String> {
        public BackgroundWorker2() {
        }

        public void onPreExecute() {
            Register.this.dialog.show();
        }

        public String doInBackground(String... params) {
            try {
                String host=getString(R.string.host);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host+"/emergencyContacts/loginReg.php").openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(URLEncoder.encode("register", "UTF-8") + "=" + URLEncoder.encode("yes", "UTF-8") + "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(Register.this.firstName, "UTF-8") + "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(Register.this.lastName, "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Register.this.username, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(Register.this.password, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(Register.this.email, "UTF-8") + "&" + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(Register.this.contact, "UTF-8"));
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

        public void onPostExecute(String s) {
            if (s == null) {
                Toast.makeText(Register.this, "Check your Internet connection and try again", Toast.LENGTH_LONG).show();
            } else if (s.equalsIgnoreCase("ok")) {
                Toast.makeText(Register.this, "Success. ", Toast.LENGTH_LONG);
                Register.this.etFirstName.setText("");
                Register.this.etLastName.setText("");
                Register.this.etUsername.setText("");
                Register.this.etPassword.setText("");
                Register.this.etConfPassword.setText("");
                Register.this.etEmail.setText("");
                Register.this.etContact.setText("");
            } else if (s.equalsIgnoreCase("copy")) {
                Toast.makeText(Register.this, "Please pick a Different Username. ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Register.this, "Unexpected Error", Toast.LENGTH_LONG).show();
            }
            Register.this.dialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.register);
        this.dialog = new ProgressDialog(this);
        this.dialog.setIndeterminate(false);
        this.dialog.setCancelable(false);
        this.dialog.setMessage("Please Wait...");
        this.btRegister = (Button) findViewById(R.id.register);
        this.etFirstName = (EditText) findViewById(R.id.firstName);
        this.etLastName = (EditText) findViewById(R.id.lastName);
        this.etUsername = (EditText) findViewById(R.id.username);
        this.etPassword = (EditText) findViewById(R.id.password);
        this.etConfPassword = (EditText) findViewById(R.id.confirmPassword);
        this.etEmail = (EditText) findViewById(R.id.email);
        this.etContact = (EditText) findViewById(R.id.contact);
        this.btRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Register.this.firstName = Register.this.etFirstName.getText().toString();
                Register.this.lastName = Register.this.etLastName.getText().toString();
                Register.this.username = Register.this.etUsername.getText().toString();
                Register.this.password = Register.this.etPassword.getText().toString();
                Register.this.confPassword = Register.this.etConfPassword.getText().toString();
                Register.this.email = Register.this.etEmail.getText().toString();
                Register.this.contact = Register.this.etContact.getText().toString();
                if (Register.this.firstName.equalsIgnoreCase("") || Register.this.lastName.equalsIgnoreCase("") || Register.this.username.equalsIgnoreCase("") || Register.this.password.equalsIgnoreCase("") || Register.this.confPassword.equalsIgnoreCase("") || Register.this.email.equalsIgnoreCase("") || Register.this.contact.equalsIgnoreCase("")) {
                    Toast.makeText(Register.this, "All the Fields Are Required", Toast.LENGTH_LONG).show();
                } else if (!Register.this.password.equalsIgnoreCase(Register.this.confPassword)) {
                    Toast.makeText(Register.this, "The Passwords do Not Match", Toast.LENGTH_LONG).show();
                } else if (!Register.this.isEmailValid(Register.this.email)) {
                    Toast.makeText(Register.this, "The Email is Invalid", Toast.LENGTH_LONG).show();
                } else {
                    new BackgroundWorker2().execute(new String[0]);
                }
            }
        });
    }


    public boolean isEmailValid(CharSequence email2) {
        return Patterns.EMAIL_ADDRESS.matcher(email2).matches();
    }
}
