package com.example.pierre.projetandroidbarcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pierre on 27/10/2017.
 */

public class ArmeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nomArme;
    ImageView img;
    Button btnRetour;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showarme);
        nomArme = (TextView) findViewById(R.id.nomArme);
        img = (ImageView) findViewById(R.id.imageViewArme);
        btnRetour = (Button) findViewById(R.id.retourBtn);
        btnRetour.setOnClickListener(this);
    }

    public void mettreAJour(int idArme){
        Log.v("IDENTIFIANT ARME : ", Integer.toString(idArme));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();

        Arme armeAfficher = monstreBDD.getArmeWithID(idArme);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retourBtn :
                finish();
                break;
        }
    }
}
