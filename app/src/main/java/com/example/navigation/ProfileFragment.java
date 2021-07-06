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

public class ProfileFragment extends Fragment {

    private static final String TAG = "MyActivity";
    Button btAddUser;
    EditText etUserId, etUsername, etMail, etPassword;
    TextView tfErrorUserId, tfErrorUsername, tfErrorMail, tfErrorPassword;
    CheckBox cbAdmin;

    String userId, username, password, mail;
    Boolean isAdmin = Boolean.FALSE;
    Boolean validData = Boolean.TRUE;

    public boolean onCheckboxAdminClicked(View view) {

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
    }

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

        btAddUser = view.findViewById(R.id.btAddUser);
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

        return view;
    }
}