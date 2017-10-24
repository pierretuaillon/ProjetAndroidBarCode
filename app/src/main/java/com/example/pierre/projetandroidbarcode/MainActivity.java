package com.example.pierre.projetandroidbarcode;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bScan;

    TextView txtFormat, txtContent;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        bScan = (Button) findViewById(R.id.scan_button);
        txtFormat = (TextView) findViewById(R.id.scan_format);
        txtContent = (TextView) findViewById(R.id.scan_content);
        bScan.setOnClickListener(this);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        txtFormat.setText(getIntent().getStringExtra("scan_format"));
        txtContent.setText(getIntent().getStringExtra("scan_content"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LecteurCodeBarres.class);
        startActivity(intent);
    }
}