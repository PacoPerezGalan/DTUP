package com.pacoperezgalan.dtup;


import android.graphics.Bitmap;

public class Actor {
    String nom;
    String descricio;
    String naixement;
    String ciutat;
    String altura;
    String matrimoni;
    String fills;
    Bitmap image;

    public Actor(){

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescricio() {
        return descricio;
    }

    public void setDescricio(String descricio) {
        this.descricio = descricio;
    }

    public String getNaixement() {
        return naixement;
    }

    public void setNaixement(String naixement) {
        this.naixement = naixement;
    }

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getMatrimoni() {
        return matrimoni;
    }

    public void setMatrimoni(String matrimoni) {
        this.matrimoni = matrimoni;
    }

    public String getFills() {
        return fills;
    }

    public void setFills(String fills) {
        this.fills = fills;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
