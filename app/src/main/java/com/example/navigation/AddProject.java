package com.example.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class AddProject extends AppCompatActivity {

    TextView tvBarcodeType;
    Button btAddProject;
    boolean[] selectedTypes;
    ArrayList<Integer> barcodeTypeList = new ArrayList<>();
    String[] BarcodeTypesArray = {"Barcode A", "Barcode B", "Barcode C", "Barcode D",
            "Barcode E", "Barcode F", "Barcode G", "Barcode H", "Barcode I", "Barcode J", "Barcode K",
            "Barcode L", "Barcode M", "Barcode N", "Barcode O", "Barcode P", "Barcode Q", "Barcode R",
            "Barcode S", "Barcode T", "Barcode U"};

    private String TAG = "AddActivity";

    private final String URL = "http://172.28.57.146/project/insert.php";
    private String pn="";
    private String pid="";

    EditText etProjectName;
    EditText etProjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        tvBarcodeType = findViewById(R.id.tv_barcodeType);
        etProjectName = findViewById(R.id.etProjectName);
        btAddProject = findViewById(R.id.btAddProject);
        etProjectID = findViewById(R.id.etProjectID);

        btAddProject.setOnClickListener(v -> {

            if(!etProjectName.getText().toString().equals("")&&!etProjectID.getText().toString().equals("")){

                pn = etProjectName.getText().toString();
                pid = etProjectID.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

                    Log.i(TAG, "zzz"+ response);

                    if (response.equals("Success")) {
                        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
                        etProjectName.setText("");
                        etProjectID.setText("");

                        Log.i(TAG, "zzz");

                    } else if (response.equals("Failure")) {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show())
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("pid", pid);
                        data.put("projektname", pn);
                        Log.i(TAG, ""+data);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
            Log.i(TAG, "bis hier gehts 2");

            finish();
        });

        selectedTypes = new boolean[BarcodeTypesArray.length];

        tvBarcodeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddProject.this
                );
                builder.setTitle("Select Barcode Types");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(BarcodeTypesArray, selectedTypes, new DialogInterface.
                        OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i, boolean b) {
                        if(b){
                            barcodeTypeList.add(i);
                            Collections.sort(barcodeTypeList);
                            etProjectName.getText().toString();
                        }else{
                            barcodeTypeList.remove(i);
                            etProjectName.getText().toString();
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j=0; j<barcodeTypeList.size(); j++)
                        {
                            stringBuilder.append(BarcodeTypesArray[barcodeTypeList.get(j)]);
                            if (j != barcodeTypeList.size()-1){
                                stringBuilder.append(", ");
                                etProjectName.getText().toString();
                            }
                        }

                        tvBarcodeType.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        for (int j=0; j<selectedTypes.length; j++)
                        {
                            selectedTypes[j] = false;
                            barcodeTypeList.clear();
                            tvBarcodeType.setText("");
                        }

                    }
                });
Log.i(TAG, "bis hier gehts");
                builder.show();
            }
        });



    }
}
