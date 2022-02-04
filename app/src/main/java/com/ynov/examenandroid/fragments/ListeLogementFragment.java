package com.ynov.examenandroid.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
import com.ynov.examenandroid.adapter.LogementAdapter;
import com.ynov.examenandroid.adapter.VilleAdapter;
import com.ynov.examenandroid.bo.logement.Logement;
import com.ynov.examenandroid.bo.ville.Ville;
import com.ynov.examenandroid.viewmodel.ListeLogementViewModel;
import com.ynov.examenandroid.viewmodel.ListeVilleViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeLogementFragment extends Fragment {

    /*
     *   Classe ListeLogementFragment: controller du fragment fragment_liste_ville
     *
     *   Ne sert pas depuis les dernière consignes d'examen
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
    private LogementAdapter logementAdapter;
    // RecyclerView permet d’afficher une liste en réutilisant un template
    private RecyclerView rv;
    // ViewModel pour sauvegarder les données d'un fragment
    // row_layout_message.xml
    private ListeLogementViewModel vm;
    //
    View view;
    Ville dataLogementRecue;

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
        vm = new ViewModelProvider(this).get(ListeLogementViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Change le titre de la barre de menu
        getActivity().setTitle("Liste des logements :");

        // Inflate le fragment fragment_liste_logement dans le conteneur
        // parent (activity_recherche_logement)
        view = inflater.inflate(R.layout.fragment_liste_logement, container, false);

        // Récupère les données de la transition du frament_liste_ville vers le fragment_details_logement
        dataLogementRecue = ListeLogementFragmentArgs.fromBundle(getArguments()).getArgsVilleToLogement();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Quand la vue est créée,
        // Instanciation de la RecyclerView qui est portée par l'activity_recherche_logement
        rv = getView().findViewById(R.id.idRecyclerViewListeLogement);
        //Instanciation d'un Adapter
        logementAdapter = new LogementAdapter();
        // Paramétrage de la RecyclerView pour afficher tous
        // les éléments de liste en Linéaire et verticale
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        // On associe l'Adapter à la RecyclerView portée par l'activité activity_recherche_logement
        rv.setAdapter(logementAdapter);
        //
        // Place un Observer sur une variable du fragment
        Observer<ArrayList<Logement>> observerList = logements -> {
            logementAdapter.setArrayListLogements(logements);
            // Permet de positionner la liste au dernier fragment à l'ouverture du fragment
            // rv.scrollToPosition(logementAdapter.getItemCount() - 1);
        };
        // Lie le ViewModel avec l'Observer
        // Si la liste évolue ou si le viewModel évolue
        // ça le mettra à jour
        vm.getArrayListLogements().observe(getViewLifecycleOwner(), observerList);

        // Si la liste est vide, on récupère les données du serveur
        if (vm.getArrayListLogements().getValue().isEmpty()) {
            String _complementUrl = "";
            fetchLogements( dataLogementRecue.getId(),_complementUrl, vm);
        }

        // Met à jour le parent conteneur recyclerView
        super.onViewCreated(view, savedInstanceState);
    }

    public void fetchLogements(int idVilleSelect, String _complementUrl, ListeLogementViewModel vm) {
        // Cette méthode gère la récupération de données via un client Http
        //
        // Référence HTTP
        OkHttpClient client = new OkHttpClient();
        Request villeRequest = new Request.Builder()
                .url("https://flutter-learning.mooo.com/logements" + _complementUrl)
                .build();

        client.newCall(villeRequest).enqueue(new Callback() {
            //
            // Traitement de la réponse du serveur
            //
            // Sur erreur on le notifie dans un log.
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "\nonFailure: " + "récupération des logements :" + e.getMessage() + "\n");
            }

            //
            // Si le serveur répond, code 200 ou meme 400, etc, on traite la réponse pour le client
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //Récupère les logement sous forme d'une String
                String resp = response.body().string();

                // Si réponse 200, on récupère et traite les données
                if (response.code() == 200) {
                    // Désérialisation String en Json du body reçu
                    ArrayList<Logement> bodyVille = new Gson().fromJson(
                            resp,
                            new TypeToken<ArrayList<Logement>>() {
                            }.getType()
                    );

                    ArrayList<Logement> lst = new ArrayList<>();
                    // Recherche des logements dans la ville
                    for (int i = 0; i < bodyVille.size(); i++) {
                        if(bodyVille.get(i).getPlace().getId() == idVilleSelect){
                            lst.add(bodyVille.get(i));
                        }
                    }

                    // Mise à jour du LogementViewModel
                    // Le ViewHolder sera mis à jour juste après avec l'Observer déclaré
                    // dans la méthode onViewCreated()
                    vm.getArrayListLogements().postValue(lst);

                } else {
                    // Sur problème d'authentification, on notifie le client.
                    // Execeptionnellement il n'y a pas d'authentification dans notre cas
                    Log.e(TAG, "onResponse: " + "authentification incorrecte");
                }
            }
        });
    }
}