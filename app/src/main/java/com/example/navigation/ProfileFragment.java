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

   /* private static final String TAG = "MyActivity";
    Button btAddUser;
    EditText etUserId, etUsername, etMail, etPassword;
    TextView tfErrorUserId, tfErrorUsername, tfErrorMail, tfErrorPassword;
    CheckBox cbAdmin;

    String userId, username, password, mail;
    Boolean isAdmin = Boolean.FALSE;
    Boolean validData = Boolean.TRUE;*/

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

    /*public boolean onCheckboxAdminClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();
        int admin = 0;

        switch (view.getId()) {
            case R.id.cbAdmin:
                if (checked)
                    isAdmin = Boolean.TRUE;
                else
                    isAdmin = Boolean.FALSE;
                break;
        }

        return isAdmin;
    }*/

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

        /*btAddUser = view.findViewById(R.id.btAddUser);
        etUserId = view.findViewById(R.id.etUserID);
        etUsername = view.findViewById(R.id.etUsername);
        etMail = view.findViewById(R.id.etMail);
        etPassword = view.findViewById(R.id.etPassword);

        tfErrorUsername = view.findViewById(R.id.tfErrorUsername);
        tfErrorUserId = view.findViewById(R.id.tfErrorUserID);
        tfErrorMail = view.findViewById(R.id.tfErrorMail);
        tfErrorPassword = view.findViewById(R.id.tfErrorPassword);

        cbAdmin = view.findViewById(R.id.cbAdmin);

        cbAdmin.setOnClickListener(v -> {
            onCheckboxAdminClicked(cbAdmin);
        });

        btAddUser.setOnClickListener(v -> {

            userId = etUserId.getText().toString();
            username = etUsername.getText().toString();
            mail = etMail.getText().toString();
            password = etPassword.getText().toString();

            Log.i(TAG, "Bis hier gehts");

            if (userId.isEmpty()) {
                Toast.makeText(getActivity(),
                        "Please fill in User ID", Toast.LENGTH_SHORT).show();
                validData = Boolean.FALSE;
            } else if (userId.length() != 4) {
                tfErrorUserId.setText("User ID must consist of 4 numbers");
                validData = Boolean.FALSE;
            }

            Log.i(TAG, "validData User ID "+validData.toString());

            if (username.isEmpty()) {
                Toast.makeText(getActivity(),
                        "Please fill in Username", Toast.LENGTH_SHORT).show();
                validData = Boolean.FALSE;
            } else if (username.length() != 7) {
                tfErrorUsername.setText("Username must consist of 7 letters");
                validData = Boolean.FALSE;
            }

            Log.i(TAG, "validData username "+validData.toString());

            if (mail.isEmpty()) {
                Toast.makeText(getActivity(),
                        "Please fill in E-Mail address", Toast.LENGTH_SHORT).show();
                validData = Boolean.FALSE;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                tfErrorMail.setText("Please enter a valid E-Mail address");
                validData = Boolean.FALSE;
            }

            Log.i(TAG, "validData mail "+validData.toString());

            if (password.isEmpty()) {
                Toast.makeText(getActivity(),
                        "Please fill in Password", Toast.LENGTH_SHORT).show();
                validData = Boolean.FALSE;
            } else if (password.length() <= 6) {
                tfErrorPassword.setText("Password must contain at least 6 characters");
                validData = Boolean.FALSE;
            }


            if (userId.length() == 4) {
                tfErrorUserId.setText("");
            }

            if (username.length() == 7) {
                tfErrorUsername.setText("");
            }

            if (Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                tfErrorMail.setText("");
            }

            if (password.length() >= 6) {
                tfErrorPassword.setText("");
            }

            Log.i(TAG, "Bis hier gehts 2");
            Log.i(TAG, "validData "+validData.toString());

            if (validData == Boolean.TRUE) {

                Toast.makeText(getActivity(),
                        "User  successfully added! ", Toast.LENGTH_LONG).show();
            }

            Log.i(TAG, "isAdmin "+ isAdmin);

        });
        */

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