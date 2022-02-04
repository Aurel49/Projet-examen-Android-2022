package com.ynov.examenandroid.bo.detail_logement;

public class DetailsDate {

    // Attributs
    String _urlImage;
    String _date;
    String _lieu;

    // Constructeur
    public DetailsDate(String _urlImage, String _date, String _lieu) {
        this._urlImage = _urlImage;
        this._date = _date;
        this._lieu = _lieu;
    }

    // Getters & setters
    public String get_urlImage() {
        return _urlImage;
    }

    public void set_urlImage(String _urlImage) {
        this._urlImage = _urlImage;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_lieu() {
        return _lieu;
    }

    public void set_lieu(String _lieu) {
        this._lieu = _lieu;
    }
}
