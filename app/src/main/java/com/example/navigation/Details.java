package com.example.navigation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Details extends AppCompatActivity {

    private TextView barcodeText;
    private TextView barcodeType;
    Button btBackToScanner;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        barcodeText = findViewById(R.id.barcode_text);
        barcodeType = findViewById(R.id.barcode_type);
        btBackToScanner = findViewById(R.id.btBackToScanner);




        Integer barcodeTyp = getIntent().getIntExtra("barcodeTyp",0);
        String barcodeData = getIntent().getStringExtra("barcodeData");
        barcodeText.setText(barcodeData);
        barcodeType.setText(Integer.toString(barcodeTyp));

       btBackToScanner.setOnClickListener(v -> {
            BackToScanner(new ScannerFragment());
        });


    }

   private void BackToScanner(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.addToBackStack(null);
        ft.commit();


    }

}