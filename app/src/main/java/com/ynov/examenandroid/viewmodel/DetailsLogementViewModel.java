package com.ynov.examenandroid.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ynov.examenandroid.bo.detail_logement.DetailsLogement;

import java.util.ArrayList;

public class DetailsLogementViewModel extends ViewModel {

    // ViewModel qui sert à sauvegarder les données du fragment_details_fragment
    MutableLiveData<ArrayList<DetailsLogement>> arrayListDetailsLogements;

    // Méthode qui créer une liste de DetailsLogement et qui peut aussi en retourner
    // les données sauvegardées
    public MutableLiveData<ArrayList<DetailsLogement>> getArrayListDetailsLogements(){
        if(arrayListDetailsLogements == null){
            this.arrayListDetailsLogements = new MutableLiveData<>(new ArrayList<>());
        }
        return this.arrayListDetailsLogements;
    }

    // Inutile pour le moment ...
    public void setDetailsLogementArrayList(ArrayList<DetailsLogement> lm){

    }
}
