package com.ynov.examenandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.ynov.examenandroid.bo.ville.Ville;

import java.util.ArrayList;

public class ListeVilleViewModel extends ViewModel {

    // ViewModel qui sert à sauvegarder les données du fragment_liste_ville
    MutableLiveData<ArrayList<Ville>> arrayListVilles;

    // Méthode qui créer une liste de Ville et qui peut aussi en retourner
    // les données sauvegardées
    public MutableLiveData<ArrayList<Ville>> getArrayListVilles(){
        if(arrayListVilles == null){
            this.arrayListVilles = new MutableLiveData<>(new ArrayList<>());
        }
        return this.arrayListVilles;
    }

    // Inutile pour le moment ...
    public void setVillesArrayList(ArrayList<Ville> lm){

    }
}
