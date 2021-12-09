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

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    Button b1,b2;
    EditText etUsername, etPassword;

    private String username, password;
    private final String URL = "http://172.28.57.24/login/login.php";

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

    public void login(View view){
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if(!username.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

                Intent intent;

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