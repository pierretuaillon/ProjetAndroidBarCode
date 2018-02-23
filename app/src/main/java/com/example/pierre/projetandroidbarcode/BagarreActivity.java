package com.example.pierre.projetandroidbarcode;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static android.os.Message.obtain;
import static android.provider.Settings.NameValueTable.NAME;

/**
 * Created by natha on 05/02/2018.
 */

public class BagarreActivity extends AppCompatActivity implements View.OnClickListener{

    //Joueur
    Monstre monstreJoueur;
    Arme armeJoueur;
    Armure armureJoueur;

    //Adversaire
    Monstre monstreAFaireCombattre;
    Arme armeMonstreAFaireCombattre;
    Armure armureMonstreAFaireCombattre;

    private TextView infoView, nomMonstreJoueur, nomMonstreAdversaire;
    private ProgressBar barPVJoueur, barPVadversaire;
    private ImageView monstreView, monstreAdvView;
    Button attaqueBTN, retourBtn;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.bagarre);

        this.monstreView = (ImageView) findViewById(R.id.imageView2);
        this.monstreAdvView = (ImageView) findViewById(R.id.imageView3);
        this.infoView = (TextView) findViewById(R.id.info);
        this.nomMonstreJoueur = (TextView) findViewById(R.id.nomJoueur);
        this.nomMonstreAdversaire = (TextView) findViewById(R.id.nomAdversaire);
        this.barPVJoueur = (ProgressBar) findViewById(R.id.progressBar);
        this.barPVadversaire = (ProgressBar) findViewById(R.id.progressBar2);
        this.attaqueBTN = (Button) findViewById(R.id.buttonBagarre);
        this.attaqueBTN.setOnClickListener(this);
        this.retourBtn = (Button) findViewById(R.id.menu);
        this.retourBtn.setOnClickListener(this);

        if(!alreadyInstance()) {
            //Création d'une instance de ma classe LivresBDD
            MonstreBDD monstreBDD = new MonstreBDD(this);

            monstreBDD.open();

            this.monstreJoueur = monstreBDD.getMonstreSelectionne();
            //On change le max des progressbar
            if (monstreJoueur == null)
                finish();
            else {

                this.monstreView.setImageResource(
                        this.getResources().getIdentifier(monstreJoueur.getApparence().toLowerCase(), "drawable", getPackageName()));

                //On récupére tous les monstres pour en faire combattre un au hasard
                ArrayList<Monstre> monstres = monstreBDD.getAllMonstres();

                Random rand = new Random();
                //Renvoie un nombre en 0 et le nombre de monstres
                int indexMonstre = rand.nextInt(monstres.size());

                while(this.monstreAFaireCombattre == null)
                    this.monstreAFaireCombattre = monstreBDD.getMonstreWithID(indexMonstre);

                this.monstreAdvView.setImageResource(
                        this.getResources().getIdentifier(monstreAFaireCombattre.getApparence().toLowerCase(), "drawable", getPackageName()));
                //On modifie ces points de vies en random
                this.monstreAFaireCombattre.setPDV(100+rand.nextInt(50));
                //idem pour les pda
                this.monstreAFaireCombattre.setPDA(30+rand.nextInt(50));

                this.barPVJoueur.setMax(monstreJoueur.getPDV());
                this.barPVadversaire.setMax(monstreAFaireCombattre.getPDV());

                if (monstreBDD.getArmeSelectionne() != null) {
                    this.armeJoueur = monstreBDD.getArmeSelectionne();
                } else {
                    this.armeJoueur = null;
                }

                if (monstreBDD.getArmureSelectionne() != null) {
                    this.armureJoueur = monstreBDD.getArmureSelectionne();
                } else {
                    this.armureJoueur = null;
                }

                this.nomMonstreJoueur.setText(monstreJoueur.getNom());
                this.nomMonstreAdversaire.setText(monstreAFaireCombattre.getNom());

                //On récupére toutes les armures pour en equiper une aléatoire
                ArrayList<Armure> armures = monstreBDD.getAllArmures();
                //idem pour les armes
                ArrayList<Armure> armes = monstreBDD.getAllArmures();

                while(this.armureMonstreAFaireCombattre == null){
                int indexArmure = rand.nextInt(armures.size());
                this.armureMonstreAFaireCombattre = monstreBDD.getArmureWithID(indexArmure);}
                this.armureMonstreAFaireCombattre.setDefense(30+rand.nextInt(30));

                while(this.armeMonstreAFaireCombattre == null){
                int indexArme = rand.nextInt(armes.size());
                this.armeMonstreAFaireCombattre = monstreBDD.getArmeWithID(indexArme);}
                this.armeMonstreAFaireCombattre.setAttaque(rand.nextInt(20)+20);
            }
            monstreBDD.close();
        }
    }

    //Méthode pour combattre flemme de la sortir du onclick x)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonBagarre) {
            Random rand = new Random();
            int attMonstre = this.monstreJoueur.getPDA();
            int attArme = 0;
            if (this.armeJoueur != null) {
                attArme = this.armeJoueur.getAttaque();
            }

            int degat = rand.nextInt(attMonstre + attArme) - rand.nextInt(this.armureMonstreAFaireCombattre.getDefense());
            this.infoView.setText("Vous infligez : " + degat);
            //On fait subir des dégats au monstre
            this.monstreAFaireCombattre.setPDV(monstreAFaireCombattre.getPDV() - degat);


            if (this.monstreAFaireCombattre.getPDV() <= 0) {
                this.barPVadversaire.setProgress(0);
                this.infoView.append("\n Vous gagnez");
                this.attaqueBTN.setEnabled(false);
                return;
            } else {
                //On met à jour sa barre de vie
                this.barPVadversaire.setProgress(monstreAFaireCombattre.getPDV());
            }

            //Au tour du joueur adversaire

            attMonstre = monstreAFaireCombattre.getPDA();
            attArme = this.armeMonstreAFaireCombattre.getAttaque();
            if (this.armureJoueur != null) {
                degat = rand.nextInt(attMonstre + attArme) - rand.nextInt(this.armureJoueur.getDefense());
            } else {
                degat = rand.nextInt(attMonstre + attArme);
            }

            //On fait subir des dégats au monstre
            this.monstreJoueur.setPDV(monstreJoueur.getPDV() - degat);


            if (monstreJoueur.getPDV() <= 0) {
                //On met à jour sa barre de vie
                this.barPVJoueur.setProgress(0);
                this.infoView.append("\n L'adversaire gagne");
                this.attaqueBTN.setEnabled(false);
            } else {
                this.barPVJoueur.setProgress(monstreJoueur.getPDV());
            }
        }
        if(v.getId()==R.id.menu)
            finish();
    }

    //Fonction pour ne pas tout remettre à 0
    public boolean alreadyInstance(){
        if ((this.monstreJoueur != null) && (this.monstreAFaireCombattre != null)){
            return true;
        }
        return false;
    }

}
