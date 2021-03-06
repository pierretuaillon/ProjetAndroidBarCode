package com.example.pierre.projetandroidbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Pierre on 23/10/2017.
 */
public class LecteurCodeBarres extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }



    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        // Do something with the result here
        Log.d("Scan content", rawResult.getText()); // Prints scan results

        Log.d("Scan format", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);

        Intent intent = new Intent();

        intent.putExtra("scan_content",rawResult.getText());
        intent.putExtra("scan_format",rawResult.getBarcodeFormat().toString());
        setResult( RESULT_OK,intent);
        finish();
    }
}
