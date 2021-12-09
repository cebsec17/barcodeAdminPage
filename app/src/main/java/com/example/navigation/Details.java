package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gs1utils.ApplicationIdentifier;
import gs1utils.CheckDigit;
import gs1utils.ElementStrings;


public class Details extends AppCompatActivity {

    private TextView barcodeText;
    private TextView barcodeType;
    private TextView gtin;
    private TextView headgtin;

    Button btBackToScanner;
    ImageButton btProject;

    private String TAG ="MyDetails";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        barcodeText = findViewById(R.id.barcode_text);
        barcodeType = findViewById(R.id.barcode_type);
        btBackToScanner = findViewById(R.id.btBackToScanner);
        gtin = findViewById(R.id.gtin);

        Integer barcodeTyp = getIntent().getIntExtra("barcodeTyp",0);
        String barcodeData = getIntent().getStringExtra("barcodeData");
        Integer r = 0;
        Integer l = barcodeData.length();
        char[] data = {barcodeData.charAt(0), barcodeData.charAt(1), barcodeData.charAt(2)};
        String[] bartype = {"All Formats", "Code_128", "Code_39", "ISBN", "Code_93", "PRODUCT", "SMS", "TEXT", "URL", "WIFI", "GEO", "Calendar Event",
                "Driver License", "Data Matrix", "EAN_13", "EAN_8", "ITF", "QR_CODE", "UPC_A", "UPC_E", "PDF417", "Aztec"};


        switch (barcodeTyp)
        {
            case 0:
                barcodeType.setText(bartype[0]);
                break;

            case 1:
                barcodeType.setText(bartype[1]);
                break;

            case 2:
                barcodeType.setText(bartype[2]);
                break;

            case 3:
                barcodeType.setText(bartype[3]);
                break;

            case 4:
                barcodeType.setText(bartype[4]);
                break;

            case 5:
                barcodeType.setText(bartype[5]);
                break;

            case 6:
                barcodeType.setText(bartype[6]);
                break;

            case 7:
                barcodeType.setText(bartype[7]);
                break;

            case 8:
                barcodeType.setText(bartype[8]);
                break;

            case 9:
                barcodeType.setText(bartype[9]);
                break;

            case 10:
                barcodeType.setText(bartype[10]);
                break;

            case 11:
                barcodeType.setText(bartype[11]);
                break;

            case 12:
                barcodeType.setText(bartype[12]);
                break;

            case 16:
                barcodeType.setText(bartype[13]);
                break;

            case 32:
                barcodeType.setText(bartype[14]);
                break;

            case 64:
                barcodeType.setText(bartype[15]);
                break;

            case 128:
                barcodeType.setText(bartype[16]);
                break;

            case 256:
                barcodeType.setText(bartype[17]);
                break;

            case 512:
                barcodeType.setText(bartype[18]);
                break;

            case 1024:
                barcodeType.setText(bartype[19]);
                break;

            case 2048:
                barcodeType.setText(bartype[20]);
                break;

            case 4096:
                barcodeType.setText(bartype[21]);
                break;

        }



        if (barcodeTyp.equals(5))
        {

            if(barcodeData.length() == 12)
            {
                int r1 = 0;
                int r2 = 0;
                int i = 1;



                while (i <= l -1)
                {
                    r1 = Character.getNumericValue(barcodeData.charAt(i));
                    r2 = Character.getNumericValue(barcodeData.charAt(i-1)) * 3;

                    r = r1+ r2;


                    i = i + 2;

                }

                if(Character.getNumericValue(barcodeData.charAt(11)) != r)
                {
                    barcodeText.setText("Wrong Checksum");
                    barcodeType.setText("Product/UPC_A");
                }
                else
                {
                    barcodeText.setText(barcodeData);
                    barcodeType.setText("Product/UPC_A");
                }
            }
            else
            {
                barcodeType.setText("Product/EAN_13");
                barcodeText.setText(barcodeData);
            }

        } else
        {
            barcodeText.setText(barcodeData);
        }



        btBackToScanner.setOnClickListener(v -> {
            BackToScanner();
        });

        if(barcodeTyp.equals(1))
        {

            ElementStrings.ParseResult result = ElementStrings.parse(barcodeData);

            if(result.getString(ApplicationIdentifier.GTIN) != null)
            {
                gtin.append("GTIN:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.GTIN));
            }

            if(result.getString(ApplicationIdentifier.CONTAINED_GTIN) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Contained GTIN:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINED_GTIN).toString());
                gtin.append("kg");
            }

