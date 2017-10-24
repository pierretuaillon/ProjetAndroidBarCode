package com.example.pierre.projetandroidbarcode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Pierre on 24/10/2017.
 */

public class GenerationBaseDeDonnees extends SQLiteOpenHelper {
    private static final String TABLE_MONSTRE = "table_monstre";
    private static final String COL_ID = "id";
    private static final String COL_PDV = "PDV";
    private static final String COL_PDA = "PDA";
    private static final String COL_NOM = "nom";
    private static final String COL_APPARENCE = "apparence";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_MONSTRE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PDV + " INTEGER NOT NULL, "
            + COL_PDA + " INTEGER NOT NULL, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_APPARENCE + " TEXT NOT NULL "
            + ");";

    public GenerationBaseDeDonnees(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_MONSTRE + ";");
        onCreate(db);
    }
}
