package com.example.pierre.projetandroidbarcode;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    private final int REQUEST_CODE_CAMERA = 100;
    private final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE= 101;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        bScan = (Button) findViewById(R.id.scan_button);
        Button MonstreBtn = (Button) findViewById(R.id.monstre_button);
        txtFormat = (TextView) findViewById(R.id.scan_format);
        txtContent = (TextView) findViewById(R.id.scan_content);
        bScan.setOnClickListener(this);
        MonstreBtn.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }

    }

    public void initialisation(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) &&
        (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set title
            alertDialogBuilder.setTitle("");
            // set dialog message
            alertDialogBuilder.setMessage("Merci pour votre confiance")
                    .setCancelable(true);
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        //Création d'une instance de ma classe LivresBDD
        MonstreBDD monstreBDD = new MonstreBDD(this);

        //On ouvre la base de données pour écrire dedans
        monstreBDD.open();

        genererData();
        // testerData();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        if(requestCode==22&&resultCode==RESULT_OK){
            if(intent.getStringExtra("scan_format") != null){
                Log.v("Scan format Main", intent.getStringExtra("scan_format"));
                txtFormat.setText(intent.getStringExtra("scan_format"));
            }
            if (intent.getStringExtra("scan_content") != null) {
                Log.v("Scan content Main", intent.getStringExtra("scan_content"));
                txtContent.setText(intent.getStringExtra("scan_content"));
                int[] tabInt = new int[4];
                tabInt = algoTraitement(intent.getStringExtra("scan_content"));


                if (tabInt[0] >= 0 && tabInt[0] < 20) {
                    Intent intentRedirectionScan = new Intent(MainActivity.this, MonstreActivity.class);
                    //Shasos
                    if (tabInt[1] >= 0 && tabInt[1] < 10) {
                        intentRedirectionScan.putExtra("MonstreID", 1);
                        //Brassso
                    } else if (tabInt[1] >= 10 && tabInt[1] < 20) {
                        intentRedirectionScan.putExtra("MonstreID", 2);
                        //Thrakhorn
                    } else if (tabInt[1] >= 20 && tabInt[1] < 30) {
                        intentRedirectionScan.putExtra("MonstreID", 3);
                        //Stynfu
                    } else if (tabInt[1] >= 30 && tabInt[1] < 40) {
                        intentRedirectionScan.putExtra("MonstreID", 4);
                        //Darzur
                    } else if (tabInt[1] >= 40 && tabInt[1] < 50) {
                        intentRedirectionScan.putExtra("MonstreID", 5);
                        //Wingdra
                    } else if (tabInt[1] >= 50 && tabInt[1] < 60) {
                        intentRedirectionScan.putExtra("MonstreID", 6);
                        //Feha
                    } else if (tabInt[1] >= 60 && tabInt[1] < 70) {
                        intentRedirectionScan.putExtra("MonstreID", 7);
                        //Claw
                    } else if (tabInt[1] >= 70 && tabInt[1] < 80) {
                        intentRedirectionScan.putExtra("MonstreID", 8);
                        //Roca
                    } else if (tabInt[1] >= 80 && tabInt[1] < 90) {
                        intentRedirectionScan.putExtra("MonstreID", 9);
                        //Lexcirhet
                    } else if (tabInt[1] >= 90 && tabInt[1] < 100) {
                        intentRedirectionScan.putExtra("MonstreID", 10);
                    }
                    intentRedirectionScan.putExtra("PDV", tabInt[2]);
                    intentRedirectionScan.putExtra("PDA", tabInt[3]);
                    startActivityForResult(intentRedirectionScan, 23);

                    //Debloque une arme
                } else if (tabInt[0] >= 20 && tabInt[0] < 60) {
                    Intent intentRedirectionScan = new Intent(MainActivity.this, ArmeActivity.class);
                    //Shasos
                    if (tabInt[1] >= 0 && tabInt[1] < 10) {
                        intentRedirectionScan.putExtra("ArmeID", 1);
                        //Brassso
                    } else if (tabInt[1] >= 10 && tabInt[1] < 20) {
                        intentRedirectionScan.putExtra("ArmeID", 2);
                        //Thrakhorn
                    } else if (tabInt[1] >= 20 && tabInt[1] < 30) {
                        intentRedirectionScan.putExtra("ArmeID", 3);
                        //Stynfu
                    } else if (tabInt[1] >= 30 && tabInt[1] < 40) {
                        intentRedirectionScan.putExtra("ArmeID", 4);
                        //Darzur
                    } else if (tabInt[1] >= 40 && tabInt[1] < 50) {
                        intentRedirectionScan.putExtra("ArmeID", 5);
                        //Wingdra
                    } else if (tabInt[1] >= 50 && tabInt[1] < 60) {
                        intentRedirectionScan.putExtra("ArmeID", 6);
                        //Feha
                    } else if (tabInt[1] >= 60 && tabInt[1] < 70) {
                        intentRedirectionScan.putExtra("ArmeID", 7);
                        //Claw
                    } else if (tabInt[1] >= 70 && tabInt[1] < 80) {
                        intentRedirectionScan.putExtra("ArmeID", 8);
                        //Roca
                    } else if (tabInt[1] >= 80 && tabInt[1] < 90) {
                        intentRedirectionScan.putExtra("ArmeID", 9);
                        //Lexcirhet
                    } else if (tabInt[1] >= 90 && tabInt[1] < 100) {
                        intentRedirectionScan.putExtra("ArmeID", 10);
                    }
                    intentRedirectionScan.putExtra("PDA", tabInt[2]);
                    startActivityForResult(intentRedirectionScan, 15);


                    //Debloque une armure
                } else {
                    Intent intentRedirectionScan = new Intent(MainActivity.this, MonstreActivity.class);
                    if (tabInt[1] >= 0 && tabInt[1] < 10) {
                        intentRedirectionScan.putExtra("ArmureID", 1);
                    } else if (tabInt[1] >= 10 && tabInt[1] < 20) {
                        intentRedirectionScan.putExtra("ArmureID", 2);
                    } else if (tabInt[1] >= 20 && tabInt[1] < 30) {
                        intentRedirectionScan.putExtra("ArmureID", 3);
                    } else if (tabInt[1] >= 30 && tabInt[1] < 40) {
                        intentRedirectionScan.putExtra("ArmureID", 4);
                    } else if (tabInt[1] >= 40 && tabInt[1] < 50) {
                        intentRedirectionScan.putExtra("ArmureID", 5);
                    } else if (tabInt[1] >= 50 && tabInt[1] < 60) {
                        intentRedirectionScan.putExtra("ArmureID", 6);
                    } else if (tabInt[1] >= 60 && tabInt[1] < 70) {
                        intentRedirectionScan.putExtra("ArmureID", 7);
                    } else if (tabInt[1] >= 70 && tabInt[1] < 80) {
                        intentRedirectionScan.putExtra("ArmureID", 8);
                    } else if (tabInt[1] >= 80 && tabInt[1] < 90) {
                        intentRedirectionScan.putExtra("ArmureID", 9);
                    } else if (tabInt[1] >= 90 && tabInt[1] < 100) {
                        intentRedirectionScan.putExtra("ArmureID", 10);
                    }
                    intentRedirectionScan.putExtra("Armure", tabInt[2]);
                    startActivityForResult(intentRedirectionScan, 10);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            Intent intent = new Intent(MainActivity.this, LecteurCodeBarres.class);
            startActivityForResult(intent,22);}
        if(v.getId()==R.id.monstre_button){
            Intent intent = new Intent(MainActivity.this, MonstreActivity.class);
            startActivityForResult(intent,15);}
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
            Monstre monstre0 = new Monstre(1, 50, 10, "Shasos", "dragon_3_tetes");
            Monstre monstre1 = new Monstre(2, 50, 10, "Brassso", "dragon_armure");
            Monstre monstre2 = new Monstre(3, 50, 10, "Thrakhorn", "dragon_colere");
            Monstre monstre3 = new Monstre(4, 50, 10, "Stynfu", "dragon_glace");
            Monstre monstre4 = new Monstre(5, 50, 10, "Darzur", "dragon_or");
            Monstre monstre5 = new Monstre(6, 50, 10, "Wingdra", "dragon_squellette");
            Monstre monstre6 = new Monstre(7, 50, 10, "Feha", "dragon_tortue");
            Monstre monstre7 = new Monstre(8, 50, 10, "Claw", "dragon_turquoise");
            Monstre monstre8 = new Monstre(9, 50, 10, "Roca", "dragon_vert");
            Monstre monstre9 = new Monstre(10, 50, 10, "Lexcirhet", "dragon_volant");

            //On insère les monstres que l'on vient de créer
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

        cursor = db.rawQuery("select * from table_arme", null);
        if(cursor.getCount()>0){
            Log.v("Insertion base arme", "Base déjà remplie");
        }else{
            Arme arme0 = new Arme(1, "Le taser", 10, "arme_electric");
            Arme arme1 = new Arme(2, "Le briquet", 10, "arme_fire");
            Arme arme2 = new Arme(3, "Le fister", 10, "arme_fighting");
            Arme arme3 = new Arme(4, "La plume d'oreiller", 10, "arme_flying");
            Arme arme4 = new Arme(5, "La beuh", 10, "arme_grass");
            Arme arme5 = new Arme(6, "Le glaçon Frigide", 10, "arme_ice");
            Arme arme6 = new Arme(7, "Le classic", 10, "arme_normal");
            Arme arme7 = new Arme(8, "le caillou", 10, "arme_rock");
            Arme arme8 = new Arme(9, "Le bout de metal", 10, "arme_steel");
            Arme arme9 = new Arme(10, "La fontaine", 10, "arme_water");

            //On insere les armes que l'on vient de creer
            monstreBDD.insertArme(arme0);
            monstreBDD.insertArme(arme1);
            monstreBDD.insertArme(arme2);
            monstreBDD.insertArme(arme3);
            monstreBDD.insertArme(arme4);
            monstreBDD.insertArme(arme5);
            monstreBDD.insertArme(arme6);
            monstreBDD.insertArme(arme7);
            monstreBDD.insertArme(arme8);
            monstreBDD.insertArme(arme9);

            Log.v("Insertion base arme", "Base remplie");
        }

        cursor = db.rawQuery("select * from table_armure", null);
        if(cursor.getCount()>0){
            Log.v("Insertion base armure", "Base déjà remplie");
        }else{
            Armure armure0 = new Armure(1, "Les ailes d'Icare", 10, "armure_ailes");
            Armure armure1 = new Armure(2, "Le casque à pointe", 10, "armure_casque");
            Armure armure2 = new Armure(3, "Le dentier de mamie", 10, "armure_dentier");
            Armure armure3 = new Armure(4, "L'anti faciale", 10, "armure_parapluie");
            Armure armure4 = new Armure(5, "L'Anneau vagi", 10, "armure_ring");
            Armure armure5 = new Armure(6, "La robe", 10, "armure_robe");
            Armure armure6 = new Armure(7, "La monture", 10, "armure_saddle");
            Armure armure7 = new Armure(8, "Le bouclier du captain", 10, "armure_shield");
            Armure armure8 = new Armure(9, "Les chaussures de Sonic", 10, "armure_shoes");
            Armure armure9 = new Armure(10, "Le plastron de maman", 10, "armure_torse");

            monstreBDD.insertArmure(armure0);
            monstreBDD.insertArmure(armure1);
            monstreBDD.insertArmure(armure2);
            monstreBDD.insertArmure(armure3);
            monstreBDD.insertArmure(armure4);
            monstreBDD.insertArmure(armure5);
            monstreBDD.insertArmure(armure6);
            monstreBDD.insertArmure(armure7);
            monstreBDD.insertArmure(armure8);
            monstreBDD.insertArmure(armure9);

            Log.v("Insertion base armure", "Base remplie");
        }
        //On close la BDD
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                }
                initialisation();
                return;
            }
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            this);
                    // set title
                    alertDialogBuilder.setTitle("");
                    // set dialog message
                    alertDialogBuilder.setMessage("Merci pour votre confiance")
                            .setCancelable(true);
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } else {

                }
                initialisation();
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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