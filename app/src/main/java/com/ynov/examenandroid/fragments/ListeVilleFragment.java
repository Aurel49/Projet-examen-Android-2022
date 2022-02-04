package com.ynov.examenandroid.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ynov.examenandroid.R;
import com.ynov.examenandroid.adapter.VilleAdapter;
import com.ynov.examenandroid.bo.ville.Ville;
import com.ynov.examenandroid.viewmodel.ListeVilleViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeVilleFragment extends Fragment {

    /*
     *   Classe ListeVilleFragment: controller du fragment fragment_liste_ville
     *
     * */

    // Constante de débeugage
    private static final String TAG = "LISTE_VILLE_FRAGMENT";

    // Attributs
    // Référence au SharedPreferences
    private SharedPreferences sp;
    // Save du token
    private String token;
    // L’adapter permet au RecyclerView d’afficher une Liste de données
    private VilleAdapter villeAdapter;
    // RecyclerView permet d’afficher une liste en réutilisant un template
    private RecyclerView rv;
    // ViewModel pour sauvegarder les données d'un fragment
    // row_layout_message.xml
    private ListeVilleViewModel vm;

    // Les méthode overridées suivent le lifecycle du fragemnt
    // Méthodes
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On récupère une instance des préférencesShared
        sp = this.getContext().getSharedPreferences(getString(R.string.spConfigName), MODE_PRIVATE);
        // On récupère le token s'il existe. Ne sert pas pour cette application
        token = sp.getString(getString(R.string.keyJwt), "henlo");
        // On déclare un viewmodel pour maintenir en mémoire les données du fragment
        vm = new ViewModelProvider(this).get(ListeVilleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Change le titre de la barre de menu
        getActivity().setTitle("Liste des villes :");

        // Inflate le fragment fragment_liste_ville dans le conteneur
        // parent (activity_recherche_logement)
        return inflater.inflate(R.layout.fragment_liste_ville, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Quand la vue est créée,
        // Instanciation de la RecyclerView qui est portée par l'activity_recherche_logement
        rv = getView().findViewById(R.id.idRecyclerViewListeVille);
        //Instanciation d'un Adapter
        villeAdapter = new VilleAdapter();
        // Paramétrage de la RecyclerView pour afficher tous
        // les éléments de liste en Linéaire et verticale
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        // On associe l'Adapter à la RecyclerView portée par l'activité activity_recherche_logement
        rv.setAdapter(villeAdapter);
        //
        // Place un Observer sur une variable du fragment
        Observer<ArrayList<Ville>> observerList = villes -> {
            villeAdapter.setArrayListVilles(villes);
            // Permet de positionner la liste au dernier fragment à l'ouverture du fragment
            // rv.scrollToPosition(villeAdapter.getItemCount() - 1);
        };
        // Lie le ViewModel avec l'Observer
        // Si la liste évolue ou si le viewModel évolue
        // ça le mettra à jour
        vm.getArrayListVilles().observe(getViewLifecycleOwner(), observerList);

        // Si la liste est vide, on récupère les données du serveur
        if (vm.getArrayListVilles().getValue().isEmpty()) {
            String _complementUrl = "";
            fetchVilles(_complementUrl, vm);
        }

        // Met à jour le parent conteneur recyclerView
        super.onViewCreated(view, savedInstanceState);
    }

    public void fetchVilles(String _complementUrl, ListeVilleViewModel vm) {
        // Cette méthode gère la récupération de données via un client Http
        //
        // Référence HTTP
        OkHttpClient client = new OkHttpClient();
        Request villeRequest = new Request.Builder()
                .url("https://flutter-learning.mooo.com/villes" + _complementUrl)
                .build();

        client.newCall(villeRequest).enqueue(new Callback() {
            //
            // Traitement de la réponse du serveur
            //
            // Sur erreur on le notifie dans un log.
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "\nonFailure: " + "récupération des villes :" + e.getMessage() + "\n");
            }

            //
            // Si le serveur répond, code 200 ou meme 400, etc, on traite la réponse pour le client
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //Récupère les villes sous forme d'une String
                String resp = response.body().string();

                // Si réponse 200, on récupère et traite les données
                if (response.code() == 200) {
                    // Désérialisation String en Json du body reçu
                    ArrayList<Ville> bodyVille = new Gson().fromJson(
                            resp,
                            new TypeToken<ArrayList<Ville>>() {
                            }.getType()
                    );

                    // Mise à jour du VilleViewModel
                    // Le ViewHolder sera mis à jour juste après avec l'Observer déclaré
                    // dans la méthode onViewCreated()
                    vm.getArrayListVilles().postValue(bodyVille);

                } else {
                    // Sur problème d'authentification, on notifie le client.
                    // Execeptionnellement il n'y a pas d'authentification dans notre cas
                    Log.e(TAG, "onResponse: " + "authentification incorrecte");
                }
            }
        });
    }
}