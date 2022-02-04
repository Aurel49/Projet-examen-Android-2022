package com.ynov.examenandroid.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ynov.examenandroid.databinding.RowLayoutVilleBinding;

public class VilleViewHolder extends RecyclerView.ViewHolder {

    // Constante de débeugage
    private static final String TAG = "VILLE_VIEW_HOLDER";

    // Attributs: le binding va permettre de faire la correspondance des
    // élémens situés sur le row_layout_ville. Un élément du row_layout_ville
    // représente un élément de la liste des villes récupérées précédement
    RowLayoutVilleBinding binding;

    // Méthode qui assure le binding des éléments
    public VilleViewHolder(@NonNull RowLayoutVilleBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }
}
