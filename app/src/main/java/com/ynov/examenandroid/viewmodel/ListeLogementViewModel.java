package com.ynov.examenandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ynov.examenandroid.bo.logement.Logement;

import java.util.ArrayList;

public class ListeLogementViewModel extends ViewModel {

    // ViewModel qui sert à sauvegarder les données du fragment_liste_logement
    MutableLiveData<ArrayList<Logement>> arrayListLogements;

    // Méthode qui créer une liste de Logement et qui peut aussi en retourner
    // les données sauvegardées
    public MutableLiveData<ArrayList<Logement>> getArrayListLogements(){
        if(arrayListLogements == null){
            this.arrayListLogements = new MutableLiveData<>(new ArrayList<>());
        }
        return this.arrayListLogements;
    }

    // Inutile pour le moment ...
    public void setLogementArrayList(ArrayList<Logement> lm){
    }
}
