package com.example.pierre.projetandroidbarcode;

/**
 * Created by natha on 25/10/2017.
 */

public class Armure {
    private int id;
    private boolean debloque=false;
    private boolean selectionne=false;
    private String nom;
    private int defense;
    private String lienImage;

    public String getLienImage() {
        return lienImage;
    }

    public void setLienImage(String lienImage) {
        this.lienImage = lienImage;
    }

    public Armure (){}

    public Armure (int id, String nom, int defense, String lienImage){
        this.id=id;
        this.nom=nom;
        this.defense=defense;
        this.lienImage = lienImage;
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


    public boolean isSelectionne() {
        return selectionne;
    }

    public void setSelectionne(boolean selectionne) {
        this.selectionne = selectionne;
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
