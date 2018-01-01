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

    private TextView nomArme, PDAArme, PDAOldArme;
    private ImageView img;
    private Button btnRetour, btnSave;
    private Arme armeAfficher;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showarme);
        nomArme = (TextView) findViewById(R.id.nomArme);
        PDAArme = (TextView) findViewById(R.id.PDA_View);
        PDAOldArme = (TextView) findViewById(R.id.PDA_OldView);
        img = (ImageView) findViewById(R.id.imageViewArme);
        btnRetour = (Button) findViewById(R.id.retourBtn);
        btnSave = (Button) findViewById(R.id.saveBtn);
        btnRetour.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        Intent source = getIntent();
        if(source.hasExtra("ArmeID")){
            if(source.hasExtra("PDA")){
                debloquerArme(source.getIntExtra("ArmeID", -1),getIntent().getIntExtra("PDA",0));
            }
        }
    }

    public void debloquerArme(int idArme, int PDA){
        Log.v("IDENTIFIANT ARME : ", Integer.toString(idArme));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        armeAfficher = monstreBDD.getArmeWithID(idArme);
        armeAfficher.setDebloque(true);
        armeAfficher.setAttaque(Math.max(armeAfficher.getAttaque(),10+PDA));
        monstreBDD.updateArme(armeAfficher);
        nomArme.setText(armeAfficher.getNom());
        PDAArme.setText(Integer.toString(PDA));
        Arme oldArme = monstreBDD.getArmeSelectionne();
        if(oldArme!=null)
            PDAOldArme.setText(Integer.toString(oldArme.getAttaque()));
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
            case R.id.saveBtn :
                MonstreBDD monstreBDD = new MonstreBDD(this);

                //On ouvre la base de données pour écrire dedans
                monstreBDD.open();
                monstreBDD.selectArme(armeAfficher.getId());
                monstreBDD.close();
                finish();
                break;
            default:
        }
    }
}
