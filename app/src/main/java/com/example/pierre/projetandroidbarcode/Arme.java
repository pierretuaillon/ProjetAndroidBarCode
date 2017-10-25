package com.example.pierre.projetandroidbarcode;

/**
 * Created by natha on 25/10/2017.
 */

public class Arme {
    private int id;
    private boolean debloque;
    private int attaque;
    private String nom;

    public Arme(int id, String nom, int atq) {
        this.id=id;
        this.nom=nom;
        this.attaque=atq;
        this.debloque=false;
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

    public int getAttaque() {
        return attaque;
    }

    public void setAttaque(int attaque) {
        this.attaque = attaque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
