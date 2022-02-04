package com.ynov.examenandroid.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.ynov.examenandroid.R;
import com.ynov.examenandroid.bo.logement.Logement;
import com.ynov.examenandroid.bo.ville.Ville;
import com.ynov.examenandroid.databinding.RowLayoutVilleBinding;
import com.ynov.examenandroid.fragments.ListeVilleFragment;
import com.ynov.examenandroid.fragments.ListeVilleFragmentDirections;
import com.ynov.examenandroid.viewmodel.ListeLogementViewModel;
import com.ynov.examenandroid.viewmodel.ListeVilleViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VilleAdapter extends RecyclerView.Adapter<VilleViewHolder> {
    //
    // Adapter fait la correspondance entre le RecyclerView et la liste des villes
    //

    // Constante de débeugage
    private static final String TAG = "VILLE_ADAPTER";

    // Attributs
    // Liste des villes potentiellement récupérées du serveur
    ArrayList<Ville> arrayListVilles;

    // Constructeur
    public VilleAdapter() {
        this.arrayListVilles = new ArrayList<>();
    }

    // Méthodes
    // Ajouter une ville
    public void addVille(Ville m) {
        arrayListVilles.add(m);
        notifyItemInserted(arrayListVilles.size() - 1);
    }

    // Permet d'initialiser la liste avec une nouvelle liste de villes
    public void setArrayListVilles(ArrayList<Ville> arrayListVilles) {
        this.arrayListVilles = arrayListVilles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VilleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cette méthode va être appelée pour créer autant de row_layout que l'on peut
        // en afficher à l'écran, visible de utilisateur
        RowLayoutVilleBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_layout_ville,
                parent,
                false);
        // false car on ne veut pas que ça inflate automatiquement le layout, mais que ce soit l'adaptater qui fasse le job

        return new VilleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VilleViewHolder holder, int position) {
        //
        // Cette méthode à 2 fonctions.
        // 1 - Elle est appelée à chaque fois que l'on dois afficher des row_layout_ville
        // 2 - Elle permet de détecter qu'un appuis a été fait sur un item de la RecyclerView
        //
        // Récupère les données de la liste Ville pour chaque élément en partant de la fin
        // de la liste. Les index évoluent, exemple de 13 à 9. Ce qui correspond
        // au row_layout_ville affichée. Dès que l'on va scroller l'écran, cette métode va être
        // appelée autant de fois que le conteneur de la RecyclerView peut afficher à l'écran.
        Ville villeAAfficher = arrayListVilles.get(position);

        // Récupérer l'ID de la ville sélectionnée et l'envoyer l'envoyer au fragment détails logment.

        //ArgumentsRecusDeVillePourDetailsLogement
        // Redirection à la page détail d'un logement des logements
        ListeVilleFragmentDirections.ActionListeVilleFragmentToListeLogementFragment uneAction = ListeVilleFragmentDirections.actionListeVilleFragmentToListeLogementFragment(villeAAfficher);
        // Redirrige avec des données du binding vers un autre fragment. Cette autre fragment est celui qui a été paramétré
        // dans l'id de la transition de la logement_navigation Graph
        holder.itemView.setOnClickListener((view) -> {

                    // Check si une ville choisie contient au moins 1 appartement
                    _fetchLogementsExiste(villeAAfficher, "", holder, uneAction, view);
                }
        );

        // Met à jour la variable d'un item row_layout_ville à afficher. (c'est un binding)
        // avec l'item rowlayout
        holder.binding.setVarBindingRowLayoutVille(villeAAfficher);
        // Récupère l'url et affiche l'image dans l'ImageView du RowLayout utilisé par le RecyclerView
        Picasso.get().load("https://flutter-learning.mooo.com" + villeAAfficher.getPic().getUrl()).resize(350, 350).into(holder.binding.idImageRowLoyaoutVille);
    }

    @Override
    public int getItemCount() {
        // Récupère le nombre de message de la liste de données
        return arrayListVilles.size();
    }

    // Check si une ville contient des logements
    public void _fetchLogementsExiste(Ville uneVille, String _complementUrl, VilleViewHolder holder, ListeVilleFragmentDirections.ActionListeVilleFragmentToListeLogementFragment uneAction, View view) {
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

                    // Est-ce que le logement existe
                    boolean isLogementExiste = false;

                    ArrayList<Logement> lst = new ArrayList<>();
                    // Recherche des logements dans la ville
                    for (int i = 0; i < bodyVille.size(); i++) {
                        if (bodyVille.get(i).getPlace().getId() == uneVille.getId()) {
                            lst.add(bodyVille.get(i));

                            isLogementExiste = true;
                        }
                    }

                    if (!isLogementExiste) {

                        affToast(view, "Il n'existe pas de logement pour " + uneVille.getNomVille());

                    } else {
                        navigAilleur(holder, uneAction, view);
                    }

                } else {
                    // Sur problème d'authentification, on notifie le client.
                    // Execeptionnellement il n'y a pas d'authentification dans notre cas
                    Log.e(TAG, "onResponse: " + "authentification incorrecte");
                }
            }
        });
    }

    // Affiche une toast
    private void affToast(View view, final String msg) {
        view.post(new Runnable() {//runOnUiThread
            @Override
            public void run() {
                Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Navigation
    private void navigAilleur(VilleViewHolder holder, ListeVilleFragmentDirections.ActionListeVilleFragmentToListeLogementFragment uneAction, View view) {
        view.post(new Runnable() {//runOnUiThread
            @Override
            public void run() {
                Navigation.findNavController(holder.itemView).navigate((NavDirections) uneAction);
            }
        });
    }
}

