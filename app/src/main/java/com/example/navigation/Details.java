package com.example.navigation;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import gs1utils.ApplicationIdentifier;
import gs1utils.CheckDigit;
import gs1utils.ElementStrings;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Details extends AppCompatActivity {

    private TextView barcodeText;
    private TextView barcodeType;
    private TextView gtin;
    private TextView headgtin;

    Bitmap bmp, scaledbmp;
    private static final int PERMISSION_REQUEST_CODE = 200;

    Button btBackToScanner;
    Button btPDF;

    int pageHeight = 1120;
    int pagewidth = 792;

    private String TAG ="MyDetails";



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo_barcodescanner_gelb);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        barcodeText = findViewById(R.id.barcode_text);
        barcodeType = findViewById(R.id.barcode_type);
        btBackToScanner = findViewById(R.id.btBackToScanner);
        gtin = findViewById(R.id.gtin);
        btPDF = findViewById(R.id.btPDF);

        Integer barcodeTyp = getIntent().getIntExtra("barcodeTyp",0);
        String barcodeData = getIntent().getStringExtra("barcodeData");
        Integer r = 0;
        Integer l = barcodeData.length();
        String c;
        char[] data = {barcodeData.charAt(0), barcodeData.charAt(1), barcodeData.charAt(2)};
        String f3 = new String(data);


        if (barcodeTyp.equals(5))
        {
            barcodeType.setText("Product");

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

        }
        else
        {
            barcodeType.setText(barcodeTyp.toString());
            barcodeText.setText(barcodeData);
        }



        btBackToScanner.setOnClickListener(v -> {
            BackToScanner();
        });

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        btPDF.setOnClickListener(v -> {
            generatePDF();
        });

        if(barcodeTyp.equals(7))
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

    private void generatePDF() {
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();
        String bcType=barcodeType.getText().toString();
        String bcText=barcodeText.getText().toString();
        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(15);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.black));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.

        canvas.drawText("Typ:" + bcType, 209, 100, title);
        canvas.drawText("Text:" + bcText, 209, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));
        title.setTextSize(15);

        // below line is used for setting
        // our text to center of PDF.
        title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "Barcode.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(Details.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }






}