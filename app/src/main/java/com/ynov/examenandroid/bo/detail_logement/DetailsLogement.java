package com.ynov.examenandroid.bo.detail_logement;

import android.os.Parcel;
import android.os.Parcelable;

import com.ynov.examenandroid.bo.logement.Logement;

public class DetailsLogement implements Parcelable {

    /*
     *   Classe DetailsLogement : une instance représente les détails d'un logement
     *
     * */

    // Attributs
    int idVille;
    String title;
    Double price;
    Double lat;
    Double lng;

    // Constructeur
    public DetailsLogement(int idVille, String title, Double price, Double lat, Double lng) {
        this.idVille = idVille;
        this.title = title;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
    }

    // Constructeur pour Parcelable
    public DetailsLogement(Parcel in) {
        this.idVille = in.readInt();
        this.title = in.readString();
        this.price = in.readDouble();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    // Getters & setters
    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    // Methods
    // Méthodes de Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        // Mise à jour des données du model à sauvegarder
        parcel.writeInt(idVille);
        parcel.writeString(title);
        parcel.writeDouble(price);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }

    public static final Creator<DetailsLogement> CREATOR = new Creator<DetailsLogement>() {
        // Création du model de sauvegarde
        @Override
        public DetailsLogement createFromParcel(Parcel in) {
            return new DetailsLogement(in);
        }

        @Override
        public DetailsLogement[] newArray(int size) {
            return new DetailsLogement[size];
        }
    };

}

