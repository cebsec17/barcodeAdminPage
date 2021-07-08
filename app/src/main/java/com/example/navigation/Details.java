package com.example.navigation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Details extends AppCompatActivity {

    private TextView barcodeText;
    private TextView barcodeType;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        barcodeText = findViewById(R.id.barcode_text);
        barcodeType = findViewById(R.id.barcode_type);


        Integer barcodeTyp = getIntent().getIntExtra("barcodeTyp",0);
        String barcodeData = getIntent().getStringExtra("barcodeData");
        barcodeText.setText(barcodeData);
        barcodeType.setText(Integer.toString(barcodeTyp));

    }

}