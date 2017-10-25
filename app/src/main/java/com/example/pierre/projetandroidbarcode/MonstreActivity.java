package com.example.pierre.projetandroidbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by natha on 25/10/2017.
 */
public class MonstreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nomMonstreView;
    private Button armesBtn;
    private Button armuresBtn;
    private Button button;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstre);
        nomMonstreView = (TextView) findViewById(R.id.nomMonstre);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        armesBtn = (Button) findViewById(R.id.armesBtn);
        armesBtn.setOnClickListener(this);
        armuresBtn = (Button) findViewById(R.id.armuresBtn);
        armuresBtn.setOnClickListener(this);
        button = (Button) findViewById(R.id.potionsBtn);
        button.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.armesBtn:
                intent = new Intent(MonstreActivity.this, ArmesActivity.class);
                startActivityForResult(intent,22);
                break;
            case R.id.armuresBtn:
                intent = new Intent(MonstreActivity.this, ArmuresActivity.class);
                startActivityForResult(intent,22);
                break;
            case R.id.potionsBtn:
                intent = new Intent(MonstreActivity.this, PotionsActivity.class);
                startActivityForResult(intent,22);
                break;
            case R.id.imageView:
                intent = new Intent(MonstreActivity.this, MonstresActivity.class);
                startActivityForResult(intent,22);
                break;
        }
    }
}