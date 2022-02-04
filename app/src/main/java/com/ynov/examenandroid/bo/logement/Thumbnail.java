package com.ynov.examenandroid.bo.logement;

import android.os.Parcel;
import android.os.Parcelable;

public class Thumbnail implements Parcelable {

    /*
     *   Classe Thumbnail: permet de récupérer l'url de l'image miniature logement
     *
     * */

    // Attributs
    String url;

    // Constructeur
    public Thumbnail(String url) {
        this.url = url;
    }

    // Constructeur pour Parcelable
    protected Thumbnail(Parcel in) {
        url = in.readString();
    }

    public Thumbnail(Thumbnail unThumbnail) {
        this.url = unThumbnail.url;
    }

    // Getters & setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Mise à jour des données du model à sauvegarder
        parcel.writeString(url);
    }

    public static final Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {
        // Création du model de sauvegarde
        @Override
        public Thumbnail createFromParcel(Parcel in) {
            return new Thumbnail(in);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };
}
