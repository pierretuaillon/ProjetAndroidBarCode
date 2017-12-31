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

    private TextView nomMonstreView, PDVMonstreTextView, PDAMonstreTextView;
    private ImageView armesBtn;
    private ImageView armuresBtn;
    private Button button;
    private ImageView  imageView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showmonstre);
        nomMonstreView = (TextView) findViewById(R.id.nomMonstre);
        PDVMonstreTextView = (TextView) findViewById(R.id.PDVMonstreText);
        PDAMonstreTextView = (TextView) findViewById(R.id.PDAMonstreText);
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
            mettreAJourMonstre(source.getIntExtra("MonstreID", -1));
            debloquerMonstre(source.getIntExtra("MonstreID", -1));
        }
    }

    public void debloquerMonstre(int idMonstre){
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(idMonstre));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        Monstre monstreAfficcher = monstreBDD.getMonstreWithID(idMonstre);
        imageView.setImageResource(
                this.getResources().getIdentifier(monstreAfficcher.getApparence().toLowerCase(), "drawable", getPackageName()));
        nomMonstreView.setText(monstreAfficcher.getNom());
        monstreAfficcher.setDebloque(true);
        monstreAfficcher.setPDA(Math.max(monstreAfficcher.getPDA(),10+getIntent().getIntExtra("PDA",0)));
        monstreAfficcher.setPDV(Math.max(monstreAfficcher.getPDV(),50+getIntent().getIntExtra("PDV",0)));
        monstreBDD.selectMonstre(monstreAfficcher.getId());
        monstreBDD.updateMonstre(monstreAfficcher.getId(), monstreAfficcher);
        monstreBDD.close();
    }

    public void mettreAJourMonstre(int idMonstre){
        //Création d'une instance de ma classe MonstreBDD
        Log.v("IDENTIFIANT MONSTRE2 : ", Integer.toString(idMonstre));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        Monstre monstreAfficcher = monstreBDD.getMonstreWithID(idMonstre);
        imageView.setImageResource(
                this.getResources().getIdentifier(monstreAfficcher.getApparence().toLowerCase(), "drawable", getPackageName()));
        nomMonstreView.setText(monstreAfficcher.getNom());
        PDVMonstreTextView.setText(monstreAfficcher.getPDV() + " PDV");
        PDAMonstreTextView.setText(monstreAfficcher.getPDA() + " PDA");
        monstreBDD.selectMonstre(monstreAfficcher.getId());

        monstreBDD.close();
    }
    public void mettreAJourArme(int id){
        //Création d'une instance de ma classe LivresBDD
        Log.v("IDENTIFIANT arme2 : ", Integer.toString(id));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreBDD.selectArme(id);
        armesBtn.setImageResource(
                this.getResources().getIdentifier(monstreBDD.getArmeWithID(id).getLienImage().toLowerCase(), "drawable", getPackageName()));
        monstreBDD.close();
    }
    public void mettreAJourArmure(int id){
        //Création d'une instance de ma classe LivresBDD
        Log.v("IDENTIFIANT armure2 : ", Integer.toString(id));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        monstreBDD.selectArmure(id);
        armuresBtn.setImageResource(
                this.getResources().getIdentifier(monstreBDD.getArmureWithID(id).getLienImage().toLowerCase(), "drawable", getPackageName()));
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