package com.example.pierre.projetandroidbarcode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Pierre on 24/10/2017.
 */

public class GenerationBaseDeDonnees extends SQLiteOpenHelper {

    //Monstres
    private static final String TABLE_MONSTRE = "table_monstre";
    private static final String COL_ID = "id";
    private static final String COL_PDV = "PDV";
    private static final String COL_PDA = "PDA";
    private static final String COL_NOM = "nom";
    private static final String COL_APPARENCE = "apparence";
    private static final String COL_DEBLOQUE = "debloque";
    private static final String COL_SELECTIONNE = "selectionne";

    //Armes
    private static final String TABLE_ARME = "table_arme";
    private static final String COL_ID_ARME = "id";
    private static final String COL_DEBLOQUE_ARME = "debloque";
    private static final String COL_SELECTIONNE_ARME = "selectionne";
    private static final String COL_NOM_ARME = "nom";
    private static final String COL_ATTAQUE_ARME = "attaque";
    private static final String COL_IMAGE_ARME = "lienImage";

    //Armures
    private static final String TABLE_ARMURE = "table_armure";
    private static final String COL_ID_ARMURE = "id";
    private static final String COL_DEBLOQUE_ARMURE = "debloque";
    private static final String COL_SELECTIONNE_ARMURE = "selectionne";
    private static final String COL_NOM_ARMURE = "nom";
    private static final String COL_DEFENSE_ARMURE = "defense";
    private static final String COL_IMAGE_ARMURE = "lienImage";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_MONSTRE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PDV + " INTEGER NOT NULL, "
            + COL_PDA + " INTEGER NOT NULL, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_APPARENCE + " TEXT NOT NULL, "
            + COL_DEBLOQUE + " BOOLEAN NOT NULL, "
            + COL_SELECTIONNE + " BOOLEAN NOT NULL "
            + ");";

    private static final String CREATE_BDD_ARME = "CREATE TABLE " + TABLE_ARME + " ("
            + COL_ID_ARME + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DEBLOQUE_ARME + " BOOLEAN NOT NULL, "
            + COL_NOM_ARME + " TEXT NOT NULL, "
            + COL_ATTAQUE_ARME + " INTEGER NOT NULL, "
            + COL_IMAGE_ARME + " TEXT NOT NULL, "
            + COL_SELECTIONNE_ARME + " BOOLEAN NOT NULL "
            + ");";

    private  static final String CREATE_BDD_ARMURE = "CREATE TABLE " + TABLE_ARMURE + " ("
            + COL_ID_ARMURE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DEBLOQUE_ARMURE + " BOOLEAN NOT NULL, "
            + COL_NOM_ARMURE + " TEXT NOT NULL, "
            + COL_DEFENSE_ARMURE + " INTEGER NOT NULL, "
            + COL_IMAGE_ARMURE + " TEXT NOT NULL, "
            + COL_SELECTIONNE_ARMURE + " BOOLEAN NOT NULL "
            + ");";

    public GenerationBaseDeDonnees(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
        db.execSQL(CREATE_BDD_ARME);
        db.execSQL(CREATE_BDD_ARMURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_MONSTRE + ";");
        db.execSQL("DROP TABLE " + TABLE_ARME + ";");
        db.execSQL("DROP TABLE " + TABLE_ARMURE + ";");
        onCreate(db);
    }


}
