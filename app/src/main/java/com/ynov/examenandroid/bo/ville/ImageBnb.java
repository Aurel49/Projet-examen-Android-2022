package com.ynov.examenandroid.bo.ville;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quentin for YnovChat on 28/01/2022.
 */
public class ImageBnb implements Parcelable {
    // Attributs
    String url;
    double size;

    // Constructeur
    public ImageBnb(String url, double size) {
        this.url = url;
        this.size = size;
    }

    public ImageBnb(Parcel in) {
        url = in.readString();
        size = in.readDouble();
    }

    // Getters & setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    // Méthodes de Parcelable
    //
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeDouble(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageBnb> CREATOR = new Creator<ImageBnb>() {
        @Override
        public ImageBnb createFromParcel(Parcel in) {
            return new ImageBnb(in);
        }

        @Override
        public ImageBnb[] newArray(int size) {
            return new ImageBnb[size];
        }
    };
}
// return new Logement(in)
// in.readString()