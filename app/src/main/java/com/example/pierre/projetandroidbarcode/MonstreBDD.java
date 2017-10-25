package com.example.pierre.projetandroidbarcode;

/**
 * Created by Pierre on 24/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class MonstreBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "monstre.db";

    private static final String TABLE_MONSTRE = "table_monstre";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_PDV = "PDV";
    private static final int NUM_COL_PDV = 1;

    private static final String COL_PDA = "PDA";
    private static final int NUM_COL_PDA = 2;

    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 3;

    private static final String COL_APPARENCE = "apparence";
    private static final int NUM_COL_APPARENCE = 4;

    private static final String COL_DEBLOQUE = "debloque";
    private static final int NUM_COL_DEBLOQUE = 5;

    private SQLiteDatabase bdd;

    private GenerationBaseDeDonnees maBaseSQLite;

    public MonstreBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new GenerationBaseDeDonnees(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertMonstre(Monstre monstre){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_PDV, monstre.getPDV());
        values.put(COL_PDA, monstre.getPDA());
        values.put(COL_NOM, monstre.getNom());
        values.put(COL_APPARENCE, monstre.getApparence());
        values.put(COL_DEBLOQUE, monstre.isDebloque());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_MONSTRE, null, values);
    }

    public int updateMonstre(int id, Monstre monstre){
        //La mise à jour d'un monstre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel monstre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_PDV, monstre.getPDV());
        values.put(COL_PDA, monstre.getPDA());
        values.put(COL_NOM, monstre.getNom());
        values.put(COL_APPARENCE, monstre.getApparence());
        values.put(COL_DEBLOQUE, monstre.isDebloque());
        return bdd.update(TABLE_MONSTRE, values, COL_ID + " = " +id, null);
    }

    public int removeMonstreWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_MONSTRE, COL_ID + " = " +id, null);
    }

    public Monstre getMonstreWithName(String Name){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_MONSTRE, new String[] {COL_ID, COL_PDV, COL_PDA, COL_NOM, COL_APPARENCE}, COL_NOM + " LIKE \"" + Name +"\"", null, null, null, null);
        return cursorToMonstre(c);
    }

    public Monstre getMonstreWithID(int id){
        Cursor c = bdd.query(TABLE_MONSTRE, new String[] {COL_ID, COL_PDV, COL_PDA, COL_NOM, COL_APPARENCE, COL_DEBLOQUE}, COL_ID + " = " + id, null, null, null, null, null);
        return cursorToMonstre(c);
    }

    public ArrayList<Monstre> getAllMonstres(){
        ArrayList<Monstre> listeMonstre = new ArrayList<>();
        for (int i=1; i<11; i++){
            listeMonstre.add(getMonstreWithID(i));
        }
        return listeMonstre;
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Monstre cursorToMonstre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Monstre monstre = new Monstre();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        monstre.setiD(c.getInt(NUM_COL_ID));
        monstre.setPDV(c.getInt(NUM_COL_PDV));
        monstre.setPDA(c.getInt(NUM_COL_PDA));
        monstre.setNom(c.getString(NUM_COL_NOM));
        monstre.setApparence(c.getString(NUM_COL_APPARENCE));
        if (c.getInt(NUM_COL_DEBLOQUE) == 0){
            monstre.setDebloque(false);
        }else{
            monstre.setDebloque(true);
        }

        //On ferme le cursor
        c.close();

        //On retourne le livre
        return monstre;
    }

}
