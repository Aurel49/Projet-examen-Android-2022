package com.ynov.examenandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ynov.examenandroid.R;
import com.ynov.examenandroid.bo.logement.Logement;
import com.ynov.examenandroid.databinding.RowLayoutLogementBinding;
import com.ynov.examenandroid.fragments.ListeLogementFragmentDirections;
import com.ynov.examenandroid.fragments.ListeVilleFragmentDirections;

import java.util.ArrayList;

public class LogementAdapter extends RecyclerView.Adapter<LogementViewHolder> {

    // Constante de débeugage
    private static final String TAG = "LOGEMENT_ADAPTER";

    // Attributs
    ArrayList<Logement> arrayListLogements;

    // Constructeur
    public LogementAdapter() {
        this.arrayListLogements = new ArrayList<>();
    }

    // Méthodes
    // Ajouter un logement
    public void addLogement(Logement m) {
        arrayListLogements.add(m);
        notifyItemInserted(arrayListLogements.size() - 1);
    }

    // Permet d'initialiser la liste avec une nouvelle liste de données de logements
    public void setArrayListLogements(ArrayList<Logement> arrayListLogements) {
        this.arrayListLogements = arrayListLogements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Charge le fragment détails logement adapter
        RowLayoutLogementBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_layout_logement,
                parent,
                false);
        // false car on ne veut pas que ça inflate automatiquement le layout, mais que ce soit l'adaptater qui fasse le job

        return new LogementViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LogementViewHolder holder, int position) {
        // Récupère les data du Logement de la position sélectionné dans l'ArrayList
        Logement logementAAfficher = arrayListLogements.get(position);

        //ArgumentsRecusDeVillePourDetailsLogement
        // Redirection à la page détail d'un logement des logements
        ListeLogementFragmentDirections.ActionListeLogementFragmentToDetailsLogementFragment uneAction =
                ListeLogementFragmentDirections.actionListeLogementFragmentToDetailsLogementFragment(logementAAfficher);

        // On change de page pour afficher la page des détails du Logement
        holder.itemView.setOnClickListener((view) -> {
            Navigation.findNavController(holder.itemView).navigate((NavDirections) uneAction);
        });

        // Met à jour le row_layout, celui avec ses 3 textview, avec les données récupérées
        // avec l'item rowlayout
        holder.binding.setRowLayoutVarBindeeLogement(logementAAfficher);
        // Récupère l'url et affiche l'image dans l'ImageView du RowLayout utilisé par le RecyclerView
        Picasso.get().load("https://flutter-learning.mooo.com" + logementAAfficher.getIllustrations().getFormats().getThumbnail().getUrl()).resize(350, 350).into(holder.binding.idImageRowLoyaoutLogement);
    }

    @Override
    public int getItemCount() {
        // Récupère le nombre de logement de la liste de données
        return arrayListLogements.size();
    }
}
