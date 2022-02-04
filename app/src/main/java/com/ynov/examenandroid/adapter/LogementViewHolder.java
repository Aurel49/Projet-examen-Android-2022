package com.ynov.examenandroid.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ynov.examenandroid.databinding.RowLayoutLogementBinding;

public class LogementViewHolder extends RecyclerView.ViewHolder {

    // Constante de débeugage
    private static final String TAG = "LOGEMENT_VIEW_HOLDER";

    // Attributs: le binding va permettre de faire la correspondance les textview
    // situé sur le row_layout
    RowLayoutLogementBinding binding;

    // Méthode qui assure le binding
    public LogementViewHolder(@NonNull RowLayoutLogementBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