            if(result.getString(ApplicationIdentifier.SSCC) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("SSCC:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SSCC));

            }

            if(result.getString(ApplicationIdentifier.SERIAL_NUMBER) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Production Location:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SERIAL_NUMBER));

            }

            if(result.getDate(ApplicationIdentifier.BEST_BEFORE_DATE) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Best Before:");
                gtin.append("\n");
                gtin.append(result.getDate(ApplicationIdentifier.BEST_BEFORE_DATE).toString());

            }

            if(result.getDate(ApplicationIdentifier.EXPIRATION_DATE) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Expiration Date:");
                gtin.append("\n");
                gtin.append(result.getDate(ApplicationIdentifier.EXPIRATION_DATE).toString());

            }

            if(result.getString(ApplicationIdentifier.BATCH_OR_LOT_NUMBER) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Batch/Lot:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.BATCH_OR_LOT_NUMBER));
            }

            if(result.getString(ApplicationIdentifier.SHIPMENT_NUMBER) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Shipment Number:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SHIPMENT_NUMBER));

            }

            if(result.getString(ApplicationIdentifier.COUNT_OF_TRADE_ITEMS) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Count:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.COUNT_OF_TRADE_ITEMS));

            }

            if(result.getString(ApplicationIdentifier.SHIP_TO_LOCATION) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Shipment Location:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SHIP_TO_LOCATION));

            }

            if(result.getString(ApplicationIdentifier.SHIP_TO_POSTAL_CODE) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Ship to:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SHIP_TO_POSTAL_CODE));

            }

            if(result.getDate(ApplicationIdentifier.SHIP_FOR_LOCATION) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Ship for:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.SHIP_FOR_LOCATION));

            }

            if(result.getDate(ApplicationIdentifier.PRODUCTION_DATE) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Production Date:");
                gtin.append("\n");
                gtin.append(result.getDate(ApplicationIdentifier.PRODUCTION_DATE).toString());

            }

            if(result.getDate(ApplicationIdentifier.PRODUCTION_OR_SERVICE_LOCATION) != null) {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Production Location:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.PRODUCTION_OR_SERVICE_LOCATION));


            }

            if(result.getString(ApplicationIdentifier.CONTAINER_AREA_SQUARE_METRES) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Area:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_AREA_SQUARE_METRES));
                gtin.append("m²");
            }

            if(result.getString(ApplicationIdentifier.CONTAINER_HEIGHT_METRES) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Height:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_HEIGHT_METRES));
                gtin.append(" m");
            }

            if(result.getString(ApplicationIdentifier.CONTAINER_LENGTH_METRES) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Length:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_LENGTH_METRES));
                gtin.append(" m");
            }

            if(result.getString(ApplicationIdentifier.CONTAINER_WIDTH_METRES) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Width:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_WIDTH_METRES));
                gtin.append(" m");
            }

            if(result.getString(ApplicationIdentifier.CONTAINER_GROSS_WEIGHT_KG) != null)
            {
                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Weight:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_GROSS_WEIGHT_KG));
                gtin.append(" kg");
            }

            if(result.getString(ApplicationIdentifier.CONTAINER_VOLUME_LITRES) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Container Volume:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.CONTAINER_VOLUME_LITRES));
                gtin.append(" l");
            }

            if(result.getDecimal(ApplicationIdentifier.PRICE_PER_UNIT) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Price per Unit:");
                gtin.append("\n");
                gtin.append(result.getDecimal(ApplicationIdentifier.PRICE_PER_UNIT).toString());

            }

            if(result.getString(ApplicationIdentifier.ITEM_AREA_SQUARE_METRES) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Item Area:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.ITEM_AREA_SQUARE_METRES));
                gtin.append(" m²");

            }

            if(result.getString(ApplicationIdentifier.ITEM_HEIGHT_METRES) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Item Height:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.ITEM_HEIGHT_METRES));
                gtin.append(" m");
            }

            if(result.getString(ApplicationIdentifier.ITEM_LENGTH_METRES) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Item Length:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.ITEM_LENGTH_METRES));
                gtin.append(" m");
            }

            if(result.getString(ApplicationIdentifier.ITEM_WIDTH_METRES) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Item Width:");
                gtin.append("\n");
                gtin.append(result.getString(ApplicationIdentifier.ITEM_WIDTH_METRES));
                gtin.append(" m");
            }

            if(result.getDecimal(ApplicationIdentifier.ITEM_NET_WEIGHT_KG) != null)
            {

                gtin.append("\n");
                gtin.append("\n");
                gtin.append("Item NET Weight:");
                gtin.append("\n");
                gtin.append(result.getDecimal(ApplicationIdentifier.ITEM_NET_WEIGHT_KG).toString());
                gtin.append(" kg");
            }

        }

    }






    private void BackToScanner() {
        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    private void onSaveDetails()
    {

    }





}