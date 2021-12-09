package com.example.navigation;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText etUserID, etUsername, etMail, etPassword;
    private CheckBox cbAdmin;
    private Button btAddUser;
    private TextView tfErrorUserID, tfErrorUsername, tfErrorMail, tfErrorPassword;

    private String TAG = "AddActivity";

    private final String URL = "http://172.28.57.24/login/insert.php";
    private String username;
    private String mail;
    private String password;
    private boolean admin;
    private int  userid, adminr;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        etUserID = view.findViewById(R.id.etUserID);
        etUsername = view.findViewById(R.id.etUsername);
        etMail = view.findViewById(R.id.etMail);
        etPassword = view.findViewById(R.id.etPassword);
        cbAdmin = view.findViewById(R.id.cbAdmin);
        btAddUser = view.findViewById(R.id.btAddUser);
        tfErrorUserID = view.findViewById(R.id.tfErrorUserID);
        tfErrorUsername = view.findViewById(R.id.tfErrorUsername);
        tfErrorMail = view.findViewById(R.id.tfErrorMail);
        tfErrorPassword = view.findViewById(R.id.tfErrorPassword);

        userid = 0;
        adminr=0;
        username = mail = password = "";

        btAddUser.setOnClickListener(v -> {
            addNewUser();
        });

        return view;
    }

    public void addNewUser(){

        if(etUserID.getText().toString().equals("") || etUserID.getText().toString().length() != 4){
            tfErrorUserID.setText("User ID must consist of 4 numbers");
        }
        else{
            userid = Integer.parseInt(etUserID.getText().toString());
            tfErrorUserID.setText("");
        }

        if(etUsername.getText().toString().equals(""))
        {
            tfErrorUsername.setText("Please enter a valid Username");
        }
        else{
            username = etUsername.getText().toString();
            tfErrorUsername.setText("");
        }

        if(etMail.getText().toString().equals("") || !Patterns.EMAIL_ADDRESS.matcher(etMail.getText().toString()).matches()){
            tfErrorMail.setText("Please enter a valid E-Mail address");
        }
        else
        {
            mail = etMail.getText().toString();
            tfErrorMail.setText("");
        }

        if(etPassword.getText().toString().equals("") || etPassword.getText().toString().length() <= 6){
            tfErrorPassword.setText("Password must contain at least 6 characters");
        }
        else{
            password = etPassword.getText().toString();
            tfErrorPassword.setText("");
        }

        admin = cbAdmin.isChecked();

        if(admin)
            adminr=1;

        if(!etUserID.getText().toString().equals("") && !username.equals("") && !mail.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                if (response.equals("Success")) {
                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                    etUserID.setText("");
                    etUsername.setText("");
                    etMail.setText("");
                    etPassword.setText("");
                    cbAdmin.setChecked(false);
                    cbAdmin.setSelected(false);
                    adminr = 0;
                } else if (response.equals("Failure")) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show())
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("userid", String.valueOf(userid));
                    data.put("username", username);
                    data.put("mail", mail);
                    data.put("password", password);
                    data.put("admin", String.valueOf(adminr));
                    Log.i(TAG, ""+data);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
}