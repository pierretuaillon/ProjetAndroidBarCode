package com.example.pierre.projetandroidbarcode;

/**
 * Created by natha on 25/10/2017.
 */

public class Armure {
    private int id;
    private boolean debloque;
    private String nom;
    private int defense;

    public Armure (){}

    public Armure (int id, String nom, int defense){
        this.id=id;
        this.debloque=false;
        this.nom=nom;
        this.defense=defense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDebloque() {
        return debloque;
    }

    public void setDebloque(boolean debloque) {
        this.debloque = debloque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
