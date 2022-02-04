package com.ynov.examenandroid.bo.logement;

import android.os.Parcel;
import android.os.Parcelable;

import com.ynov.examenandroid.bo.ville.CPic;

public class Place implements Parcelable {

    /*
     *   Classe Place : représente un objet dans le json récupéré
     *
     * */

    // Attributs
    int id;
    String name;
    CPic pic;

    // Constructeur
    public Place(int id, String name, CPic pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }

    // Constructeur avec Parcelable
    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pic = in.readParcelable(CPic.class.getClassLoader());
    }

    // Getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CPic getPic() {
        return pic;
    }

    public void setPic(CPic pic) {
        this.pic = pic;
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeParcelable(pic, i);
    }

    // Le créateur du pakage (parcel) à sauvegarder
    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}