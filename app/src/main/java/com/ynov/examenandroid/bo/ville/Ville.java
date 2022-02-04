package com.ynov.examenandroid.bo.ville;

import android.os.Parcel;
import android.os.Parcelable;


public class Ville implements Parcelable {

    /*
     *   Classe Ville : représente un objet Ville
     *
     * */

    // Attributs
    int id;
    String name;
    ImageBnb pic;
    String created_at;

    // Constructeur
    public Ville(int id, String name, ImageBnb pic,String created_at) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.created_at = created_at;
    }

    // Constructeur pour Parcelable
    protected Ville(Parcel in) {
        id = in.readInt();
        name = in.readString();
        pic = in.readParcelable(ImageBnb.class.getClassLoader());
        created_at = in.readString();
    }

    // Getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomVille() {
        return name;
    }

    public void setNom(String nomVille) {
        this.name = nomVille;
    }

    public ImageBnb getPic() {
        return pic;
    }

    public void setPic(ImageBnb pic) {
        this.pic = pic;
    }

    public String getSize() {
        return created_at;
    }

    public void setSize(String size) {
        this.created_at = size;
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
        parcel.writeString(created_at);
    }

    // Le créateur du pakage (parcel) à sauvegarder
    public static final Creator<Ville> CREATOR = new Creator<Ville>() {
        // Création du model de sauvegarde
        @Override
        public Ville createFromParcel(Parcel in) {
            return new Ville(in);
        }

        @Override
        public Ville[] newArray(int size) {
            return new Ville[size];
        }
    };
}
