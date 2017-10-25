package com.example.pierre.projetandroidbarcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by natha on 25/10/2017.
 */

public class MonstresActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Monstre> monstres;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstres);
        monstres = MonstreBDD.getAllMonstres();
        
    }

    @Override
    public void onClick(View v) {

    }
}
