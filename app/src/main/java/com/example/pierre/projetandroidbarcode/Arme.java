package com.example.pierre.projetandroidbarcode;

import android.util.Log;

/**
 * Created by natha on 25/10/2017.
 */

public class Arme {
    private int id;
    private boolean debloque=false;
    private boolean selectionne=false;
    private int attaque;
    private String nom;
    private String lienImage;

    public Arme(){}

    public Arme(int id, String nom, int atq, String lien) {
        this.id=id;
        this.nom=nom;
        this.attaque=atq;
        this.lienImage = lien;
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

    public void setDebloque(boolean debloqueUpdate) {
        debloque = debloqueUpdate;
    }

    public boolean isSelectionne() {
        return selectionne;
    }

    public void setSelectionne(boolean selectionne) {
        this.selectionne = selectionne;
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

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    @Override
    public String toString() {
        return "Arme{" +
                "id=" + id +
                ", debloque=" + debloque +
                ", attaque=" + attaque +
                ", nom='" + nom + '\'' +
                ", lienImage='" + lienImage + '\'' +
                '}';
    }
}
