package com.example.pierre.projetandroidbarcode;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + "monstre.db" + "'", null);

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

}