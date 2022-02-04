package com.ynov.examenandroid.bo.logement;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Logement  implements Parcelable {

    /*
     *   Classe Logement : une instance représente un logement
     *
     * */

    // Attributs
    int id;
    Place place;
    String title;
    double price;
    String owner;
    double lat;
    double lng;
    List<String> listDateAvailable;
    Illustration illustrations;


    // Constructeur
    public Logement(int id, Place place, String title, double price, String owner, double lat, double lng, List<String> listDateAvailable, Illustration illustrations) {
        this.id = id;
        this.place = place;
        this.title = title;
        this.price = price;
        this.owner = owner;
        this.lat = lat;
        this.lng = lng;
        this.listDateAvailable = listDateAvailable;
        this.illustrations = illustrations;

    }

    // Constructeur pour Parcelable
    protected Logement(Parcel in) {
        id = in.readInt();
        place =  in.readParcelable(Place.class.getClassLoader());
        title = in.readString();
        price = in.readDouble();
        owner = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        listDateAvailable =  in.readArrayList(String.class.getClassLoader());
        illustrations =  in.readParcelable(Illustration.class.getClassLoader());
    }

    public Logement(Logement unLogement){
        this.id = unLogement.id;
        this.place = unLogement.place;
        this.title = unLogement.title;
        this.price = unLogement.price;
        this.owner = unLogement.owner;
        this.lat = unLogement.lat;
        this.lng = unLogement.lng;
        this.listDateAvailable = unLogement.listDateAvailable;
        this.illustrations = unLogement.illustrations;
    }

    // Getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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

    public List<String> getListDateAvailable() {
        return listDateAvailable;
    }

    public void setListDateAvailable(List<String> listDateAvailable) {
        this.listDateAvailable = listDateAvailable;
    }

    public Illustration getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Illustration illustrations) {
        this.illustrations = illustrations;
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
        parcel.writeInt(id);
        parcel.writeParcelable(place, i);
        parcel.writeString(title);
        parcel.writeDouble(price);
        parcel.writeString(owner);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeParcelable(illustrations, i);
    }

    public static final Creator<Logement> CREATOR = new Creator<Logement>() {
        // Création du model de sauvegarde
        @Override
        public Logement createFromParcel(Parcel in) {
            return new Logement(in);
        }

        @Override
        public Logement[] newArray(int size) {
            return new Logement[size];
        }
    };
}
