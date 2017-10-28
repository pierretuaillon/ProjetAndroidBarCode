package com.example.pierre.projetandroidbarcode;

import android.content.Intent;
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
        Intent source = getIntent();
        if(source.hasExtra("ArmeID")){
            if(source.hasExtra("PDA")){
                mettreAJour(source.getIntExtra("ArmeID", -1), source.getIntExtra("PDA", -1));
            }
        }
    }

    public void mettreAJour(int idArme, int PDA){
        Log.v("IDENTIFIANT ARME : ", Integer.toString(idArme));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();

        Arme armeAfficher = monstreBDD.getArmeWithID(idArme);
        nomArme.setText(armeAfficher.getNom());

        img.setImageResource(
                this.getResources().getIdentifier(armeAfficher.getLienImage().toLowerCase(), "drawable", getPackageName())
        );
        monstreBDD.close();
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
