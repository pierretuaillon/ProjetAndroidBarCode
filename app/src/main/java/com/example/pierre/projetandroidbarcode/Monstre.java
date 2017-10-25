package com.example.pierre.projetandroidbarcode;

import android.media.Image;

/**
 * Created by Pierre on 24/10/2017.
 */

public class Monstre {

    private int id;
    private int PDV;
    private int PDA;
    private String nom;
    private String apparence;
    private boolean debloque=false;


    public Monstre(){}

    public Monstre(int numeroMonstre, int PDV, int PDA, String nomMonstre, String apparence) {
        this.id = numeroMonstre;
        this.PDV = PDV;
        this.PDA = PDA;
        this.nom = nomMonstre;
        this.apparence = apparence;
    }

    public int getId() {
        return id;
    }

    public void setiD(int numeroMonstre) {
        this.id = numeroMonstre;
    }

    public int getPDV() {
        return PDV;
    }

    public void setPDV(int PDV) {
        this.PDV = PDV;
    }

    public int getPDA() {
        return PDA;
    }

    public void setPDA(int PDA) {
        this.PDA = PDA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomMonstre) {
        this.nom = nomMonstre;
    }

    public String getApparence() {
        return apparence;
    }

    public void setApparence(String apparence) {
        this.apparence = apparence;
    }

    public boolean isDebloque() {
        return debloque;
    }

    public void setDebloque(boolean debloque) {
        this.debloque = debloque;
    }
}
