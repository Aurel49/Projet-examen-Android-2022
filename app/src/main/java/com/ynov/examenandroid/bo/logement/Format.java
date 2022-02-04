package com.ynov.examenandroid.bo.logement;

import android.os.Parcel;
import android.os.Parcelable;

public class Format implements Parcelable {

    /*
     *   Classe Format: objet parent de l'objet Thumbnail
     *
     * */

    // Attributs
    Thumbnail thumbnail;

    // Constructeur
    public Format(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    // Constructeur pour Parcelable
    protected Format(Parcel in) {
        thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
    }

    public Format(Format unFormat) {
        this.thumbnail = unFormat.thumbnail;
    }

    // Getters & setters
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
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
        parcel.writeParcelable(thumbnail, i);
    }

    public static final Creator<Format> CREATOR = new Creator<Format>() {
        // Création du model de sauvegarde
        @Override
        public Format createFromParcel(Parcel in) {
            return new Format(in);
        }

        @Override
        public Format[] newArray(int size) {
            return new Format[size];
        }
    };
}
