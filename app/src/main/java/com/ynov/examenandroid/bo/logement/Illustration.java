package com.ynov.examenandroid.bo.logement;

import android.os.Parcel;
import android.os.Parcelable;

public class Illustration implements Parcelable {

    /*
     *   Classe Illustration: classe objet parent de l'objet Format
     *
     * */

    // Attributs
    Format formats;

// Constructeur


    public Illustration(Format formats) {
        this.formats = formats;
    }

    // Constructeur pour Parcelable
    protected Illustration(Parcel in) {
        formats = in.readParcelable(Format.class.getClassLoader());
    }

    public Illustration(Illustration uneIllustration) {
        this.formats = uneIllustration.formats;
    }

    // Getters & setters
    public Format getFormats() {
        return formats;
    }

    public void setFormats(Format formats) {
        this.formats = formats;
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
        parcel.writeParcelable(formats, i);
    }

    public static final Creator<Illustration> CREATOR = new Creator<Illustration>() {
        // Création du model de sauvegarde
        @Override
        public Illustration createFromParcel(Parcel in) {
            return new Illustration(in);
        }

        @Override
        public Illustration[] newArray(int size) {
            return new Illustration[size];
        }
    };
}
