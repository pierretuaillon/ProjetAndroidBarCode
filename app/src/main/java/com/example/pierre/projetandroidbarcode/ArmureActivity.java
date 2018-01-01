package com.example.pierre.projetandroidbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ArmureActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nomArmure, defense, oldDefense;
    ImageView img;
    Button btnRetour, btnSave;
    Armure armureAfficher;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.showarmure);
        nomArmure = (TextView) findViewById(R.id.nomArmure);
        defense = (TextView) findViewById(R.id.def_View);
        oldDefense = (TextView) findViewById(R.id.olddef_View);
        img = (ImageView) findViewById(R.id.imageViewArmure);
        btnRetour = (Button) findViewById(R.id.retourBtn);
        btnSave = (Button) findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(this);
        btnRetour.setOnClickListener(this);
        Intent source = getIntent();
        if(source.hasExtra("ArmureID")){
            if(source.hasExtra("Armure")){
                debloquerArmure(source.getIntExtra("ArmureID", -1), source.getIntExtra("Armure", -1));
            }
        }
    }


    public void debloquerArmure(int idArmure, int armure){
        Log.v("IDENTIFIANT ARMURE : ", Integer.toString(idArmure));
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        armureAfficher = monstreBDD.getArmureWithID(idArmure);
        armureAfficher.setDebloque(true);
        armureAfficher.setDefense(Math.max(armureAfficher.getDefense(),armure));
        monstreBDD.updateArmure(armureAfficher);
        nomArmure.setText(armureAfficher.getNom());
        defense.setText(Integer.toString(armure));
        Armure oldArmure = monstreBDD.getArmureSelectionne();
        if(oldArmure!=null)
            oldDefense.setText(Integer.toString(oldArmure.getDefense()));
        img.setImageResource(
                this.getResources().getIdentifier(armureAfficher.getLienImage().toLowerCase(), "drawable", getPackageName())
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
                monstreBDD.selectArmure(armureAfficher.getId());
                monstreBDD.close();
                finish();
                break;
            default:
        }
    }
}
