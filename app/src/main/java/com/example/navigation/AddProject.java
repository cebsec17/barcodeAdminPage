package com.example.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class AddProject extends AppCompatActivity {

    TextView tvBarcodeType;
    Button btAddProject;
    boolean[] selectedTypes;
    ArrayList<Integer> barcodeTypeList = new ArrayList<>();
    String[] BarcodeTypesArray = {"Barcode A", "Barcode B", "Barcode C", "Barcode D",
            "Barcode E", "Barcode F", "Barcode G", "Barcode H", "Barcode I", "Barcode J", "Barcode K",
            "Barcode L", "Barcode M", "Barcode N", "Barcode O", "Barcode P", "Barcode Q", "Barcode R",
            "Barcode S", "Barcode T", "Barcode U"};


    EditText projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        tvBarcodeType = findViewById(R.id.tv_barcodeType);
        projectName = findViewById(R.id.etProjectName);
        btAddProject = findViewById(R.id.btAddProject);
        btAddProject.setOnClickListener(v -> {
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
                            projectName.getText().toString();
                        }else{
                            barcodeTypeList.remove(i);
                            projectName.getText().toString();
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
                                projectName.getText().toString();
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

                builder.show();
            }
        });
    }
}
