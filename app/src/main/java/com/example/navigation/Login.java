package com.example.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    Button b1,b2;
    EditText etUsername, etPassword;

    private String username, password;
    private final String URL = "http://10.0.2.2/login/login3.php";

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = password = "";

        b1 = findViewById(R.id.button);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        b2 = findViewById(R.id.button2);

        b2.setOnClickListener(v -> finish());
    }

    public static final String md5(final String s)
    {
        try
        {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public void login(View view){
        username = etUsername.getText().toString();
        password = md5(etPassword.getText().toString());

        Log.i(TAG, "Es geht");

        Log.i(TAG, "username "+username);

        Log.i(TAG, "password "+password);

        if(!username.equals("") && !password.equals("")){

            Log.i(TAG, "username "+ username+" password "+password);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

                Intent intent;

                Log.i(TAG, "response "+response);

                switch (response){
                    case "SuccessAdmin":
                        intent = new Intent(Login.this, HomeAdmin.class);
                        startActivity(intent);
                        finish();
                        break;

                    case "SuccessUser":
                        intent = new Intent(Login.this, HomeUser.class);
                        startActivity(intent);
                        finish();
                        break;

                    case "Failure":
                        Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
                        break;
                }
            }, error -> Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show())
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("password", password);
                    return data;
                }
            };

            Log.i(TAG, "sr " + stringRequest);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            Log.i(TAG, "Kein Password");
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}