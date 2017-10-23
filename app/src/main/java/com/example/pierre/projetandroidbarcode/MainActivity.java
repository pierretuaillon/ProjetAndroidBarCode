package com.example.pierre.projetandroidbarcode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bScan;



    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        bScan = (Button) findViewById(R.id.scan_button);
        bScan.setOnClickListener(this);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LecteurCodeBarres.class);
        startActivity(intent);
    }
}