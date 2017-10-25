package com.example.pierre.projetandroidbarcode;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


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

        //Création d'une instance de ma classe LivresBDD
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();

        genererData();
        // testerData();



    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        if(intent.getStringExtra("scan_format") != null){
            Log.v("Scan format Main", intent.getStringExtra("scan_format"));
            txtFormat.setText(intent.getStringExtra("scan_format"));
        }
        if (intent.getStringExtra("scan_content") != null){
            Log.v("Scan content Main", intent.getStringExtra("scan_content"));
            txtContent.setText(intent.getStringExtra("scan_content"));
            int [] tabInt = new int[4];
            tabInt = algoTraitement(intent.getStringExtra("scan_content"));

            //Shasos
            if (tabInt[0]>=0 && tabInt[0]<10 ){

            //Brassso
            }else if(tabInt[0]>=10 && tabInt[0]<20){

            //Thrakhorn
            }else if(tabInt[0]>=20 && tabInt[0]<30){

            //Stynfu
            }else if (tabInt[0]>=30 && tabInt[0]<40){

            //Darzur
            }else if (tabInt[0]>=40 && tabInt[0]<50){

            //Wingdra
            }else if (tabInt[0]>=50 && tabInt[0]<60){

            //Feha
            }else if (tabInt[0]>=60 && tabInt[0]<70){

            //Claw
            }else if (tabInt[0]>=70 && tabInt[0]<80){

            //Roca
            }else if (tabInt[0]>=80 && tabInt[0]<90){

            //Lexcirhet
            }else if (tabInt[0]>=90 && tabInt[0]<100){

            }




        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, LecteurCodeBarres.class);
        startActivityForResult(intent,22);
    }


    public void genererData(){
        //Création d'une instance de ma classe LivresBDD
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();
        SQLiteDatabase db=monstreBDD.getBDD();


        Cursor cursor = db.rawQuery("select * from table_monstre", null);
        if(cursor.getCount()>0){
            Log.v("Resultat insertion base", "Base déjà remplie");
        }else{
            //Création des monstres
            Monstre monstre0 = new Monstre(1, 50, 10, "Shasos", "../res/drawable/dragon_3_tetes.gif");
            Monstre monstre1 = new Monstre(2, 50, 10, "Brassso", "../res/drawable/dragon_armure.gif");
            Monstre monstre2 = new Monstre(3, 50, 10, "Thrakhorn", "../res/drawable/dragon_colere.gif");
            Monstre monstre3 = new Monstre(4, 50, 10, "Stynfu", "../res/drawable/dragon_glace.gif");
            Monstre monstre4 = new Monstre(5, 50, 10, "Darzur", "../res/drawable/dragon_or.gif");
            Monstre monstre5 = new Monstre(6, 50, 10, "Wingdra", "../res/drawable/dragon_squellette.gif");
            Monstre monstre6 = new Monstre(7, 50, 10, "Feha", "../res/drawable/dragon_tortue.gif");
            Monstre monstre7 = new Monstre(8, 50, 10, "Claw", "../res/drawable/dragon_turquoise.gif");
            Monstre monstre8 = new Monstre(9, 50, 10, "Roca", "../res/drawable/dragon_vert.gif");
            Monstre monstre9 = new Monstre(10, 50, 10, "Lexcirhet", "../res/drawable/dragon_volant.gif");

            //On insère le livre que l'on vient de créer
            monstreBDD.insertMonstre(monstre0);
            monstreBDD.insertMonstre(monstre1);
            monstreBDD.insertMonstre(monstre2);
            monstreBDD.insertMonstre(monstre3);
            monstreBDD.insertMonstre(monstre4);
            monstreBDD.insertMonstre(monstre5);
            monstreBDD.insertMonstre(monstre6);
            monstreBDD.insertMonstre(monstre7);
            monstreBDD.insertMonstre(monstre8);
            monstreBDD.insertMonstre(monstre9);
            Log.v("Resultat insertion base", "Base remplie");
        }



        //On close la BDD
        monstreBDD.close();
    }

    public void testerData(){
        //Création d'une instance de ma classe LivresBDD
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();

        Monstre m = monstreBDD.getMonstreWithID(1);

        if(m == null){
            Toast.makeText(this, "Ce monstre n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(this, "Ce monstre existe dans la BDD", Toast.LENGTH_LONG).show();
        }

        monstreBDD.close();

    }

    public int[] algoTraitement(String content){
        int [] tabInt = new int[4];

        int value = content.hashCode();

        String valueHash = Integer.toString(Math.abs(value));
        Log.v("HASHVALUE : ", valueHash);
        //il faut 8 valeurs;

        while(valueHash.length()<9){
            valueHash += "1";
        }

        tabInt[0] = Integer.parseInt(valueHash.substring(0, 2));
        tabInt[1] = Integer.parseInt(valueHash.substring(2, 4));
        tabInt[2] = Integer.parseInt(valueHash.substring(4, 6));
        tabInt[3] = Integer.parseInt(valueHash.substring(6, 8));
        Log.v("HASHVALUE1 : ", Integer.toString(tabInt[0]));
        Log.v("HASHVALUE2 : ", Integer.toString(tabInt[1]));
        Log.v("HASHVALUE3 : ", Integer.toString(tabInt[2]));
        Log.v("HASHVALUE4 : ", Integer.toString(tabInt[3]));

        return tabInt;
    }

}