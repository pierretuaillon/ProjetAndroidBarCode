package com.example.pierre.projetandroidbarcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

/**
 * Created by natha on 25/10/2017.
 */
public class MonstreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nomMonstreView;
    private Button armesBtn;
    private Button armuresBtn;
    private Button button;
    private ImageView  imageView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstre);
        nomMonstreView = (TextView) findViewById(R.id.nomMonstre);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        armesBtn = (Button) findViewById(R.id.armesBtn);
        armesBtn.setOnClickListener(this);
        Button retourBtn = (Button) findViewById(R.id.retourBtn);
        retourBtn.setOnClickListener(this);
        armuresBtn = (Button) findViewById(R.id.armuresBtn);
        armuresBtn.setOnClickListener(this);

        Intent source = getIntent();
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(source.getIntExtra("MonstreID", -1)));
        if(source.hasExtra("MonstreID"))
        mettreAJourMonstre(source.getIntExtra("MonstreID", -1));

    }

    public void mettreAJourMonstre(int idMonstre){
        //Création d'une instance de ma classe LivresBDD
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(idMonstre));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        Monstre monstreAfficcher = monstreBDD.getMonstreWithID(idMonstre);
        imageView.setImageResource(
                this.getResources().getIdentifier(monstreAfficcher.getApparence().toLowerCase(), "drawable", getPackageName()));
        nomMonstreView.setText(monstreAfficcher.getNom());


        //MonstreActivity.getAppContext().getResources().getIdentifier(monstreAfficcher.getApparence().toLowerCase(), "drawable",
        //      MainActivity.getAppContext().getPackageName()));



        monstreBDD.close();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.armesBtn:
                intent = new Intent(MonstreActivity.this, ArmesActivity.class);
                startActivityForResult(intent,25);
                break;
            case R.id.armuresBtn:
                intent = new Intent(MonstreActivity.this, ArmuresActivity.class);
                startActivityForResult(intent,26);
                break;
            case R.id.imageView:
                intent = new Intent(MonstreActivity.this, MonstresActivity.class);
                startActivityForResult(intent,27);
                break;
            case R.id.retourBtn:
                finish();
                break;
            default:

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode){
            case 23 :

                break;
            case 25://arme

                break;
            case 26://armure

                break;
            case 27://dragon
                if(resultCode==RESULT_OK)
                    mettreAJourMonstre(intent.getIntExtra("monstre", -1));
                break;
        }

    }
}