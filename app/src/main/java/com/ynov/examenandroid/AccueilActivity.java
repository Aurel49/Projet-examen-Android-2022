package com.ynov.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.ynov.examenandroid.activity.RechercheLogementActivity;
import com.ynov.examenandroid.databinding.ActivityAccueilBinding;

public class AccueilActivity extends AppCompatActivity {

    /*
     *   Activité de la page d'accueil
     * */

    // Attributs pour le binding des éléments de la page d'accueil
    ActivityAccueilBinding binding;

    // Badges des menu de la bottomAppBar
    BadgeDrawable badge1, badge2, badge3, badge4;


    // Compteurs des badges pour les menus de la bottomAppBar
    private int cpt1, cpt2, cpt3, cpt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_accueil);

        binding.idHomeBarreDeRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AccueilActivity.this, RechercheLogementActivity.class);
                // Quelques data au cas où
                Bundle bundle = new Bundle();
                bundle.putString("subscribe_string_1", "From Home");
                bundle.putInt("subscribe_int_1", 1111);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        // Instanciations des itemMenu de la BottomNavigationView
        badge1 = binding.idBottomNavigationView.getOrCreateBadge(R.id.idMenu_generale);
        badge2 = binding.idBottomNavigationView.getOrCreateBadge(R.id.idMenu_recherche);
        badge3 = binding.idBottomNavigationView.getOrCreateBadge(R.id.idMenu_budget);
        badge4 = binding.idBottomNavigationView.getOrCreateBadge(R.id.idMenu_agenda);
        // Call the BottomNavigationView settings
        this.configureBottomView();
    }

    // 2 - Configure BottomNavigationView Listener
    private void configureBottomView() {
        binding.idBottomNavigationView.setOnNavigationItemSelectedListener(item ->
                updateMainFragment(item.getItemId()));
    }

    // 3 - Update Main Fragment design
    // Avec quelques animations
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    private Boolean updateMainFragment(Integer integer) {
        switch (integer) {
            case R.id.idMenu_generale:
                cpt1 += 1;
                badge1.setNumber(cpt1);

                // Affiche un toast avec les consignes d'utilisation de l'application
                View toastLayout = getLayoutInflater().inflate(R.layout.layout_toast_personnalise, (ViewGroup) findViewById(R.id.idToast_container));
                TextView txt = toastLayout.findViewById(R.id.idTxtToastP2);
                txt.setText("1: Cliquer que la barre de recherche pour commencer votre recherche.\n2) Puis dans la liste des villes, choisir une ville." +
                        "\n3) Dans la liste suivante, si un logement existe, choisir un logement en cliquant dessus.\n4) La page des détails du logement vous montrera des détails du logement. Puis cliquer sur RESERVER." +
                        "\n5) Sélectionner une date de réservation et ...");
                Toast toast = new Toast(this);
                toast.setView(toastLayout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.idMenu_recherche:
                cpt2 += 1;
                badge2.setNumber(cpt2);
                badge2.isVisible();
                break;
            case R.id.idMenu_budget:
                cpt3 += 1;
                badge3.setNumber(cpt3);
                badge3.isVisible();
                break;
            case R.id.idMenu_agenda:
                cpt4 += 1;
                badge4.setNumber(cpt4);
                badge4.isVisible();
                break;
        }
        return true;
    }
}
