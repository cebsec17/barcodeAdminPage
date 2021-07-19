package com.example.navigation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class AddProject extends AppCompatActivity {

    TextView tvBarcodeType;
    boolean[] selectedTypes;
    ArrayList<Integer> barcodeTypeList = new ArrayList<>();
    String[] BarcodeTypesArray = {"Barcode A", "BarcodeB", "BarcodeC", "BarcodeD",
            "BarcodeE", "BarcodeF", "BarcodeG"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        tvBarcodeType = findViewById(R.id.tv_barcodeType);

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
                        }else{
                            barcodeTypeList.remove(i);
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
