package com.ynov.examenandroid.bo.ville;

import android.os.Parcel;
import android.os.Parcelable;

public class CPic  implements Parcelable {

    /*
    *   Classe CPic : représente un objet dans le json récupéré
    *
    * */

    // Attributs
    int width;
    int height;
    String url;

    // Constructeur
    public CPic(int width, int height, String url) {
        this.width = width;
        this.height = height;
        this.url = url;
    }

    // Constructeur pour Parcelable
    protected CPic(Parcel in) {
        width = in.readInt();
        height = in.readInt();
        url = in.readString();
    }

    // Getters & setters
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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

    // Mise à jour des données du model à sauvegarder
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(url);
    }

    // Le créateur du pakage (parcel) à sauvegarder
    public static final Creator<CPic> CREATOR = new Creator<CPic>() {
        // Création du model de sauvegarde
        @Override
        public CPic createFromParcel(Parcel in) {
            return new CPic(in);
        }

        @Override
        public CPic[] newArray(int size) {
            return new CPic[size];
        }
    };
}
