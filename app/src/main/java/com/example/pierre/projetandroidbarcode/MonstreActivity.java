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

import static android.icu.lang.UProperty.MATH;

/**
 * Created by natha on 25/10/2017.
 */
public class MonstreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nomMonstreView, PDVMonstreTextView, PDAMonstreTextView, armureTextView;
    private ImageView armesBtn,armuresBtn;
    private ImageView  imageView;
    private Monstre monstreAfficher;
    private Arme armeAfficher;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstre);
        nomMonstreView = (TextView) findViewById(R.id.nomMonstre);
        PDVMonstreTextView = (TextView) findViewById(R.id.PDVMonstreText);
        PDAMonstreTextView = (TextView) findViewById(R.id.PDAMonstreText);
        armureTextView = (TextView) findViewById(R.id.armureText);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        armesBtn = (ImageView) findViewById(R.id.armesBtn);
        armesBtn.setOnClickListener(this);
        Button retourBtn = (Button) findViewById(R.id.retourBtn);
        retourBtn.setOnClickListener(this);
        armuresBtn = (ImageView) findViewById(R.id.armuresBtn);
        armuresBtn.setOnClickListener(this);

        Intent source = getIntent();
        Log.v("IDENTIFIANT MONSTRE : ", Integer.toString(source.getIntExtra("MonstreID", -1)));
        if(source.hasExtra("MonstreID")) {
            debloquerMonstre(source.getIntExtra("MonstreID", -1));
            mettreAJourMonstre(source.getIntExtra("MonstreID", -1));
        }
        else{
            afficherMonstre();
        }
        afficherArme();
        afficherArmure();
    }

    public void debloquerMonstre(int idMonstre){
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(idMonstre));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreAfficher = monstreBDD.getMonstreWithID(idMonstre);
        imageView.setImageResource(
                this.getResources().getIdentifier(monstreAfficher.getApparence().toLowerCase(), "drawable", getPackageName()));
        nomMonstreView.setText(monstreAfficher.getNom());
        monstreAfficher.setDebloque(true);
        monstreAfficher.setPDA(Math.max(monstreAfficher.getPDA(),10+getIntent().getIntExtra("PDA",0)));
        monstreAfficher.setPDV(Math.max(monstreAfficher.getPDV(),50+getIntent().getIntExtra("PDV",0)));
        monstreBDD.selectMonstre(monstreAfficher.getId());
        monstreBDD.updateMonstre(monstreAfficher.getId(), monstreAfficher);
        monstreBDD.close();
    }

    public void mettreAJourMonstre(int idMonstre){
        //Création d'une instance de ma classe MonstreBDD
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(idMonstre));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreAfficher = monstreBDD.getMonstreWithID(idMonstre);
        monstreBDD.selectMonstre(monstreAfficher.getId());
        monstreBDD.close();
        afficherMonstre();
    }
    public void afficherMonstre(){
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        monstreAfficher=monstreBDD.getMonstreSelectionne();
        monstreBDD.close();
        if(monstreAfficher!=null) {
            imageView.setImageResource(
                    this.getResources().getIdentifier(monstreAfficher.getApparence().toLowerCase(), "drawable", getPackageName()));
            nomMonstreView.setText(monstreAfficher.getNom());
            PDVMonstreTextView.setText(Integer.toString(monstreAfficher.getPDV()));
            if(armeAfficher!=null)
                PDAMonstreTextView.setText(Integer.toString(monstreAfficher.getPDA() + armeAfficher.getAttaque()));
            else
                PDAMonstreTextView.setText(Integer.toString(monstreAfficher.getPDA()));
        }

    }
    public void mettreAJourArme(int id){
        //Création d'une instance de ma classe LivresBDD
        Log.v("IDENTIFIANT arme2 : ", Integer.toString(id));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreBDD.selectArme(id);
        monstreBDD.close();
        afficherArme();
    }
    public void afficherArme(){
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        armeAfficher = monstreBDD.getArmeSelectionne();
        monstreBDD.close();
        if(armeAfficher!=null) {
            PDAMonstreTextView.setText(Integer.toString(armeAfficher.getAttaque() + monstreAfficher.getPDA()));
            armesBtn.setImageResource(
                    this.getResources().getIdentifier(armeAfficher.getLienImage().toLowerCase(), "drawable", getPackageName()));
        }
    }
    public void mettreAJourArmure(int id){
        //Création d'une instance de ma classe LivresBDD
        Log.v("IDENTIFIANT armure2 : ", Integer.toString(id));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreBDD.selectArmure(id);
        monstreBDD.close();
        afficherArmure();
    }

    public void afficherArmure(){
        MonstreBDD monstreBDD = new MonstreBDD(this);
        monstreBDD.open();
        Armure armure = monstreBDD.getArmureSelectionne();
        monstreBDD.close();
        if(armure!=null) {
            armureTextView.setText(Integer.toString(armure.getDefense()));
            armuresBtn.setImageResource(
                    this.getResources().getIdentifier(armure.getLienImage().toLowerCase(), "drawable", getPackageName()));
        }
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
                if(resultCode==RESULT_OK)
                    mettreAJourArme(intent.getIntExtra("id", -1));
                break;
            case 26://armure
                if(resultCode==RESULT_OK)
                    mettreAJourArmure(intent.getIntExtra("id", -1));
                break;
            case 27://dragon
                if(resultCode==RESULT_OK)
                    mettreAJourMonstre(intent.getIntExtra("monstre", -1));
                break;
        }

    }
}