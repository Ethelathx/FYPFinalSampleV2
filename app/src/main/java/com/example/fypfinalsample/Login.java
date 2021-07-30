package com.example.fypfinalsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    TextView tvSignUp, tvForgot;
    EditText etEmail, etPassword;
    Button btnSignIn;

    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgot = findViewById(R.id.tvForgot);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        client = new AsyncHttpClient();


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    Toast.makeText(Login.this, "Please enter email.", Toast.LENGTH_LONG).show();
                } else if (password.equalsIgnoreCase("")) {
                    Toast.makeText(Login.this, "Please enter password.", Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });
    }


    private void login() {
        RequestParams params = new RequestParams();
        params.add("email", etEmail.getText().toString());
        params.add("password", etPassword.getText().toString());
        //for real devices, use the current location's ip address
        client.post("http://10.0.2.2/greek_goodies/doLogin.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.i("doLogin Results: ", response.toString());
                    Boolean authenticated = response.getBoolean("authenticated");
                    if (authenticated == true) {
                        String apikey = response.getString("apikey");
                        String role = response.getString("role");
                        String userId = response.getString("user_id");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("role", role);
                        editor.putString("apikey", apikey);
                        editor.putString("userId", userId);
                        editor.commit();
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }//end onSuccess
        });
    }
}