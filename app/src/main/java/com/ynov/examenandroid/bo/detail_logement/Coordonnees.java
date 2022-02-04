package com.ynov.examenandroid.bo.detail_logement;

import android.os.Parcel;
import android.os.Parcelable;

import com.ynov.examenandroid.bo.ville.ImageBnb;
import com.ynov.examenandroid.bo.ville.Ville;

public class Coordonnees implements Parcelable {

    /*
     *   Classe Coordonnees : représente un objet point de coordonnées
     *
     * */

    // Attributs
    double lat;
    double lng;
    double haut;

    // Constructeur
    public Coordonnees(double lat, double lng, double haut) {
        this.lat = lat;
        this.lng = lng;
        this.haut = haut;
    }

    // Constructeur pour Parcelable
    protected Coordonnees(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        haut = in.readDouble();
    }


    // Getters & setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getHaut() {
        return haut;
    }

    public void setHaut(double haut) {
        this.haut = haut;
    }


    // Methods

    // Méthodes de Parcelable
    //
    // pas utilisé mais on doit l'implémenter quand même
    // car issu d'une interface
    @Override
    public int describeContents() {
        return 0;
    }

    // Mise à jour des données du model à sauvegarder
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeDouble(haut);
    }

    // Le créateur du pakage (parcel) à sauvegarder
    public static final Creator<Coordonnees> CREATOR = new Creator<Coordonnees>() {
        // Création du model de sauvegarde
        @Override
        public Coordonnees createFromParcel(Parcel in) {
            return new Coordonnees(in);
        }

        @Override
        public Coordonnees[] newArray(int size) {
            return new Coordonnees[size];
        }
    };
}
