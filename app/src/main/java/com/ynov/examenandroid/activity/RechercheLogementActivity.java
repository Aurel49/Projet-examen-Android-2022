package com.ynov.examenandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ynov.examenandroid.R;

public class RechercheLogementActivity extends AppCompatActivity {

    /*
     *   Activité de la recherche d'un logement par ville
     *
     *   Cette activitée porte un NavHostFragment qui reçoit les fragments
     *
     *   fragment_liste_ville.xml et fragment_details_logement.xml
     *
     *   La navigation entre fragment est réalisée grace à logement_navigation.xml
     *
     * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_logement);
    }
}